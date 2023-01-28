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
                    essence.setEssence(target.getPlayer(), amount);
                    player.sendMessage(CC.translate(this.core.getConfig().getString("Messages.Points-Set")
                            .replace("%player%", target.getName())
                            .replace("%amount%", String.valueOf(amount))));
                    break;

                case "add":
                    argsCheck(player, args);
                    if (target.getPlayer() == null) {
                        player.sendMessage(CC.translate("&7Player is not online"));
                    }
                    essence.addEssence(target.getPlayer(), amount);
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
                    essence.removeEssence(target.getPlayer(), amount);
                    player.sendMessage(CC.translate(this.core.getConfig().getString("Messages.Points-Remove")
                            .replace("%player%", target.getName())
                            .replace("%amount%", String.valueOf(amount))));
                    break;

                case "check":
                    if (target.getPlayer() == null) {
                        player.sendMessage(CC.translate("&7Player is not online"));
                    }
                    int essenceBal = essence.getEssence(target.getPlayer());
                    player.sendMessage(CC.translate(this.core.getConfig().getString("Messages.Points-View")
                            .replace("%player%", target.getName())
                            .replace("%amount%", String.valueOf(essenceBal))));
                    break;
            }
        }
    }

    public void argsCheck(Player player, String[] args) {
        if (args.length == 1) {
            essence.usage(player);
        } else {
            if (args.length == 2) {
                essence.usage(player);
            }
        }
    }
}



