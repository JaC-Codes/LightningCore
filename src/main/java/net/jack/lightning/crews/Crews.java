package net.jack.lightning.crews;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.CC;
import net.jack.lightning.utilities.Config;
import org.bukkit.entity.Player;


public class Crews {

    private final LightningCore core;
    private final String crew;

    public Crews(LightningCore core, String crew) {
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

    public void listCrews(Player player) {
        player.sendMessage(CC.translate("&b---------------------------------------------------"));
        player.sendMessage(" ");
        player.sendMessage(CC.translate("&7List of crews:"));
        for (int i = 0; i < 6; i++) {
            for (String crews : this.core.getCrewConfiguration().getStringList("Crew." + crew)) {
                player.sendMessage(CC.translate("&7" + crews));
                player.sendMessage(" ");
            }
        }
        player.sendMessage(" ");
        player.sendMessage(CC.translate("&b---------------------------------------------------"));
    }
}


