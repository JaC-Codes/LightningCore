package net.jack.lightning.crews.commandmanager;

import org.bukkit.entity.Player;

public abstract class SubCommand {

    public abstract String getName();


    public abstract void perform(Player player, String[] args);

    public abstract void getUsage(Player player);
}

