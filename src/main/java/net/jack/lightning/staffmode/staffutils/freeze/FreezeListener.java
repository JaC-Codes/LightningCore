package net.jack.lightning.staffmode.staffutils.freeze;

import net.jack.lightning.LightningCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FreezeListener implements Listener {

    private final LightningCore core;


    public FreezeListener(LightningCore core) {
        this.core = core;
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (core.getFreezeClass().getFrozen().contains(player.getUniqueId())) {
            event.setTo(event.getFrom());
        }
    }

    @EventHandler
    public void openPlayerInventory(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        if (core.getFreezeClass().getFrozen().contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void frozenDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (core.getFreezeClass().getFrozen().contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void disconnectWhileFrozen(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String permCheck = this.core.getStaffModeConfiguration().getString("StaffMode.permissions.freeze");

        if (permCheck == null) {
            System.out.println("Perm check for freeze not found");
        }
        if (!core.getFreezeClass().getFrozen().contains(player.getUniqueId())) return;
        for (Player players : Bukkit.getServer().getOnlinePlayers()) {
            if (!players.hasPermission(permCheck)) return;
            core.getFreezeClass().disconnectedWhileFrozen(players);
        }
    }


    }

