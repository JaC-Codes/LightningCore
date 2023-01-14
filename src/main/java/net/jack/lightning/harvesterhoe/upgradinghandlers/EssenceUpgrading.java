package net.jack.lightning.harvesterhoe.upgradinghandlers;

import net.jack.lightning.LightningCore;
import net.jack.lightning.harvesterhoe.customenchants.EnchantProfile;
import net.jack.lightning.harvesterhoe.tokens.Tokens;
import net.jack.lightning.utilities.CC;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;


public class EssenceUpgrading implements Listener {

    private final LightningCore core;
    private final EnchantProfile enchantProfile;
    private final Tokens tokens;
    private final UpgradingHandler upgradingHandler;
    private int tokensRequired;


    private final NamespacedKey essUpgrade;
    private final NamespacedKey key;



    public EssenceUpgrading(LightningCore core) {
        this.core = core;
        this.enchantProfile = new EnchantProfile(core);
        this.tokens = new Tokens(core);
        this.upgradingHandler = new UpgradingHandler(core);

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
        tokensRequired = enchantProfile.getEssenceEnhanceLevel(player) * 50;
        if (tokens.getTokens(player) >= tokensRequired) {
            enchantProfile.addEssenceEnhanceLevel(player, 1);
            player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.essence-upgrade")));
            tokens.removeTokens(player, tokensRequired);
            upgradingHandler.loopInventory(player);
        } else {
            player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.insufficient-amount")));
        }
    }
}
