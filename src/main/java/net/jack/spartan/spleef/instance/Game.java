package net.jack.spartan.spleef.instance;

import net.jack.spartan.spleef.GameState;
import net.jack.spartan.utilities.CC;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {

    private final Arena arena;
    private final List<UUID> SPLEEF = new ArrayList<>();
    boolean kickPlayers;

    public Game(Arena arena) {
        this.arena = arena;
    }

    public void start() {
        arena.setState(GameState.LIVE);
        arena.sendMessage(CC.translate("&bThe spleef game has commenced! Good luck!"));

        SPLEEF.addAll(arena.getPlayers());

    }

    public List<UUID> getSpleefMap() {
        return SPLEEF;
    }

    public boolean getKickPlayers() {
        return getKickPlayers();
    }
}
