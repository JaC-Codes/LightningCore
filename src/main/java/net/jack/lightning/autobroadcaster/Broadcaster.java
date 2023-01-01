package net.jack.lightning.autobroadcaster;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.CC;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import static org.bukkit.Bukkit.getServer;

public class Broadcaster {

    private final LightningCore core;

    public Broadcaster(LightningCore core) {
        this.core = core;
    }

    public String getRandomElement(Set<String> set) {
        int index = new Random().nextInt(set.size());
        Iterator<String> iter = set.iterator();
        for (int i = 0; i < index; i ++) {
            iter.next();
        }
        return iter.next();
    }


    public void startTimer(BukkitScheduler scheduler) {
        int scheduleId = scheduler.scheduleSyncDelayedTask(core, () -> {
            if (core.getConfig().getBoolean("broadcasts-enabled")) {
                Set<String> broadcastList = this.core.getConfig().getConfigurationSection("Broadcasts.").getKeys(false);
                String broadcastId = getRandomElement(broadcastList);
                ConfigurationSection broadcast = this.core.getConfig().getConfigurationSection("Broadcasts." + broadcastId);
                for (String message : broadcast.getStringList("messages")) {
                    getServer().broadcastMessage(CC.translate(message).replace("%prefix%", CC.translate(core.getPrefix())));

                }
                if (broadcast.getString("sound") != null) {

                    for (Player player : getServer().getOnlinePlayers()) {
                        try {
                            player.playSound(player.getLocation(), Sound.valueOf(broadcast.getString("sounds")), 5, 5);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            startTimer(scheduler);
        }, this.core.getConfig().getLong("broadcast-interval"));
    }
}
