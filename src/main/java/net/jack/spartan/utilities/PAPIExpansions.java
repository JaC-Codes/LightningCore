package net.jack.spartan.utilities;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.jack.spartan.SpartanCore;
import net.jack.spartan.crews.CrewUser;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PAPIExpansions extends PlaceholderExpansion {

    private final SpartanCore core;

    public PAPIExpansions(SpartanCore core) {
        this.core = core;
    }

    @Override
    public String getIdentifier() {
        return "spartan";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Jack";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer p, String params) {
        if (params.equalsIgnoreCase("crew")) {
            CrewUser crewUser = new CrewUser(core);
            if (crewUser.getUserCrew(p.getPlayer()) == null) {
                return "";
            }
            try {
                return crewUser.getUserCrew(p.getPlayer());
            } catch (Exception e) {
                return "";
            }
        }
        if (params.equalsIgnoreCase("points")) {
            CrewUser crewUser = new CrewUser(core);
            try {
                String.valueOf(crewUser.getUserPoints(p.getPlayer()));
            } catch (Exception e) {
                return "";
            }
        }
        return "";
    }
}





