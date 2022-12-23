package net.jack.lightning.crews.commandmanager;

import net.jack.lightning.LightningCore;
import net.jack.lightning.crews.commands.*;
import net.jack.lightning.utilities.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CrewCommandManager implements CommandExecutor {

    private final ArrayList<SubCommand> subCommands = new ArrayList<>();
    private final LightningCore core;

    public CrewCommandManager(LightningCore core) {
        this.core = core;
        subCommands.add(new CrewCreate(core));
        subCommands.add(new CrewDisband(core));
        subCommands.add(new CrewLeave(core));
        subCommands.add(new CrewList(core));
        subCommands.add(new CrewPoints(core));
        subCommands.add(new CrewInvite(core));
        subCommands.add(new CrewJoin(core));
    }

    @Override
    @SuppressWarnings("ALL")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;

            if (args.length > 0){
                for (int i = 0; i < getSubcommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())){
                        getSubcommands().get(i).perform(player, args);
                    }
                }
            }else if(args.length == 0){
                for (final String i : this.core.getCrewSettingsConfiguration().getStringList("Usage")) {
                    player.sendMessage(CC.translate(i));
                }
            }

        }


        return true;
    }

    public ArrayList<SubCommand> getSubcommands(){
        return subCommands;
    }
}
