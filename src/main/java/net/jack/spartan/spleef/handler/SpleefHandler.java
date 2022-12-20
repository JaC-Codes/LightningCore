package net.jack.spartan.spleef.handler;

import net.jack.spartan.SpartanCore;
import net.jack.spartan.spleef.instance.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpleefHandler {

    private final SpartanCore core;

    private List<Arena> arenas = new ArrayList<>();

    public SpleefHandler(SpartanCore core) {
        this.core = core;

        for (String m : this.core.getSpleefConfiguration().getConfigurationSection("arenas").getKeys(false)) {
            String worldString = this.core.getSpleefConfiguration().getString("arenas." + m + ".world");
            if (worldString == null) {
                System.out.println("World null");
            }
            arenas.add(new Arena(Integer.parseInt(m),
                    new Location(Bukkit.getWorld(worldString),
                    this.core.getSpleefConfiguration().getDouble("arenas." + m + ".x"),
                    this.core.getSpleefConfiguration().getDouble("arenas." + m + ".y"),
                    this.core.getSpleefConfiguration().getDouble("arenas." + m + ".z"),
                    (float) this.core.getSpleefConfiguration().getDouble("arenas." + m + ".yaw"),
                    (float) this.core.getSpleefConfiguration().getDouble("arenas." + m + ".pitch")), core));
        }
    }

    public List<Arena> getArenas() {
        return arenas;
    }

    public Arena getArena(Player player) {
        for (Arena arena : arenas) {
            if (arena.getPlayers().contains(player.getUniqueId())) {
                return arena;
            }
        }
        return null;
    }

    public Arena getArena(int id) {
        for (Arena arena : arenas) {
            if (arena.getId() == id) {
                return arena;
            }
        }
        return null;
    }
}
