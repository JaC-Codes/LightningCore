package net.jack.lightning.harvesterhoe.essence;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.Config;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Essence {

    private final LightningCore core;

    public Essence(LightningCore core) {
        this.core = core;
    }

    public boolean playerExists(Player player) {
        return this.core.getEssenceConfiguration().contains("Player." + player.getUniqueId());
    }

    public void createPlayer(Player player) {
        this.core.getEssenceConfiguration().set("Player." + player.getUniqueId() + ".essence", 0);
        new Config(this.core.getEssence(), this.core.getEssenceConfiguration(), core);
    }

    public int getEssence(Player player) {
        return this.core.getEssenceConfiguration().getInt("Player." + player.getUniqueId() + ".essence");
    }

    public void setEssence(Player player, int amount) {
        this.core.getEssenceConfiguration().set("Player." + player.getUniqueId() + ".essence", amount);
        new Config(this.core.getEssence(), this.core.getEssenceConfiguration(), core);
    }

    public void addEssence(Player player, int amount) {
        int essence = getEssence(player);
        this.core.getEssenceConfiguration().set("Player." + player.getUniqueId() + ".essence", (essence + amount));
        new Config(this.core.getEssence(), this.core.getEssenceConfiguration(), core);
    }

    public void removeEssence(Player player, int amount) {
        int essence = getEssence(player);
        this.core.getEssenceConfiguration().set("Player." + player.getUniqueId() + ".essence", (essence - amount));
        new Config(this.core.getEssence(), this.core.getEssenceConfiguration(), core);
    }
}
