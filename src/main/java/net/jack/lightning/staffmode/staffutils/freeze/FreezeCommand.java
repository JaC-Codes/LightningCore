package net.jack.lightning.staffmode.staffutils.freeze;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FreezeCommand implements CommandExecutor {

    private final LightningCore core;
    private final Freeze freeze;

    public FreezeCommand(LightningCore core) {
        this.core = core;
        this.freeze = new Freeze(core);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        String permCheck = (this.core.getStaffModeConfiguration().getString("StaffMode.permissions.freeze"));

        if (permCheck == null) {
            System.out.println("Perm check for freeze not found");
        }

        if (!sender.hasPermission(permCheck)) {
            sender.sendMessage(CC.translate("&cYou do not have permission to use this command."));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(CC.translate("&cUsage: /freeze <player>"));
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage(CC.translate("&cPlayer was not found"));
        } else {
            var playerFrozen = core.getStaffMode().getFrozen();
            var targetUI = target.getUniqueId();
            if (!playerFrozen.contains(targetUI)) {
                playerFrozen.add(targetUI);
                freeze.frozenMessage(target);
                sender.sendMessage(CC.translate("&7You have frozen &e" + target.getName() + "&7."));
                return true;
            } else {
                playerFrozen.remove(targetUI);
                target.sendMessage(CC.translate("&7You have been &eunfrozen&7."));
                sender.sendMessage(CC.translate("&7You have unfrozen &e" + target.getName() + "&7."));
            }
        }
        return false;
    }
}
