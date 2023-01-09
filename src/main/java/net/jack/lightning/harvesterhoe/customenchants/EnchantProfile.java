package net.jack.lightning.harvesterhoe.customenchants;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EnchantProfile implements Listener {

    private final LightningCore core;

    public EnchantProfile(LightningCore core) {
        this.core = core;
    }

    public boolean playerExists(Player player) {
        return this.core.getEnchantConfiguration().contains("Player." + player.getUniqueId());
    }

    public void createPlayer(Player player) {
        this.core.getEnchantConfiguration().set("Player." + player.getUniqueId() + ".EssenceEnhanceLevel", 1);
        this.core.getEnchantConfiguration().set("Player." + player.getUniqueId() + ".TokenGrabberLevel", 1);
        this.core.getEnchantConfiguration().set("Player." + player.getUniqueId() + ".XpGainerLevel", 1);
        new Config(this.core.getEnchants(), this.core.getEnchantConfiguration(), core);
    }

    public int getEssenceEnhanceLevel(Player player) {
        return this.core.getEnchantConfiguration().getInt("Player." + player.getUniqueId() + ".EssenceEnhanceLevel");
    }

    public void setEssenceEnhanceLevel(Player player, int amount) {
        this.core.getEnchantConfiguration().set("Player." + player.getUniqueId() + ".EssenceEnhanceLevel", amount);
        new Config(this.core.getEnchants(), this.core.getEnchantConfiguration(), core);
    }

    public void addEssenceEnhanceLevel(Player player, int amount) {
        int essenceLevel = getEssenceEnhanceLevel(player);
        this.core.getEnchantConfiguration().set("Player." + player.getUniqueId() + ".EssenceEnhanceLevel", (essenceLevel + amount));
        new Config(this.core.getEnchants(), this.core.getEnchantConfiguration(), core);
    }

    public void removeEssenceEnhanceLevel(Player player, int amount) {
        int essenceLevel = getEssenceEnhanceLevel(player);
        this.core.getEnchantConfiguration().set("Player." + player.getUniqueId() + "EssenceEnhanceLevel", (essenceLevel - amount));
        new Config(this.core.getEnchants(), this.core.getEnchantConfiguration(), core);
    }

    public int getTokenGrabberLevel(Player player) {
        return this.core.getEnchantConfiguration().getInt("Player." + player.getUniqueId() + ".TokenGrabberLevel");
    }

    public void setTokenGrabberLevel(Player player, int amount) {
        this.core.getEnchantConfiguration().set("Player." + player.getUniqueId() + ".TokenGrabberLevel", amount);
        new Config(this.core.getEnchants(), this.core.getEnchantConfiguration(), core);
    }

    public void addTokenGrabberLevel(Player player, int amount) {
        int TokenGrabberLevel = getTokenGrabberLevel(player);
        this.core.getEnchantConfiguration().set("Player." + player.getUniqueId() + ".TokenGrabberLevel", (TokenGrabberLevel + amount));
        new Config(this.core.getEnchants(), this.core.getEnchantConfiguration(), core);
    }

    public void removeTokenGrabberLevel(Player player, int amount) {
        int TokenGrabberLevel = getTokenGrabberLevel(player);
        this.core.getEnchantConfiguration().set("Player." + player.getUniqueId() + "TokenGrabberLevel", (TokenGrabberLevel - amount));
        new Config(this.core.getEnchants(), this.core.getEnchantConfiguration(), core);
    }

    public int getXpGainerLevel(Player player) {
        return this.core.getEnchantConfiguration().getInt("Player." + player.getUniqueId() + ".XpGainerLevel");
    }

    public void setXpGainerLevel(Player player, int amount) {
        this.core.getEnchantConfiguration().set("Player." + player.getUniqueId() + ".XpGainerLevel", amount);
        new Config(this.core.getEnchants(), this.core.getEnchantConfiguration(), core);
    }

    public void addXpGainerLevel(Player player, int amount) {
        int XpGainerLevel = getXpGainerLevel(player);
        this.core.getEnchantConfiguration().set("Player." + player.getUniqueId() + ".XpGainerLevel", (XpGainerLevel + amount));
        new Config(this.core.getEnchants(), this.core.getEnchantConfiguration(), core);
    }

    public void removeXpGainerLevel(Player player, int amount) {
        int XpGainerLevel = getXpGainerLevel(player);
        this.core.getEnchantConfiguration().set("Player." + player.getUniqueId() + "XpGainerLevel", (XpGainerLevel - amount));
        new Config(this.core.getEnchants(), this.core.getEnchantConfiguration(), core);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!playerExists(player)) {
            createPlayer(player);
        }
    }
}
