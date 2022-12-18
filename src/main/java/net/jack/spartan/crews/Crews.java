package net.jack.spartan.crews;

import net.jack.spartan.SpartanCore;
import net.jack.spartan.utilities.Config;
import org.bukkit.entity.Player;


public class Crews {

    private final SpartanCore core;
    private final String crew;

    public Crews(SpartanCore core, String crew) {
        this.core = core;
        this.crew = String.valueOf(crew);
    }

    public void createCrew(Player boss) {
        this.core.getCrewConfiguration().set("Crew." + crew + ".boss", boss.getUniqueId().toString());
        this.core.getCrewConfiguration().set("Crew." + crew + ".points", 0);

        new Config(this.core.getCrews(), this.core.getCrewConfiguration(), core);

    }

    public boolean crewExists() {
        return this.core.getCrewConfiguration().contains("Crew." + crew);
    }

    public void disband() {
        this.core.getCrewConfiguration().set("Crew." + crew, null);

        new Config(this.core.getCrews(), this.core.getCrewConfiguration(), core);
    }

    public void crewRename(String name) {
        this.core.getCrewConfiguration().getString("Crew." + crew).replace(crew, name);

        new Config(this.core.getCrews(), this.core.getCrewConfiguration(), core);
    }

    public int getCrewPoints() {
        return this.core.getCrewConfiguration().getInt("Crew." + crew + ".points");
    }

    public void setCrewPoints(int amount) {
        this.core.getCrewConfiguration().set("Crew." + crew + ".points", amount);

        new Config(this.core.getCrews(), this.core.getCrewConfiguration(), core);
    }

}
