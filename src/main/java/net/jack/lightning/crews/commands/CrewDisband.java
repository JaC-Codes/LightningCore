package net.jack.lightning.crews.commands;

import net.jack.lightning.LightningCore;
import net.jack.lightning.crews.CrewUser;
import net.jack.lightning.crews.Crews;
import net.jack.lightning.crews.commandmanager.SubCommand;
import net.jack.lightning.utilities.CC;
import org.bukkit.entity.Player;

public class CrewDisband extends SubCommand {

    private final LightningCore core;
    private final CrewUser crewUser;

    public CrewDisband(LightningCore core) {
        this.core = core;
        this.crewUser = new CrewUser(core);
    }

    @Override
    public String getName() {
        return "disband";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length < 1) {
            getUsage(player);
        }

        String crewName = crewUser.getUserCrew(player);
        String crewRole = crewUser.getUserCrewRole(player);
        if (crewName == null) {
            player.sendMessage(CC.translate(this.core.getCrewSettingsConfiguration().getString("messages.no-crew")));
        }
        if (!crewRole.equalsIgnoreCase("boss")) {
            player.sendMessage(CC.translate(this.core.getCrewSettingsConfiguration().getString("messages.boss-permission")));
            return;
        }
        new Crews(core, crewName).disband();
        crewUser.setUserCrew(player, null, null);
        player.sendMessage(CC.translate(this.core.getCrewSettingsConfiguration().getString("messages.disbanded")
                    .replace("%crew%", crewName)));
        crewUser.getUserCrew(player);

    }

    @Override
    public void getUsage(Player player) {
        for (final String i : this.core.getCrewSettingsConfiguration().getStringList(("Usage"))) {
            player.sendMessage(CC.translate(i));
        }
    }
}
