package net.jack.lightning.harvesterhoe.menus;

import net.jack.lightning.LightningCore;
import net.jack.lightning.harvesterhoe.menus.menuhandlers.HoeMenuHandler;
import net.jack.lightning.utilities.CC;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MenuEvents implements Listener {

    private final LightningCore core;
    private final HoeMenu hoeMenu;
    private final HoeMenuHandler hoeMenuHandler;
    private final MainMenu mainMenu;

    private final NamespacedKey guiHoeKey;
    private final NamespacedKey autoSellKey;
    private final NamespacedKey asenabled;
    private final NamespacedKey key;

    public MenuEvents(LightningCore core) {
        this.core = core;
        this.hoeMenu = new HoeMenu(core);
        this.hoeMenuHandler = new HoeMenuHandler(core);
        this.mainMenu = new MainMenu(core);

        this.guiHoeKey = new NamespacedKey(core, "guihoe");
        this.autoSellKey = new NamespacedKey(core, "autosell");
        this.asenabled = new NamespacedKey(core, "asenabled");
        this.key = new NamespacedKey(core, "hoe");
    }

    @EventHandler
    public void inventoryInteract(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        if (event.getView().getTitle().equals(CC.translate(this.core.getHarvesterHoeConfiguration().getString("MainMenu.inventory.title"))) ||
                event.getView().getTitle().equals(CC.translate(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.title")))) {
            event.setCancelled(true);
        }
        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) return;
        ItemMeta meta = event.getCurrentItem().getItemMeta();
        if (meta.getPersistentDataContainer().has(guiHoeKey, PersistentDataType.STRING)) {
            player.closeInventory();
            hoeMenu.openHoeMenu(player);
        } else if (meta.getPersistentDataContainer().has(autoSellKey, PersistentDataType.STRING) && !pdc.has(asenabled, PersistentDataType.STRING)) {
            enabledClick(player);
        } else if (meta.getPersistentDataContainer().has(autoSellKey, PersistentDataType.STRING) && pdc.has(asenabled, PersistentDataType.STRING)) {
            disabledClick(player);
            }
        }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
       if (event.getView().getTitle().equalsIgnoreCase(CC.translate(this.core.getHarvesterHoeConfiguration()
                .getString("HoeMenu.inventory.title")))) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    mainMenu.openMainMenu(player);
                }
            }.runTaskLater(core, 5L);
        }
    }

    public void loopInventory(Player player) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item == null) return;
            if (!(item.getType() == Material.DIAMOND_HOE)) return;
            ItemMeta hoeMeta = item.getItemMeta();
            if (hoeMeta.getPersistentDataContainer().has(key, PersistentDataType.STRING) &&
                    player.getPersistentDataContainer().has(asenabled, PersistentDataType.STRING)) {
                hoeMeta.setDisplayName(CC.translate(this.core.getHarvesterHoeConfiguration()
                        .getString("HarvesterHoe.item.auto-sell-name")));
                item.setItemMeta(hoeMeta);
            } else if (hoeMeta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
                hoeMeta.setDisplayName(CC.translate(this.core.getHarvesterHoeConfiguration()
                        .getString("HarvesterHoe.item.name")));
                item.setItemMeta(hoeMeta);
            }
        }
    }

    public void enabledClick(Player player) {
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        player.sendMessage(CC.translate("&a&lAUTO SELL &eenabled."));
        pdc.set(asenabled, PersistentDataType.STRING, "asenabled");
        Inventory updateHoe2 = hoeMenu.getHoeMenu();
        updateHoe2.setItem(this.core.getHarvesterHoeConfiguration().getInt("HoeMenu.inventory.auto-sell.slot"), hoeMenuHandler.autoSell(player));
        loopInventory(player);
    }

    public void disabledClick(Player player) {
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        player.sendMessage(CC.translate("&a&lAUTO SELL &edisabled."));
        pdc.remove(asenabled);
        Inventory updateHoe = hoeMenu.getHoeMenu();
        updateHoe.setItem(this.core.getHarvesterHoeConfiguration().getInt("HoeMenu.inventory.auto-sell.slot"), hoeMenuHandler.autoSell(player));
        loopInventory(player);
    }
}

