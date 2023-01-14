package net.jack.lightning.staffmode.mode;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ModeCommand implements CommandExecutor {


    private final LightningCore core;

    public ModeCommand(LightningCore core) {
        this.core = core;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        String permCheck = this.core.getStaffModeConfiguration().getString("StaffMode.permissions.mode");

        if (permCheck == null) {
            System.out.println("Cannot find permission for mode");
        }

        if (!(player.hasPermission(permCheck))) {
            player.sendMessage(CC.translate("&cYou do not have permission to use this command."));
        }

        if (!core.getModeHandler().containsStaffArray(player.getUniqueId())) {
            core.getModeHandler().enterStaffMode(player);
            return true;
        } else {
            core.getModeHandler().exitStaffMode(player);
        }

        return false;
    }
}
