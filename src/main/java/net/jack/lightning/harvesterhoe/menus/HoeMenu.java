package net.jack.lightning.harvesterhoe.menus;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class HoeMenu {

    private final LightningCore core;
    private Inventory hoeMenu;
    private final NamespacedKey autoSellKey;
    private final NamespacedKey asenabled;


    public HoeMenu(LightningCore core) {
        this.core = core;
        hoeMenu = Bukkit.createInventory(null, this.core.getHarvesterHoeConfiguration().getInt("HoeMenu.inventory.size"),
                CC.translate(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.title")));

        ItemStack glasspane = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1, (short) 0);
        int slot;
        while ((slot = hoeMenu.firstEmpty()) != -1) hoeMenu.setItem(slot, glasspane);
        this.autoSellKey = new NamespacedKey(core, "autosell");
        this.asenabled = new NamespacedKey(core, "asenabled");
    }

    public void openHoeMenu(Player player) {

        hoeMenu.setItem(this.core.getHarvesterHoeConfiguration().getInt("HoeMenu.inventory.auto-sell.slot"), autoSell(player));

        player.openInventory(hoeMenu);
    }

    public ItemStack autoSell(Player player) {
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        if (pdc.has(asenabled, PersistentDataType.STRING)) {
            ItemStack enabledItem = new ItemStack(Material.valueOf(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.auto-sell.enabled-item")));
            ItemMeta enabledMeta = enabledItem.getItemMeta();
            enabledMeta.getPersistentDataContainer().set(autoSellKey, PersistentDataType.STRING, "autosell");
            enabledMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            enabledMeta.setDisplayName(CC.translate(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.auto-sell.name")));
            List<String> lore = new ArrayList<>();
            for (final String l : this.core.getHarvesterHoeConfiguration().getStringList("MainMenu.inventory.auto-sell.lore")) {
                lore.add(CC.translate(l));
            }
            enabledMeta.setLore(lore);
            enabledItem.setItemMeta(enabledMeta);
            return enabledItem;
        } else {
            ItemStack item = new ItemStack(Material.valueOf(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.auto-sell.disabled-item")));
            ItemMeta meta = item.getItemMeta();
            meta.getPersistentDataContainer().set(autoSellKey, PersistentDataType.STRING, "autosell");
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.setDisplayName(CC.translate(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.auto-sell.name")));
            List<String> lore = new ArrayList<>();
            for (final String l : this.core.getHarvesterHoeConfiguration().getStringList("MainMenu.inventory.auto-sell.lore")) {
                lore.add(CC.translate(l));
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }

    public void updateHoeMenu(Player player) {
        player.updateInventory();
    }

    public Inventory getHoeMenu() {
        return hoeMenu;
    }
}


