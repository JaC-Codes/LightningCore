package net.jack.lightning.crews.commands;

import net.jack.lightning.LightningCore;
import net.jack.lightning.crews.CrewUser;
import net.jack.lightning.crews.commandmanager.SubCommand;
import net.jack.lightning.utilities.CC;
import org.bukkit.entity.Player;

public class CrewLeave extends SubCommand {

    private final LightningCore core;
    private final CrewUser crewUser;

    public CrewLeave(LightningCore core) {
        this.core = core;
        this.crewUser = new CrewUser(core);
    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length < 1) {
            getUsage(player);
        }

        String crew = crewUser.getUserCrew(player);
        String role = crewUser.getUserCrewRole(player);

        if (crew == null) {
            player.sendMessage(CC.translate("&7You are not in a crew!"));
            return;
        }
        if (role.equalsIgnoreCase("boss")) {
            player.sendMessage(CC.translate("&7You cannot leave when you are the boss!"));
            return;
        } else {
            crewUser.setUserCrew(player, null, null);
            player.sendMessage(CC.translate("&cYou have left your crew."));
        }
    }

    @Override
    public void getUsage(Player player) {
        for (final String i : this.core.getCrewSettingsConfiguration().getStringList(("Usage"))) {
            player.sendMessage(CC.translate(i));
        }
    }
}
