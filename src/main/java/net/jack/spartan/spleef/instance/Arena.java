package net.jack.spartan.spleef.instance;

import net.jack.spartan.SpartanCore;
import net.jack.spartan.spleef.GameState;
import net.jack.spartan.spleef.handler.SpleefConfig;
import net.jack.spartan.utilities.CC;
import org.bukkit.Bukkit;
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
    private Game game;

    private GameState state;
    private List<UUID> players;
    private Countdown countdown;

    public Arena(int id, Location spawn, SpartanCore core) {
        this.id = id;
        this.spawn = spawn;
        this.core = core;
        this.game = new Game(this);

        this.spleefConfig = new SpleefConfig(core);

        this.countdown = new Countdown(core, this);
        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
    }

    public void start() {
        game.start();
    }

    public void reset(boolean kickPlayers) {

        if (kickPlayers) {
            Location location = spleefConfig.getLobbySpawn();
            for (UUID uuid : players) {
                Bukkit.getPlayer(uuid).teleport(location);
            }
            players.clear();
        }

        sendTitle("", "");
        state = GameState.RECRUITING;
        countdown.cancel();
        countdown = new Countdown(core, this);
        game = new Game(this);
    }

    public void sendMessage(String message) {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendMessage(CC.translate(message));
        }
    }

    public void sendTitle(String title, String subtitle) {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendTitle(CC.translate(title), subtitle);
        }
    }

    public void addPlayer(Player player) {

        players.add(player.getUniqueId());
        player.teleport(spawn);

        if (state.equals(GameState.RECRUITING) && players.size() >= spleefConfig.getRequiredPlayers()) {
            countdown.start();
        }
    }

    public void removePlayer(Player player) {

        players.remove(player.getUniqueId());
        player.teleport(spleefConfig.getLobbySpawn());
        player.sendTitle("", "");

        if (state == GameState.COUNTDOWN && players.size() < spleefConfig.getRequiredPlayers()) {
            sendMessage(CC.translate("Not enough players. Countdown cancelled."));
            reset(false);
            return;
        }

        if (state == GameState.LIVE && players.size() < spleefConfig.getRequiredPlayers()) {
            sendMessage(CC.translate("Game has been ended. Too many players quit."));
            reset(false);
        }
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

    public void setState(GameState state) {
        this.state = state;
    }

}
