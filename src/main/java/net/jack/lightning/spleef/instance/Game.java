package net.jack.lightning.spleef.instance;

import net.jack.lightning.spleef.GameState;
import net.jack.lightning.utilities.CC;
import org.bukkit.entity.Player;

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
        arena.getPlayers().clear();
        arena.setState(GameState.LIVE);
        arena.sendMessage(CC.translate("&bThe spleef game has commenced! Good luck!"));
        arena.sendTitle("", "");

        SPLEEF.addAll(arena.getPlayers());

    }

    public List<UUID> getSpleefMap() {
        return SPLEEF;
    }

    public boolean getKickPlayers() {
        return getKickPlayers();
    }

    public void removeSpleefmap(Player player) {
        SPLEEF.remove(player.getUniqueId());
    }


}
