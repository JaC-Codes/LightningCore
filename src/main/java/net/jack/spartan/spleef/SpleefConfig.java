package net.jack.spartan.spleef;

import net.jack.spartan.SpartanCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SpleefConfig {

    private final SpartanCore core;

    public SpleefConfig(SpartanCore core) {
        this.core = core;
    }

    public int getRequiredPlayers() {
        return this.core.getSpleefConfiguration().getInt("required-players");
    }

    public int getCountdownSeconds() {
        return this.core.getSpleefConfiguration().getInt("countdown-seconds");
    }

    public Location getLobbySpawn() {

        String worldString = this.core.getSpleefConfiguration().getString("lobby-spawn.world");
        if (worldString == null) {
            System.out.println("World null");
        }

        return new Location(Bukkit.getWorld(worldString), this.core.getSpleefConfiguration().getDouble("lobby-spawn.x"),
                this.core.getSpleefConfiguration().getDouble("lobby-spawn.y"), this.core.getSpleefConfiguration().getDouble("lobby-spawn.z"),
                (float) this.core.getSpleefConfiguration().getDouble("lobby-spawn.yaw"),
                (float) this.core.getSpleefConfiguration().getDouble("lobby-spawn.pitch"));
    }
}
