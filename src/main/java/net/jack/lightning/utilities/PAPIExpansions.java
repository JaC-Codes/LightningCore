package net.jack.lightning.utilities;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.jack.lightning.LightningCore;
import net.jack.lightning.crews.CrewUser;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PAPIExpansions extends PlaceholderExpansion {

    private final LightningCore core;

    public PAPIExpansions(LightningCore core) {
        this.core = core;
    }

    @Override
    public String getIdentifier() {
        return "lightning";
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
                return "null";
            }
        }
        if (params.equalsIgnoreCase("points")) {
            CrewUser crewUser = new CrewUser(core);
            if (crewUser.getUserCrew(p.getPlayer()) == null) {
                return "";
            }
            try {
                return String.valueOf(crewUser.getUserPoints(p.getPlayer()));
            } catch (Exception e) {
                return "null";
            }
        }
        return "";
    }
}





