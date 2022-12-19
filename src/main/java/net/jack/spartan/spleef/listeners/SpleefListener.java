package net.jack.spartan.spleef.listeners;

import net.jack.spartan.SpartanCore;
import net.jack.spartan.spleef.instance.Arena;
import net.jack.spartan.spleef.instance.Game;
import net.jack.spartan.utilities.CC;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpleefListener implements Listener {

    private final Game game;
    private final Arena arena;

    public SpleefListener(Arena arena) {
        this.arena = arena;
        this.game = new Game(arena);
    }

    @EventHandler
    public void onSnowBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!game.getSpleefMap().contains(player.getUniqueId())) return;
        Block block = event.getBlock();
        if (!block.getType().equals(Material.SNOW_BLOCK)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void landOnWater(PlayerInteractEvent event, boolean kickPlayers) {
        Player player = event.getPlayer();
        if (!game.getSpleefMap().contains(player.getUniqueId())) return;
        Material m = event.getPlayer().getLocation().getBlock().getType();
        if (m == Material.WATER) {
            arena.sendTitle(CC.translate("&b&lLoser!"), "&7You got spleefed! Better luck next time.");
            arena.reset(kickPlayers);
        }
    }
}
