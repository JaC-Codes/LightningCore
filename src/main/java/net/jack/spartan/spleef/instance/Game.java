package net.jack.spartan.spleef.instance;

import net.jack.spartan.spleef.GameState;
import net.jack.spartan.utilities.CC;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Game implements Listener {

    private final Arena arena;
    private List<UUID> SPLEEF = new ArrayList<>();

    public Game(Arena arena) {
        this.arena = arena;
    }

    public void start() {
        arena.setState(GameState.LIVE);
        arena.sendMessage(CC.translate("&bThe spleef game has commenced! Good luck!"));

        SPLEEF.addAll(arena.getPlayers());

    }

    @EventHandler
    public void onSnowBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!SPLEEF.contains(player.getUniqueId())) return;
        Block block = event.getBlock();
        if (!block.getType().equals(Material.SNOW_BLOCK)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void landOnWater(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!SPLEEF.contains(player.getUniqueId())) return;
        Material m = event.getPlayer().getLocation().getBlock().getType();
        if (m == Material.WATER) {
            arena.sendTitle(CC.translate("&b&lLoser!"), "&7You got spleefed! Better luck next time.");
            arena.reset();
        }
    }
}
