package net.jack.lightning.stattrack;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.Config;
import org.bukkit.entity.Player;

import java.util.UUID;

public class StatTrack {

    private final LightningCore core;

    public StatTrack(LightningCore core) {
        this.core = core;
    }

    public int getKills(Player player) {
        return this.core.getStatTrackConfiguration().getInt("Player." + player.getName() + ".kills");
    }

    public void addKill(Player player, int amount) {

        int kills = getKills(player);
        this.core.getStatTrackConfiguration().set("Player." + player.getName() + ".kills", (kills + amount));
        new Config(this.core.getStattrack(), this.core.getStatTrackConfiguration(), core);
    }

    public String getPlayer(String name) {
        return this.core.getStatTrackConfiguration().getString("Player." + name);
    }
    public void createUser(Player player) {

        this.core.getStatTrackConfiguration().set("Player." + player.getName() + ".kills", 0);
        new Config(this.core.getStattrack(), this.core.getStatTrackConfiguration(), core);
    }

    public boolean userExists(Player player) {
        return this.core.getStatTrackConfiguration().contains("Player." + player.getUniqueId());
    }
}
