package net.jack.lightning.harvesterhoe.upgradinghandlers;

import net.jack.lightning.LightningCore;
import net.jack.lightning.harvesterhoe.customenchants.EnchantProfile;
import net.jack.lightning.harvesterhoe.essence.Essence;
import net.jack.lightning.harvesterhoe.tokens.Tokens;
import net.jack.lightning.utilities.CC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class XpGainerUpgrading implements Listener {

    private final LightningCore core;
    private final EnchantProfile enchantProfile;
    private final UpgradingHandler upgradingHandler;
    private final Tokens tokens;
    private int tokensXpRequired;

    private final NamespacedKey key;
    private final NamespacedKey xpgainer;

    public XpGainerUpgrading(LightningCore core) {
        this.core = core;
        this.enchantProfile = new EnchantProfile(core);
        this.upgradingHandler = new UpgradingHandler(core);
        this.tokens = new Tokens(core);

        this.key = new NamespacedKey(core, "hoe");
        this.xpgainer = new NamespacedKey(core, "xpgainer");
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getEquipment().getItemInMainHand();
        Block block = event.getBlock();
        Material material = event.getBlock().getType();
        Location broke = block.getLocation();
        if (!item.getType().equals(Material.DIAMOND_HOE)) return;
        if (!item.hasItemMeta()) return;
        if (!material.equals(Material.SUGAR_CANE)) return;
        if (!item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) return;
        if (broke.clone().subtract(0, 1, 0).getBlock().getType().equals(Material.SUGAR_CANE)) {
            int enchantLevel = enchantProfile.getXpGainerLevel(player);
            int sumOfExp = enchantLevel + 2;
            player.giveExp(sumOfExp);
        }
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!event.getView().getTitle().equals(CC.translate(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.title"))))
            return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) return;
        ItemMeta meta = event.getCurrentItem().getItemMeta();
        if (!meta.getPersistentDataContainer().has(xpgainer, PersistentDataType.STRING)) return;
        tokensXpRequired = enchantProfile.getXpGainerLevel(player) * 30;
        if (tokens.getTokens(player) >= tokensXpRequired) {
            enchantProfile.addXpGainerLevel(player, 1);
            player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.essence-upgrade")));
            tokens.removeTokens(player, tokensXpRequired);
            upgradingHandler.loopInventory(player);
        } else {
            player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.insufficient-amount")));
        }
    }

    public int getTokensXpRequired() {
        return tokensXpRequired;
    }
}
