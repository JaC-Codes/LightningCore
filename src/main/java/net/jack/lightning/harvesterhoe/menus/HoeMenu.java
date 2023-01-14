package net.jack.lightning.harvesterhoe.menus;

import net.jack.lightning.LightningCore;
import net.jack.lightning.harvesterhoe.menus.menuhandlers.HoeMenuHandler;
import net.jack.lightning.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class HoeMenu {

    private final LightningCore core;
    private final Inventory hoeMenu;
    private final HoeMenuHandler hoeMenuHandler;


    public HoeMenu(LightningCore core) {
        this.core = core;
        this.hoeMenuHandler = new HoeMenuHandler(core);

        hoeMenu = Bukkit.createInventory(null, this.core.getHarvesterHoeConfiguration().getInt("HoeMenu.inventory.size"),
                CC.translate(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.title")));

    }

    public void openHoeMenu(Player player) {

        hoeMenu.setItem(this.core.getHarvesterHoeConfiguration().getInt("HoeMenu.inventory.auto-sell.slot"),
                hoeMenuHandler.autoSell(player));
        hoeMenu.setItem(this.core.getHarvesterHoeConfiguration().getInt("HoeMenu.inventory.essence-enhancer.slot"),
                hoeMenuHandler.essenceEnhancer(player));
        hoeMenu.setItem(this.core.getHarvesterHoeConfiguration().getInt("HoeMenu.inventory.token-grabber.slot"),
                hoeMenuHandler.tokenGrabber(player));
        hoeMenu.setItem(this.core.getHarvesterHoeConfiguration().getInt("HoeMenu.inventory.xp-gainer.slot"), hoeMenuHandler.xpGainer(player));


        player.openInventory(hoeMenu);
    }

    public void updateHoeMenu(Player player) {
        player.updateInventory();
    }

    public Inventory getHoeMenu() {
        return hoeMenu;
    }
}


