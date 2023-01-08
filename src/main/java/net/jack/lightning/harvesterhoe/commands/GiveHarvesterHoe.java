package net.jack.lightning.harvesterhoe.commands;

import net.jack.lightning.LightningCore;
import net.jack.lightning.harvesterhoe.HoeHandler;
import net.jack.lightning.harvesterhoe.commandsmanager.SubCommand;
import net.jack.lightning.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GiveHarvesterHoe extends SubCommand {

    private final LightningCore core;
    private final HoeHandler itemBuild;

    public GiveHarvesterHoe(LightningCore core) {
        this.core = core;
        this.itemBuild = new HoeHandler(core);
    }

    @Override
    public String getName() {
        return "hoe";
    }

    // Harvesterhoe hoe give <player> <amount> : 3 args length of 4
    @Override
    public void perform(Player player, String[] args) {
        if (args.length < 4) {
                for (final String i : this.core.getHarvesterHoeConfiguration().getStringList("Usage")) {
                    player.sendMessage(CC.translate(i));
                }
            return;
        }
        if (!args[1].equalsIgnoreCase("give")) return;
        Player give = Bukkit.getPlayer(args[2]);
        if (give != null) {
            int amount = Integer.parseInt(args[3]);
            itemBuild.giveHoe(give, amount);

        }
    }
    }
