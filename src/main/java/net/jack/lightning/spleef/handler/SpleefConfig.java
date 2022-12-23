package net.jack.lightning.spleef.handler;

import net.jack.lightning.LightningCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SpleefConfig {

    private final LightningCore core;

    public SpleefConfig(LightningCore core) {
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
