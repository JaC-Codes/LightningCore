package net.jack.lightning.harvesterhoe.commands;

import net.jack.lightning.LightningCore;
import net.jack.lightning.harvesterhoe.commandsmanager.SubCommand;
import net.jack.lightning.harvesterhoe.essence.Essence;
import net.jack.lightning.harvesterhoe.tokens.Tokens;
import net.jack.lightning.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TokenCommands extends SubCommand {



    private final LightningCore core;
    private final Tokens tokens;

    public TokenCommands(LightningCore core) {
        this.core = core;
        this.tokens = new Tokens(core);
    }

    @Override
    public String getName() {
        return "tokens";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!(player.hasPermission("spartancore.admin"))) return;

        if (args.length == 0) {
            tokens.usage(player);
            return;
        }

        if (args.length == 3) {
            Player target = Bukkit.getPlayerExact(args[1]);
            int amount = Integer.parseInt(args[2]);


            switch (args[0]) {

                case "set":
                    argsCheck(player, args);
                    if (target.getPlayer() == null) {
                        target.sendMessage(CC.translate("&7Player is not online"));
                    }
                    tokens.setTokens(target.getPlayer(), amount);
                    player.sendMessage(CC.translate(this.core.getConfig().getString("Messages.Points-Set")
                            .replace("%player%", target.getName())
                            .replace("%amount%", String.valueOf(amount))));
                    break;

                case "add":
                    argsCheck(player, args);
                    if (target.getPlayer() == null) {
                        player.sendMessage(CC.translate("&7Player is not online"));
                    }
                    tokens.addTokens(target.getPlayer(), amount);
                    player.sendMessage(CC.translate(this.core.getConfig().getString("Messages.Points-Add")
                            .replace("%player%", target.getName())
                            .replace("%amount%", String.valueOf(amount))));
                    target.sendMessage(CC.translate(this.core.getConfig().getString("Messages.Points-Received"))
                            .replace("%amount%", String.valueOf(amount)));
                    break;

                case "remove":
                    argsCheck(player, args);
                    if (target.getPlayer() == null) {
                        player.sendMessage(CC.translate("&7Player is not online"));
                    }
                    tokens.removeTokens(target.getPlayer(), amount);
                    player.sendMessage(CC.translate(this.core.getConfig().getString("Messages.Points-Remove")
                            .replace("%player%", target.getName())
                            .replace("%amount%", String.valueOf(amount))));
                    break;

                case "check":
                    if (target.getPlayer() == null) {
                        player.sendMessage(CC.translate("&7Player is not online"));
                    }
                    int tokenBal = tokens.getTokens(target.getPlayer());
                    player.sendMessage(CC.translate(this.core.getConfig().getString("Messages.Points-View")
                            .replace("%player%", target.getName())
                            .replace("%amount%", String.valueOf(tokenBal))));
                    break;
            }
        }
    }

    public void argsCheck(Player player, String[] args) {
        if (args.length == 1) {
            tokens.usage(player);
        } else {
            if (args.length == 2) {
                tokens.usage(player);
            }
        }
    }
}




