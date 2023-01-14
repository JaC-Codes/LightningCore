package net.jack.lightning.staffmode.staffutils.staffchat;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChatCommand implements CommandExecutor {



    private final LightningCore core;


    public StaffChatCommand(LightningCore core) {
        this.core = core;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        String permCheck = this.core.getStaffModeConfiguration().getString("StaffMode.permissions.staffchat");

        if (permCheck == null) {
            System.out.println("staffchat perm null");
        }

        if (!player.hasPermission(permCheck)) {
            player.sendMessage(CC.translate("&cYou do not have permission to use this command."));
        }

        if (args.length == 0) {
            if (core.getStaffMode().getStaffChat().contains(player.getUniqueId())) {
                core.getStaffMode().getStaffChat().remove(player.getUniqueId());
                player.sendMessage(CC.translate(this.core.getStaffModeConfiguration().getString("StaffMode.messages.staffchat-disabled")));
                return true;
            } else {
                core.getStaffMode().getStaffChat().add(player.getUniqueId());
                player.sendMessage(CC.translate(this.core.getStaffModeConfiguration().getString("StaffMode.messages.staffchat-enabled")));
            }
        }
        return false;
    }
}
