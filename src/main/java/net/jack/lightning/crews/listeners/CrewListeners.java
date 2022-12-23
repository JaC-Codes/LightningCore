package net.jack.lightning.crews.listeners;

import net.jack.lightning.LightningCore;
import net.jack.lightning.crews.CrewUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class CrewListeners implements Listener {

    private final LightningCore core;
    private final CrewUser crewUser;

    public CrewListeners(LightningCore core) {
        this.core = core;
        this.crewUser = new CrewUser(core);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!crewUser.userExists(player)) {
            crewUser.createUser(player);
        }
    }
}
