package net.jack.lightning.harvesterhoe.upgradinghandlers;

import net.jack.lightning.LightningCore;
import net.jack.lightning.harvesterhoe.customenchants.EnchantProfile;
import net.jack.lightning.harvesterhoe.customenchants.EnumEnchants;
import net.jack.lightning.harvesterhoe.essence.Essence;
import net.jack.lightning.harvesterhoe.menus.HoeMenu;
import net.jack.lightning.utilities.CC;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;


public class TokenUpgrading implements Listener {

    private final LightningCore core;
    private final EnchantProfile enchantProfile;
    private final Essence essence;
    private final UpgradingHandler upgradingHandler;
    private int essenceRequired;

    private final NamespacedKey tokenGrab;
    private final NamespacedKey key;

    public TokenUpgrading(LightningCore core) {
        this.core = core;
        this.enchantProfile = new EnchantProfile(core);
        this.essence = new Essence(core);
        this.upgradingHandler = new UpgradingHandler(core);

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
        essenceRequired = enchantProfile.getTokenGrabberLevel(player) * 100;
        if (essence.getEssence(player) >= essenceRequired) {
            enchantProfile.addTokenGrabberLevel(player, 1);
            player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.token-upgrade")));
            essence.removeEssence(player, essenceRequired);
            upgradingHandler.loopInventory(player);

        } else {
            player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.insufficient-amount")));
        }
    }

    public int getEssenceRequired() {
        return essenceRequired;
    }
}
