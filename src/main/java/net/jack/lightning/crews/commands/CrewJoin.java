package net.jack.lightning.crews.commands;

import net.jack.lightning.LightningCore;
import net.jack.lightning.crews.CrewHandler;
import net.jack.lightning.crews.CrewUser;
import net.jack.lightning.crews.commandmanager.SubCommand;
import net.jack.lightning.utilities.CC;
import org.bukkit.entity.Player;

public class CrewJoin extends SubCommand {

    private final LightningCore core;
    private final CrewUser crewUser;
    private final CrewHandler crewHandler;

    public CrewJoin(LightningCore core) {
        this.core = core;
        this.crewUser = new CrewUser(core);
        this.crewHandler = new CrewHandler();
    }


    @Override
    public String getName() {
        return "join";
    }

    @Override
    public void perform(Player player, String[] args) {
        String crew = crewUser.getUserCrew(player);

        if (args.length < 1) {
            getUsage(player);
        }

        if (crew != null) {
            player.sendMessage(CC.translate(this.core.getCrewSettingsConfiguration().getString("messages.in-crew")));
            return;
        }
        if (args.length > 1) {
            if (crewHandler.getInviteList(player) == null) {
                player.sendMessage(CC.translate(this.core.getCrewSettingsConfiguration().getString("messages.no-invite").replace("%crew%", args[1])));
                return;
            }
            crewUser.setUserCrew(player, args[1], "member");
        }

    }


    @Override
    public void getUsage(Player player) {
        for (final String i : this.core.getCrewSettingsConfiguration().getStringList(("Usage"))) {
            player.sendMessage(CC.translate(i));
        }
    }
}
