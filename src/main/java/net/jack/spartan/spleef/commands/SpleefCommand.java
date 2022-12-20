package net.jack.spartan.spleef.commands;

import net.jack.spartan.SpartanCore;
import net.jack.spartan.spleef.GameState;
import net.jack.spartan.spleef.instance.Arena;
import net.jack.spartan.utilities.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpleefCommand implements CommandExecutor {


    private final SpartanCore core;

    public SpleefCommand(SpartanCore core) {
        this.core = core;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                player.sendMessage(CC.translate("&bThese are the available arenas: "));
                for (Arena arena : core.getSpleefHandler().getArenas()) {
                    player.sendMessage(CC.translate("&f&l- " + arena.getId() + " &b(" + arena.getState() + "&b)"));
                }

            } else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {

                Arena arena = core.getSpleefHandler().getArena(player);
                if (arena != null) {
                    player.sendMessage(CC.translate("&bYou have left the arena!"));
                    arena.removePlayer(player);
                } else {
                    player.sendMessage(CC.translate("&cYou are not in an arena!"));
                }

            } else if (args.length == 2 && args[0].equalsIgnoreCase("join")) {

                if (core.getSpleefHandler().getArena(player) != null) {
                    player.sendMessage(CC.translate("&cYou are already in an arena!"));
                    return false;
                }

                int id;
                try {
                    id = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage(CC.translate("&cYou have not entered an arena id!"));
                    return false;
                }

                if (id >= 0 && id < core.getSpleefHandler().getArenas().size()) {

                    Arena arena = core.getSpleefHandler().getArena(id);
                    if (arena.getState() == GameState.RECRUITING || arena.getState() == GameState.COUNTDOWN) {
                        player.sendMessage(CC.translate("&bYou are now playing in &f&lArena " + id));
                        arena.addPlayer(player);
                    } else {
                        player.sendMessage(CC.translate("&cThis game is currently live."));
                    }


                } else {
                    player.sendMessage(CC.translate("&cYou have not entered an arena id!"));
                }



            } else {
                player.sendMessage(usage(player));
            }
        }













        return false;
    }

    public String usage(Player player) {

        for (String usage : this.core.getSpleefConfiguration().getStringList("Usage")) {
            player.sendMessage(CC.translate(usage));
        }

        return null;
    }
}
