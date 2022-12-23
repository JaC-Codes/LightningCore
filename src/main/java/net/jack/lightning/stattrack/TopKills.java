package net.jack.lightning.stattrack;

import net.jack.lightning.LightningCore;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static java.util.Collections.reverseOrder;
import static java.util.Collections.sort;

public class TopKills {

    private final LightningCore core;
    private final StatTrack statTrack;

    private static final HashMap<String, Integer> topkills = new HashMap<>();

    public TopKills(LightningCore core) {
        this.core = core;
        this.statTrack = new StatTrack(core);
    }

    public void topKillTracker(OfflinePlayer player) {
        if (this.core.getStatTrackConfiguration().getConfigurationSection("Player.") == null) {
            return;
        }
        for (String i : this.core.getStatTrackConfiguration().getConfigurationSection("Player.").getKeys(false)) {
            int kills = this.core.getStatTrackConfiguration().getInt("Player." + player.getName() + ".kills");
            String name = statTrack.getPlayer(player.getName());
            topkills.put(name, kills);
        }
    }

    public void sortTop() {
        List<Map.Entry<String, Integer>> entrySet = topkills.entrySet().stream().limit(3).sorted(reverseOrder
                (Map.Entry.comparingByValue())).toList();

        for (Map.Entry<String, Integer> entry : entrySet) {
            System.out.println(entry.getValue());
        }

    }

    public void updateLeaderboard() {

    }

    public void killTopUpdater() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (OfflinePlayer player: Bukkit.getOfflinePlayers()) {
                    topKillTracker(player);
                    sortTop();
                }
                }
            }.runTaskTimer(core, 1200L,20L);
        }

    }

