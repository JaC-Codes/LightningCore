package net.jack.spartan.crews.commands;

import net.jack.spartan.SpartanCore;
import net.jack.spartan.crews.CrewUser;
import net.jack.spartan.crews.Crews;
import net.jack.spartan.crews.commandmanager.SubCommand;
import net.jack.spartan.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CrewCreate extends SubCommand {


    private final SpartanCore core;

    public CrewCreate(SpartanCore core) {
        this.core = core;
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length < 2) {
            getUsage(player);
            return;
        }
            Crews crew = new Crews(this.core, args[1]);
            CrewUser crewUser = new CrewUser(this.core);
            if (crew.crewExists()) {
                player.sendMessage(CC.translate(this.core.getCrewSettingsConfiguration().getString("messages.exists")).replace("%crew%", args[1]));
                return;
            }
            if (crewUser.getUserCrew(player) != null) {
                player.sendMessage(CC.translate(this.core.getCrewSettingsConfiguration().getString("messages.in-crew")));
                return;
            }

            crew.createCrew(player);
            crewUser.setUserCrew(player, args[1], "boss");

            if (this.core.getCrewSettingsConfiguration().getBoolean("settings.created-broadcast")) {
                Bukkit.broadcastMessage(CC.translate(this.core.getCrewSettingsConfiguration().getString(CC.translate("messages.created"))
                        .replace("%crew%", args[1])));
            } else {
                player.sendMessage(CC.translate(this.core.getCrewSettingsConfiguration().getString(CC.translate("messages.created"))
                        .replace("%crew%", args[1])));
            }
        }

    @Override
    public void getUsage(Player player) {
        for (final String i : this.core.getCrewSettingsConfiguration().getStringList(("Usage"))) {
            player.sendMessage(CC.translate(i));
        }
    }
}


