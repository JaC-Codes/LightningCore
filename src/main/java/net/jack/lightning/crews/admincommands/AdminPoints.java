package net.jack.lightning.crews.admincommands;

import net.jack.lightning.LightningCore;
import net.jack.lightning.crews.CrewUser;
import net.jack.lightning.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminPoints implements CommandExecutor {

    private final LightningCore core;
    private final CrewUser crewUser;

    public AdminPoints(LightningCore core) {
        this.core = core;
        this.crewUser = new CrewUser(core);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender.hasPermission("spartancore.admin"))) return false;

        int amount = Integer.parseInt(args[2]);
        Player target = Bukkit.getPlayerExact(args[1]);

        if (sender instanceof Player player) {

            switch (args[0]) {
                case "set":
                    argsCheck(player, args);
                    if (target.getPlayer() == null) {
                        sender.sendMessage(CC.translate("&7Player is not online"));
                    }
                    crewUser.setUserPoints(target.getPlayer(), amount);
                    player.sendMessage(CC.translate(this.core.getConfig().getString("Messages.Points-Send")
                            .replace("%player%", target.getName())
                            .replace("%amount%", String.valueOf(amount))));

                case "add":
                    argsCheck(player, args);
                    if (target.getPlayer() == null) {
                        sender.sendMessage(CC.translate("&7Player is not online"));
                    }
                    crewUser.addUserPoints(target.getPlayer(), amount);
                    player.sendMessage(CC.translate(this.core.getConfig().getString("Messages.Points-Add")
                            .replace("%player%", target.getName())
                            .replace("%amount%", String.valueOf(amount))));
                    target.sendMessage(CC.translate(this.core.getConfig().getString("Messages.Points-Received"))
                            .replace("%amount%", String.valueOf(amount)));

                case "remove":
                    argsCheck(player, args);
                    if (target.getPlayer() == null) {
                        sender.sendMessage(CC.translate("&7Player is not online"));
                    }
                    crewUser.removeUserPoints(target.getPlayer(), amount);
                    player.sendMessage(CC.translate(this.core.getConfig().getString("Messages.Points-Remove")
                            .replace("%player%", target.getName())
                            .replace("%amount%", String.valueOf(amount))));

                case "check":
                    if (args.length == 1) {
                        usage(player);
                    } else {
                        if (target.getPlayer() == null) {
                            sender.sendMessage(CC.translate("&7Player is not online"));
                        }
                        int gold = crewUser.getUserPoints(target.getPlayer());
                        player.sendMessage(CC.translate(this.core.getConfig().getString("Messages.Points-View")
                                .replace("%player%", target.getName())
                                .replace("%amount%", String.valueOf(gold))));
                    }
            }
        }
        return false;
    }

    public void usage(Player player) {
        for (final String i : this.core.getConfig().getStringList(("Crew-Admin-Usage"))) {
            player.sendMessage(CC.translate(i));
        }
    }

    public void argsCheck(Player player, String[] args) {
        if (args.length == 1) {
            usage(player);
        } else {
            if (args.length == 2) {
                usage(player);
            }
        }
    }
}



