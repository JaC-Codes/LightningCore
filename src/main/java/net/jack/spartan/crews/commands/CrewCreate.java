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

        if (args.length == 0) {
            for (final String i : core.getCrewSettingsConfiguration().getStringList("Crew-Settings.usage")) {
                player.sendMessage(CC.translate(i));
            }

            if (args.length < 1) {
                Crews crew = new Crews(this.core, args[1]);
                CrewUser crewUser = new CrewUser(this.core);
                if (crew.crewExists()) {
                    player.sendMessage(this.core.getCrewSettingsConfiguration().getString("messages.exists").replace("%crew", args[1]));
                    return;
                }

                if (crewUser.getUserCrew(player) != null) {
                    player.sendMessage(CC.translate(this.core.getCrewSettingsConfiguration().getString("messages.in-crew")));
                    return;
                }
                crew.createCrew(player);
                crewUser.setUserCrew(player, args[1], "boss");

                if (this.core.getCrewSettingsConfiguration().getBoolean("settings.created-broadcast")) {
                    Bukkit.broadcastMessage(CC.translate(this.core.getCrewSettingsConfiguration().getString("messages.created")
                            .replace("%team%", args[1])));
                } else {
                    player.sendMessage(CC.translate(this.core.getCrewSettingsConfiguration().getString("messages.created")
                            .replace("%team%", args[1])));
                }
            }
        }
    }
}

