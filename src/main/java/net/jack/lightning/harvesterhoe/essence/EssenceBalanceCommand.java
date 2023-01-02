package net.jack.lightning.harvesterhoe.essence;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EssenceBalanceCommand implements CommandExecutor {



    private final LightningCore core;
    private final Essence essence;

    public EssenceBalanceCommand(LightningCore core) {
        this.core = core;
        this.essence = new Essence(core);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        if (args.length == 0) {
            int ess = essence.getEssence(player);
            player.sendMessage(CC.translate("&bEssence: &f" + String.valueOf(ess)));
            return true;
        }


        return false;
    }
}
