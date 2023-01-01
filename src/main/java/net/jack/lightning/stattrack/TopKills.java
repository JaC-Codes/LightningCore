package net.jack.lightning.stattrack;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static java.util.Collections.reverseOrder;

public class TopKills {

    private final LightningCore core;
    private final StatTrack statTrack;

    private static final HashMap<String, Integer> topkills = new HashMap<>();

    public TopKills(LightningCore core) {
        this.core = core;
        this.statTrack = new StatTrack(core);
    }

    public void topKillTracker() {
        if (this.core.getStatTrackConfiguration().getConfigurationSection("Player.") == null) {
            return;
        }

        for (String playerName : this.core.getStatTrackConfiguration().getConfigurationSection("Player").getKeys(false)) {
            int kills = this.core.getStatTrackConfiguration().getInt("Player." + playerName + ".kills");
            topkills.put(playerName, kills);
        }
    }


    public void sortTop(Player player) {
        List<Map.Entry<String, Integer>> entrySet = topkills.entrySet().stream().limit(3).sorted(reverseOrder
                (Map.Entry.comparingByValue())).toList();

        player.sendMessage("\n");
        player.sendMessage(CC.translate("&6&lTop Kills Leaderboard"));
        player.sendMessage("\n");

        for (Map.Entry<String, Integer> entry : entrySet) {
            player.sendMessage(CC.translate("&f" + (entry.getKey()) + "&7:" + entry.getValue()));
            player.sendMessage("\n");
        }

    }


    public void killTopUpdater() {
        new BukkitRunnable() {
            @Override
            public void run() {
                topKillTracker();
            }
        }.runTaskTimer(core, 1200L, 1200L);
    }

}

