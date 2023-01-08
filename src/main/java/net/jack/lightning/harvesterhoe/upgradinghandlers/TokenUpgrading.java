package net.jack.lightning.harvesterhoe.upgradinghandlers;

import net.jack.lightning.LightningCore;
import net.jack.lightning.harvesterhoe.customenchants.EnchantProfile;
import net.jack.lightning.harvesterhoe.customenchants.EnumEnchants;
import net.jack.lightning.harvesterhoe.essence.Essence;
import net.jack.lightning.harvesterhoe.menus.HoeMenu;
import net.jack.lightning.utilities.CC;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class TokenUpgrading implements Listener {

    private final LightningCore core;
    private final EnchantProfile enchantProfile;
    private final Essence essence;
    private final HoeMenu hoeMenu;

    private final NamespacedKey tokenGrab;
    private final NamespacedKey key;

    public TokenUpgrading(LightningCore core) {
        this.core = core;
        this.enchantProfile = new EnchantProfile(core);
        this.essence = new Essence(core);
        this.hoeMenu = new HoeMenu(core);

        this.tokenGrab = new NamespacedKey(core, "tokengrab");
        this.key = new NamespacedKey(core, "hoe");
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!event.getView().getTitle().equals(CC.translate(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.title"))))
            return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) return;
        ItemMeta meta = event.getCurrentItem().getItemMeta();
        if (!meta.getPersistentDataContainer().has(tokenGrab, PersistentDataType.STRING)) return;
        int levelsRequired = enchantProfile.getTokenGrabberLevel(player) * 100;
        if (essence.getEssence(player) >= levelsRequired) {
            enchantProfile.addTokenGrabberLevel(player, 1);
            player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.token-upgrade")));
            essence.removeEssence(player, levelsRequired);
            loopInventory(player);

        } else {
            player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.insufficient-amount")));
        }
    }

    public void loopInventory(Player player) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item == null) return;
            if (!(item.getType() == Material.DIAMOND_HOE)) return;
            ItemMeta hoeMeta = item.getItemMeta();
            if (hoeMeta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
                List<String> lore = new ArrayList<>();
                for (final String l : this.core.getHarvesterHoeConfiguration().getStringList("HarvesterHoe.item.lore")) {
                    lore.add(CC.translate(l)
                            .replace("%tokengrabber%", EnumEnchants.TOKEN_GRABBER.getDisplayName())
                            .replace("%tokenlevel%", String.valueOf(enchantProfile.getTokenGrabberLevel(player)))
                            .replace("%essenceenhancer%", EnumEnchants.ESSENCE_ENHANCER.getDisplayName())
                            .replace("%essencelevel%", String.valueOf(enchantProfile.getEssenceEnhanceLevel(player))));
                }
                hoeMeta.setLore(lore);
                item.setItemMeta(hoeMeta);
                hoeMenu.updateHoeMenu(player);
            }
        }
    }
}
