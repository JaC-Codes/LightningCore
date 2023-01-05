package net.jack.lightning.autobroadcaster;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Reloader implements CommandExecutor {

    private final LightningCore core;

    public Reloader(LightningCore core) {
        this.core = core;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            sender.sendMessage(CC.translate("&cUsage: &e/lc reload"));
        }
        if (args.length == 1) {
            core.reloadHarvesterHoe();
            core.reloadConfig();
            sender.sendMessage(CC.translate(core.getPrefix() + " &eConfiguration reloaded."));
        }


        return false;
    }
}
