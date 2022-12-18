package net.jack.spartan.crews.listeners;

import net.jack.spartan.SpartanCore;
import net.jack.spartan.crews.CrewUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class CrewListeners implements Listener {

    private final SpartanCore core;
    private final CrewUser crewUser;

    public CrewListeners(SpartanCore core) {
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
