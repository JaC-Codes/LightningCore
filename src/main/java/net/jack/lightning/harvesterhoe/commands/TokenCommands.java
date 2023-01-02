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
        } else if (args[1].equalsIgnoreCase("set")) {
            if (args.length == 2) {
                tokens.usage(player);
            } else {
                Player target = Bukkit.getPlayerExact(args[2]);
                if (args.length == 3) {
                    tokens.usage(player);
                } else {
                    int amount = Integer.parseInt(args[3]);
                    if (target.getPlayer() == null) {
                        player.sendMessage(CC.translate("&7Player is not online"));
                    }
                    tokens.setTokens(target.getPlayer(), amount);
                    if (amount == 1) {
                        player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.Token-Set").replace("%player%", target.getName())
                                .replace("%amount%", String.valueOf(amount))));
                    } else {
                        player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.Token-Set")
                                .replace("%player%", target.getName())
                                .replace("%amount%", String.valueOf(amount))));
                    }
                }
            }
        } else if (args[1].equalsIgnoreCase("add")) {
            if (args.length == 2) {
                tokens.usage(player);
            } else {
                Player target = Bukkit.getPlayerExact(args[2]);
                if (args.length == 3) {
                    tokens.usage(player);
                } else {
                    int amount = Integer.parseInt(args[3]);
                    if (target.getPlayer() == null) {
                        player.sendMessage(CC.translate("&7Player is not online"));
                    }
                    tokens.addTokens(target.getPlayer(), amount);
                    if (amount == 1) {
                        player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.Token-Add").replace("%player%", target.getName())
                                .replace("%amount%", String.valueOf(amount))));
                        target.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.Token-Received").replace("%amount%",
                                String.valueOf(amount))));
                    } else {
                        player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.Tokens-Add")
                                .replace("%player%", target.getName())
                                .replace("%amount%", String.valueOf(amount))));
                        target.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.Tokens-Received").replace("%amount%",
                                String.valueOf(amount))));
                    }
                }
            }
        } else if (args[1].equalsIgnoreCase("remove")) {
            if (args.length == 2) {
                tokens.usage(player);
            } else {
                Player target = Bukkit.getPlayerExact(args[2]);
                if (args.length == 3) {
                    tokens.usage(player);
                } else {
                    int amount = Integer.parseInt(args[3]);
                    if (target.getPlayer() == null) {
                        player.sendMessage(CC.translate("&7Player is not online"));
                    }
                    tokens.removeTokens(target.getPlayer(), amount);
                    if (amount == 1) {
                        player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.Token-Remove").replace("%player%", target.getName())
                                .replace("%amount%", String.valueOf(amount))));
                    } else {
                        player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.Tokens-Remove")
                                .replace("%player%", target.getName())
                                .replace("%amount%", String.valueOf(amount))));
                    }
                }
            }
        } else if (args[1].equalsIgnoreCase("check")) {
            if (args.length == 2) {
                tokens.usage(player);
            } else {
                Player target = Bukkit.getPlayerExact(args[2]);
                assert target != null;
                if (target.getPlayer() == null) {
                    player.sendMessage(CC.translate("&7Player is not online"));
                }
                int toks = tokens.getTokens(target.getPlayer());
                player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.Tokens-Check")
                        .replace("%player%", target.getName())
                        .replace("%amount%", String.valueOf(toks))));
            }
        }
    }
}




