package net.jack.lightning.stattrack.listeners;

import net.jack.lightning.LightningCore;
import net.jack.lightning.stattrack.StatTrack;
import net.jack.lightning.utilities.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Events implements Listener {

    private final LightningCore core;
    private final StatTrack statTrack;

    public Events(LightningCore core) {
        this.core = core;
        this.statTrack = new StatTrack(core);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        if (statTrack.userExists(player)) return;
        statTrack.createUser(player);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        Player killer = event.getEntity().getKiller();

        if (player == null & killer == null) return;
        statTrack.addKill(killer, 1);
        new Config(this.core.getStattrack(), this.core.getStatTrackConfiguration(), core);
    }

}
