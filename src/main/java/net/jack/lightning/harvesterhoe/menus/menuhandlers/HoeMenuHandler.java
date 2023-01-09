package net.jack.lightning.harvesterhoe.menus.menuhandlers;

import net.jack.lightning.LightningCore;
import net.jack.lightning.harvesterhoe.customenchants.EnchantProfile;
import net.jack.lightning.harvesterhoe.customenchants.EnumEnchants;
import net.jack.lightning.utilities.CC;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class HoeMenuHandler {

    private final LightningCore core;
    private final EnchantProfile enchantProfile;

    private final NamespacedKey autoSellKey;
    private final NamespacedKey asenabled;
    private final NamespacedKey essUpgrade;
    private final NamespacedKey tokenGrab;
    private final NamespacedKey xpgainer;


    public HoeMenuHandler(LightningCore core) {
        this.core = core;
        this.enchantProfile = new EnchantProfile(core);

        this.autoSellKey = new NamespacedKey(core, "autosell");
        this.asenabled = new NamespacedKey(core, "asenabled");
        this.essUpgrade = new NamespacedKey(core, "essupgrade");
        this.tokenGrab = new NamespacedKey(core, "tokengrab");
        this.xpgainer = new NamespacedKey(core, "xpgainer");
    }

    public ItemStack autoSell(Player player) {
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        if (!pdc.has(asenabled, PersistentDataType.STRING)) {
            ItemStack enabledItem = new ItemStack(Material.valueOf(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.auto-sell.disabled-item")));
            ItemMeta enabledMeta = enabledItem.getItemMeta();
            enabledMeta.getPersistentDataContainer().set(autoSellKey, PersistentDataType.STRING, "autosell");
            enabledMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            enabledMeta.setDisplayName(CC.translate(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.auto-sell.disabled-name")));
            List<String> lore = new ArrayList<>();
            for (final String l : this.core.getHarvesterHoeConfiguration().getStringList("HoeMenu.inventory.auto-sell.disabled-lore")) {
                lore.add(CC.translate(l));
            }
            enabledMeta.setLore(lore);
            enabledItem.setItemMeta(enabledMeta);
            return enabledItem;
        } else {
            ItemStack item = new ItemStack(Material.valueOf(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.auto-sell.enabled-item")));
            ItemMeta meta = item.getItemMeta();
            meta.getPersistentDataContainer().set(autoSellKey, PersistentDataType.STRING, "autosell");
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.setDisplayName(CC.translate(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.auto-sell.enabled-name")));
            List<String> lore = new ArrayList<>();
            for (final String l : this.core.getHarvesterHoeConfiguration().getStringList("HoeMenu.inventory.auto-sell.enabled-lore")) {
                lore.add(CC.translate(l));
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }

    public ItemStack essenceEnhancer(Player player) {
        ItemStack item = new ItemStack(Material.valueOf(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.essence-enhancer.item")));
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(essUpgrade, PersistentDataType.STRING, "essupgrade");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(CC.translate(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.essence-enhancer.name"))
                .replace("%essenceenhancer%", EnumEnchants.ESSENCE_ENHANCER.getDisplayName()));
        List<String> lore = new ArrayList<>();
        for (final String l : this.core.getHarvesterHoeConfiguration().getStringList("HoeMenu.inventory.essence-enhancer.lore")) {
            lore.add(CC.translate(l)
                    .replace("%essencelevel%", String.valueOf(enchantProfile.getEssenceEnhanceLevel(player))));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack tokenGrabber(Player player) {
        ItemStack item = new ItemStack(Material.valueOf(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.token-grabber.item")));
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(tokenGrab, PersistentDataType.STRING, "tokengrab");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(CC.translate(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.token-grabber.name"))
                .replace("%tokengrabber%", EnumEnchants.TOKEN_GRABBER.getDisplayName()));
        List<String> lore = new ArrayList<>();
        for (final String l : this.core.getHarvesterHoeConfiguration().getStringList("HoeMenu.inventory.token-grabber.lore")) {
            lore.add(CC.translate(l)
                    .replace("%tokenlevel%", String.valueOf(enchantProfile.getTokenGrabberLevel(player))));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack xpGainer(Player player) {
        ItemStack item = new ItemStack(Material.valueOf(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.xp-gainer.item")));
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(tokenGrab, PersistentDataType.STRING, "xpgainer");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(CC.translate(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.xp-gainer.name"))
                .replace("%xpgainer%", EnumEnchants.XP_GAINER.getDisplayName()));
        List<String> lore = new ArrayList<>();
        for (final String l : this.core.getHarvesterHoeConfiguration().getStringList("HoeMenu.inventory.xp-gainer.lore")) {
            lore.add(CC.translate(l)
                    .replace("%xpgainlevel%", String.valueOf(enchantProfile.getXpGainerLevel(player))));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
