package net.jack.lightning.spleef.listeners;

import net.jack.lightning.LightningCore;
import net.jack.lightning.spleef.instance.Arena;
import net.jack.lightning.spleef.instance.Game;
import net.jack.lightning.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class SpleefListener implements Listener {

    private final Game game;
    private final LightningCore core;
    private final Arena arena;
    private boolean lost = false;

    public SpleefListener(LightningCore core, Arena arena) {
        this.core = core;
        this.arena = arena;
        this.game = new Game(arena);
    }

    @EventHandler
    public void onSnowBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!arena.getPlayers().contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
        System.out.println(" tt");
        if (!game.getSpleefMap().contains(player.getUniqueId())) return;
        Block block = event.getBlock();
        if (!block.getType().equals(Material.SNOW_BLOCK)) {
            System.out.println("ttt");
            event.setCancelled(true);

        }
    }

    public void waterCheck() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!game.getSpleefMap().contains(player.getUniqueId())) return;
                    if (!(player.getLocation().getBlock().getType() == Material.WATER)) return;
                    arena.sendTitle(CC.translate("&b&lLoser!"), "&7You got spleefed! Better luck next time.");
                    arena.winnerReset(true);
                    lost = true;
                }
            }
        }.runTaskTimer(core, 20L, 20L);
    }

    public boolean getLost() {
        return lost;
    }
}

