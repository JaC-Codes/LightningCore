package net.jack.lightning.harvesterhoe.tokens;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.CC;
import net.jack.lightning.utilities.Config;
import org.bukkit.entity.Player;

public class Tokens {

    private final LightningCore core;

    public Tokens(LightningCore core) {
        this.core = core;
    }

    public boolean playerExists(Player player) {
        return this.core.getTokensConfiguration().contains("Player." + player.getUniqueId());
    }

    public void createPlayer(Player player) {
        this.core.getTokensConfiguration().set("Player." + player.getUniqueId() + ".tokens", 0);
        new Config(this.core.getTokens(), this.core.getTokensConfiguration(), core);
    }

    public int getTokens(Player player) {
        return this.core.getTokensConfiguration().getInt("Player." + player.getUniqueId() + ".tokens");
    }

    public void setTokens(Player player, int amount) {
        this.core.getTokensConfiguration().set("Player." + player.getUniqueId() + ".tokens", amount);
        new Config(this.core.getTokens(), this.core.getTokensConfiguration(), core);
    }

    public void addTokens(Player player, int amount) {
        int tokens = getTokens(player);
        this.core.getTokensConfiguration().set("Player." + player.getUniqueId() + ".tokens", (tokens + amount));
        new Config(this.core.getTokens(), this.core.getTokensConfiguration(), core);
    }

    public void removeTokens(Player player, int amount) {
        int tokens = getTokens(player);
        this.core.getTokensConfiguration().set("Player." + player.getUniqueId() + ".essence", (tokens - amount));
        new Config(this.core.getTokens(), this.core.getTokensConfiguration(), core);
    }

    public void usage(Player player) {
        for (final String i : this.core.getHarvesterHoeConfiguration().getStringList("Usage")) {
            player.sendMessage(CC.translate(i));
        }
    }
}

