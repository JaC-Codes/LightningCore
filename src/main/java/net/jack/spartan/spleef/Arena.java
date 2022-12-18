package net.jack.spartan.spleef;

import net.jack.spartan.SpartanCore;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    private final int id;
    private final Location spawn;
    private final SpleefConfig spleefConfig;
    private final SpartanCore core;

    private GameState state;
    private List<UUID> players;

    public Arena(int id, Location spawn, SpartanCore core) {
        this.id = id;
        this.spawn = spawn;
        this.core = core;

        this.spleefConfig = new SpleefConfig(core);

        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
    }

    public void addPlayer(Player player) {

        players.add(player.getUniqueId());
        player.teleport(spawn);
    }

    public void removePlayer(Player player) {

        players.remove(player.getUniqueId());
        player.teleport(spleefConfig.getLobbySpawn());
    }

    public int getId() {
        return id;
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public GameState getState() {
        return state;
    }

}
