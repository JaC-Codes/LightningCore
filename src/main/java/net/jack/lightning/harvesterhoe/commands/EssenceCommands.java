package net.jack.lightning.harvesterhoe.commands;

import net.jack.lightning.LightningCore;
import net.jack.lightning.harvesterhoe.commandsmanager.SubCommand;
import net.jack.lightning.harvesterhoe.essence.Essence;
import net.jack.lightning.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EssenceCommands extends SubCommand {


    private final LightningCore core;
    private final Essence essence;

    public EssenceCommands(LightningCore core) {
        this.core = core;
        this.essence = new Essence(core);
    }

    @Override
    public String getName() {
        return "essence";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!(player.hasPermission("spartancore.admin"))) return;

        if (args.length == 0) {
            essence.usage(player);
        } else if (args[1].equalsIgnoreCase("set")) {
            if (args.length == 2) {
                essence.usage(player);
            } else {
                Player target = Bukkit.getPlayerExact(args[2]);
                if (args.length == 3) {
                    essence.usage(player);
                } else {
                    int amount = Integer.parseInt(args[3]);
                    if (target.getPlayer() == null) {
                        player.sendMessage(CC.translate("&7Player is not online"));
                    }
                    essence.setEssence(target.getPlayer(), amount);
                    player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.Essence-Set")
                            .replace("%player%", target.getName())
                            .replace("%amount%", String.valueOf(amount))));
                }
            }
        } else if (args[1].equalsIgnoreCase("add")) {
            if (args.length == 2) {
                essence.usage(player);
            } else {
                Player target = Bukkit.getPlayerExact(args[2]);
                if (args.length == 3) {
                    essence.usage(player);
                } else {
                    int amount = Integer.parseInt(args[3]);
                    if (target.getPlayer() == null) {
                        player.sendMessage(CC.translate("&7Player is not online"));
                    }
                    essence.addEssence(target.getPlayer(), amount);
                    player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.Essence-Add")
                            .replace("%player%", target.getName())
                            .replace("%amount%", String.valueOf(amount))));
                    target.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.Essence-Received").replace("%amount%",
                            String.valueOf(amount))));
                }
            }
        } else if (args[1].equalsIgnoreCase("remove")) {
            if (args.length == 2) {
                essence.usage(player);
            } else {
                Player target = Bukkit.getPlayerExact(args[2]);
                if (args.length == 3) {
                    essence.usage(player);
                } else {
                    int amount = Integer.parseInt(args[3]);
                    if (target.getPlayer() == null) {
                        player.sendMessage(CC.translate("&7Player is not online"));
                    }
                    essence.removeEssence(target.getPlayer(), amount);
                    player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.Essence-Remove")
                            .replace("%player%", target.getName())
                            .replace("%amount%", String.valueOf(amount))));
                }
            }
        } else if (args[1].equalsIgnoreCase("check")) {
            if (args.length == 2) {
                essence.usage(player);
            } else {
                Player target = Bukkit.getPlayerExact(args[2]);
                assert target != null;
                if (target.getPlayer() == null) {
                    player.sendMessage(CC.translate("&7Player is not online"));
                }
                int ess = essence.getEssence(target.getPlayer());
                player.sendMessage(CC.translate(this.core.getHarvesterHoeConfiguration().getString("Messages.Essence-Check")
                        .replace("%player%", target.getName())
                        .replace("%amount%", String.valueOf(ess))));
            }
        }
    }
}



