package net.jack.lightning.crews;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.Config;
import org.bukkit.entity.Player;

public class CrewUser {

    private final LightningCore core;

    public CrewUser(LightningCore core) {
        this.core = core;
    }

    public String getUserCrew(Player player) {
        if (!this.core.getCrewUserConfiguration().contains("Player." + player.getUniqueId())) {
            return null;
        }
        return this.core.getCrewUserConfiguration().getString("Player." + player.getUniqueId() + ".crew");
    }

    public void setUserCrew(Player player, String crew, String role) {
        this.core.getCrewUserConfiguration().set("Player." + player.getUniqueId() + ".crew", crew);
        this.core.getCrewUserConfiguration().set("Player." + player.getUniqueId() + ".role", role);

        new Config(this.core.getCrewUser(), this.core.getCrewUserConfiguration(), core);
    }

    public String getUserCrewRole(Player player) {
        return this.core.getCrewUserConfiguration().getString("Player." + player.getUniqueId() + ".role");
    }

    public void leaveCrew(Player player) {
        this.core.getCrewUserConfiguration().set("Player." + player.getUniqueId(), null);

        new Config(this.core.getCrewUser(), this.core.getCrewUserConfiguration(), core);
    }

    public int getUserPoints(Player player) {
        return this.core.getCrewUserConfiguration().getInt("Player." + player.getUniqueId() + ".points");
    }

    public void setUserPoints(Player player, int amount) {
        this.core.getCrewUserConfiguration().set("Player." + player.getUniqueId() + ".points", amount);
    }

    public boolean userExists(Player player) {
        return this.core.getCrewUserConfiguration().contains("Player." + player.getUniqueId());
    }

    public void createUser(Player player) {
        this.core.getCrewUserConfiguration().set("Player." + player.getUniqueId() + ".points", 0);
        new Config(this.core.getCrewUser(), this.core.getCrewUserConfiguration(), core);
    }

    public void addUserPoints(Player player, int amount) {
        int points = getUserPoints(player);
        this.core.getCrewUserConfiguration().set("Player." + player.getUniqueId() + ".points", (points + amount));
        new Config(this.core.getCrewUser(), this.core.getCrewUserConfiguration(), core);
    }

    public void removeUserPoints(Player player, int amount) {
        int points = getUserPoints(player);
        if (points >= amount) {
            this.core.getCrewUserConfiguration().set("Player." + player.getUniqueId() + ".points", (points - amount));
        } else {
            setUserPoints(player, 0);
        }
        new Config(this.core.getCrewUser(), this.core.getCrewUserConfiguration(), core);
    }

}



