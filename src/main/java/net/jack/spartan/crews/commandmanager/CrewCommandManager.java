package net.jack.spartan.crews.commandmanager;

import net.jack.spartan.SpartanCore;
import net.jack.spartan.crews.commands.CrewCreate;
import net.jack.spartan.utilities.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CrewCommandManager implements CommandExecutor {

    private final ArrayList<SubCommand> subCommands = new ArrayList<>();
    private final SpartanCore core;

    public CrewCommandManager(SpartanCore core) {
        this.core = core;
        subCommands.add(new CrewCreate(core));
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
                for (final String i : this.core.getCrewSettingsConfiguration().getStringList("Crew-Settings.usage")) {
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
