package net.jack.spartan.crews.commands;

import net.jack.spartan.SpartanCore;
import net.jack.spartan.crews.Crews;
import net.jack.spartan.crews.commandmanager.SubCommand;
import net.jack.spartan.utilities.CC;
import org.bukkit.entity.Player;

public class CrewList extends SubCommand {

    private final SpartanCore core;
    private final Crews crews;

    public CrewList(SpartanCore core) {
        this.core = core;
        this.crews = new Crews(core, null);
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length < 1) {
            getUsage(player);
        }

        crews.listCrews(player);


    }

    @Override
    public void getUsage(Player player) {
        for (final String i : this.core.getCrewSettingsConfiguration().getStringList(("Usage"))) {
            player.sendMessage(CC.translate(i));
        }
    }
}
