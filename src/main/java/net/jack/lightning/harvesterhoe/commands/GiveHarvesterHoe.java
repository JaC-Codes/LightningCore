package net.jack.lightning.harvesterhoe.commands;

import net.jack.lightning.LightningCore;
import net.jack.lightning.harvesterhoe.ItemBuild;
import net.jack.lightning.harvesterhoe.commandsmanager.SubCommand;
import net.jack.lightning.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GiveHarvesterHoe extends SubCommand {

    private final LightningCore core;
    private final ItemBuild itemBuild;

    public GiveHarvesterHoe(LightningCore core) {
        this.core = core;
        this.itemBuild = new ItemBuild(core);
    }

    @Override
    public String getName() {
        return "hoe";
    }

    // Harvesterhoe hoe give <player> <amount> : 3 args length of 4
    @Override
    public void perform(Player player, String[] args) {
        if (args.length < 4) {
            getUsage(player);
            return;
        }
        if (!args[1].equalsIgnoreCase("give")) return;
        Player give = Bukkit.getPlayer(args[2]);
        if (give != null) {
            int amount = Integer.parseInt(args[3]);
            itemBuild.giveHoe(give, amount);

        }
    }

        @Override
        public void getUsage (Player player){
        player.sendMessage(CC.translate(core.getPrefix() + "&7Usage: &e/harvesterhoe give <player> <amount>"));

        }
    }
