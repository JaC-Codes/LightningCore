package net.jack.lightning.harvesterhoe.upgradinghandlers;

import net.jack.lightning.LightningCore;
import net.jack.lightning.harvesterhoe.customenchants.EnchantProfile;
import net.jack.lightning.harvesterhoe.customenchants.EnumEnchants;
import net.jack.lightning.harvesterhoe.essence.Essence;
import net.jack.lightning.harvesterhoe.menus.HoeMenu;
import net.jack.lightning.harvesterhoe.menus.menuhandlers.HoeMenuHandler;
import net.jack.lightning.harvesterhoe.tokens.Tokens;
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


public class EssenceUpgrading implements Listener {

    private final LightningCore core;
    private final EnchantProfile enchantProfile;
    private final Tokens tokens;
    private final HoeMenu hoeMenu;
    private final HoeMenuHandler hoeMenuHandler;


    private final NamespacedKey essUpgrade;
    private final NamespacedKey key;


    public EssenceUpgrading(LightningCore core) {
        this.core = core;
        this.enchantProfile = new EnchantProfile(core);
        this.tokens = new Tokens(core);
        this.hoeMenu = new HoeMenu(core);
        this.hoeMenuHandler = new HoeMenuHandler(core);

        this.essUpgrade = new NamespacedKey(core, "essupgrade");
        this.key = new NamespacedKey(core, "hoe");
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!event.getView().getTitle().equals(CC.translate(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.title"))))
            return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) return;
        ItemMeta meta = event.getCurrentItem().getItemMeta();
        if (!meta.getPersistentDataContainer().has(essUpgrade, PersistentDataType.STRING)) return;
        int levelsRequired = enchantProfile.getTokenGrabberLevel(player) * 30;
        if (tokens.getTokens(player) >= levelsRequired) {
            enchantProfile.addEssenceEnhanceLevel(player, 1);
            player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.essence-upgrade")));
            tokens.removeTokens(player, levelsRequired);
            loopInventory(player);
            updatingInventory(player);
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
                hoeMenu.updateHoeMenu(player);
                hoeMeta.setLore(lore);
                item.setItemMeta(hoeMeta);
            }
        }
    }

    public void updatingInventory(Player player) {
        hoeMenu.getHoeMenu().remove(Material.valueOf(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.essence-enhancer.item")));
        hoeMenu.getHoeMenu().remove(Material.valueOf(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.token-grabber.item")));
        hoeMenu.getHoeMenu().setItem(this.core.getHarvesterHoeConfiguration().getInt("HoeMenu.inventory.essence-enhancer.slot"),
                hoeMenuHandler.essenceEnhancer(player));
        hoeMenu.getHoeMenu().setItem(this.core.getHarvesterHoeConfiguration().getInt("HoeMenu.inventory.token-grabber.slot"),
                hoeMenuHandler.tokenGrabber(player));
    }
}
