package net.jack.lightning.crews.commands;

import net.jack.lightning.LightningCore;
import net.jack.lightning.crews.CrewUser;
import net.jack.lightning.utilities.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Points implements CommandExecutor {

    private final LightningCore core;
    private final CrewUser crewUser;

    public Points(LightningCore core) {
        this.core = core;
        this.crewUser = new CrewUser(core);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (args.length == 0) {
            int points = crewUser.getUserPoints(player);
            sender.sendMessage(CC.translate("&ePoints Balance: &7" + String.valueOf(points)));
        }
        return false;
    }
}
