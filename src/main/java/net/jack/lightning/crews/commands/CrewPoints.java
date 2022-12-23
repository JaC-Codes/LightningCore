package net.jack.lightning.crews.commands;

import net.jack.lightning.LightningCore;
import net.jack.lightning.crews.CrewUser;
import net.jack.lightning.crews.Crews;
import net.jack.lightning.crews.commandmanager.SubCommand;
import net.jack.lightning.utilities.CC;
import org.bukkit.entity.Player;

public class CrewPoints extends SubCommand {

    private final LightningCore core;
    private final Crews crews;
    private String crew = null;
    private final CrewUser crewUser;

    public CrewPoints(LightningCore core) {
        this.core = core;
        this.crews = new Crews(core, null);
        this.crewUser = new CrewUser(core);
    }

    @Override
    public String getName() {
        return "points";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length < 1) {
            getUsage(player);
        } else {
            if (crewUser.getUserCrew(player) == null) {
                player.sendMessage(CC.translate("&7You are not in a crew!"));
                return;
            }
            int points = crews.getCrewPoints();
            player.sendMessage(CC.translate("&bCrew Points Balance: &7" + String.valueOf(points)));
        }
    }

    @Override
    public void getUsage(Player player) {
        for (final String i : this.core.getCrewSettingsConfiguration().getStringList(("Usage"))) {
            player.sendMessage(CC.translate(i));
        }
    }
}

