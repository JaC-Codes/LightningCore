package net.jack.lightning.stattrack.commands;

import net.jack.lightning.LightningCore;
import net.jack.lightning.stattrack.TopKills;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Leaderboard implements CommandExecutor {

    private final TopKills topKills;
    private final LightningCore core;

    public Leaderboard(LightningCore core) {
        this.core = core;
        this.topKills = new TopKills(core);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        if (args.length == 0) {
            topKills.sortTop(player);
            return true;
        }


        return false;
    }
}
