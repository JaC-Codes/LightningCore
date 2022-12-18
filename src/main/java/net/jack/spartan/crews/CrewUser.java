package net.jack.spartan.crews;

import net.jack.spartan.SpartanCore;
import net.jack.spartan.utilities.Config;
import org.bukkit.entity.Player;

public class CrewUser {

    private final SpartanCore core;

    public CrewUser(SpartanCore core) {
        this.core = core;
    }

    public String getUserCrew(Player player) {
        if (!this.core.getCrewUserConfiguration().contains("Player." + player.getUniqueId())) {
            return null;
        }
        return this.core.getCrewUserConfiguration().getString("Player." + player.getUniqueId()) + ".crew";
    }

    public void setUserCrew(Player player, String crew, String role) {
        this.core.getCrewUserConfiguration().set("Player." + player.getUniqueId() + ".crew", crew);
        this.core.getCrewUserConfiguration().set("Player." + player.getUniqueId() + ".crew", role);

        new Config(this.core.getCrews(), this.core.getCrewConfiguration(), core);
    }

    public String getUserCrewRole(Player player) {
        return this.core.getCrewUserConfiguration().getString("Player." + player.getUniqueId() + ".role");
    }

    public void leaveCrew(Player player) {
        this.core.getCrewUserConfiguration().set("Player." + player.getUniqueId(), null);

        new Config(this.core.getCrews(), this.core.getCrewConfiguration(), core);
    }
}
