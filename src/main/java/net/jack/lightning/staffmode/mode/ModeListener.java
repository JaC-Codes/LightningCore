package net.jack.lightning.staffmode.mode;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ModeListener implements Listener {

    private final LightningCore core;

    public ModeListener(LightningCore core) {
        this.core = core;
    }

    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (core.getModeHandler().containsStaffArray(player.getUniqueId())) {
            core.getModeHandler().removeStaffArray(player.getUniqueId());
            core.getModeHandler().exitStaffMode(player);
        }
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (core.getModeHandler().containsStaffArray(player.getUniqueId())) {
            core.getModeHandler().removeStaffArray(player.getUniqueId());
            core.getModeHandler().exitStaffMode(player);
        }
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (core.getModeHandler().containsStaffArray(player.getUniqueId())) {
            event.setCancelled(true);
            player.sendMessage(CC.translate(this.core.getStaffModeConfiguration().getString("StaffMode.messages.block-break")));
        }
    }

    @EventHandler
    public void itemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (core.getModeHandler().containsStaffArray(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (core.getModeHandler().containsStaffArray(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void itemPickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if (core.getModeHandler().containsStaffArray(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (core.getModeHandler().containsStaffArray(player.getUniqueId())) {
            event.setCancelled(true);
            player.sendMessage(CC.translate(this.core.getStaffModeConfiguration().getString("StaffMode.messages.block-place")));
        }
    }
}
