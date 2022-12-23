package net.jack.lightning.spleef.instance;

import net.jack.lightning.LightningCore;
import net.jack.lightning.spleef.GameState;
import net.jack.lightning.spleef.handler.SpleefConfig;
import net.jack.lightning.spleef.listeners.SpleefListener;
import net.jack.lightning.utilities.CC;
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
    private final LightningCore core;
    private final SpleefListener spleefListener;
    private Game game;

    private GameState state;
    private List<UUID> players;
    private Countdown countdown;

    public Arena(Integer id, Location spawn, LightningCore core) {
        this.id = id;
        this.spawn = spawn;
        this.core = core;
        this.spleefListener = new SpleefListener(core, this);
        this.game = new Game(this);

        this.spleefConfig = new SpleefConfig(core);

        this.countdown = new Countdown(core, this);
        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
    }

    public void start() {
        game.start();
    }

    public void winnerReset(boolean kickPlayers) {

        if (spleefListener.getLost()) {
            reset(true);
        }

    }

    public void reset(boolean kickPlayers) {

        if (kickPlayers) {
            Location location = spleefConfig.getLobbySpawn();
            for (UUID uuid : players) {
                Bukkit.getPlayer(uuid).teleport(location);
            }
            players.clear();
        }



        game.getSpleefMap().clear();
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
            sendMessage(CC.translate("&7Not enough players. Countdown cancelled."));
            reset(false);
            return;
        }

        if (state == GameState.LIVE && players.size() < spleefConfig.getRequiredPlayers()) {
            sendMessage(CC.translate("&cGame has been ended. Too many players quit."));
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

    public Location getSpawn() {
        return this.spawn;
    }


}
