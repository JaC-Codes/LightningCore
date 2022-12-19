package net.jack.spartan.spleef.listeners;

import net.jack.spartan.SpartanCore;
import net.jack.spartan.spleef.handler.SpleefConfig;
import net.jack.spartan.spleef.instance.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener {

    private final SpartanCore core;
    private final SpleefConfig spleefConfig;

    public ConnectListener(SpartanCore core) {
        this.core = core;
        this.spleefConfig = new SpleefConfig(core);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        player.teleport(spleefConfig.getLobbySpawn());

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        Arena arena = core.getSpleefHandler().getArena(player.getPlayer());
        if (arena != null) {
            arena.removePlayer(player);
        }

    }

}
