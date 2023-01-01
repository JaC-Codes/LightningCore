package net.jack.lightning.serverutils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

public class LightningBoard implements Listener {

    private final LightningCore core;

    public LightningBoard(LightningCore core) {
        this.core = core;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("scoreboard", "lightning");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(CC.translate(this.core.getConfig().getString("Scoreboard.Title")));


        Score space1 = obj.getScore(" ");
        space1.setScore(11);

        Score space2 = obj.getScore("  ");
        space2.setScore(9);


        Score space3 = obj.getScore("   ");
        space3.setScore(7);

        Score field4 = obj.getScore(CC.translate("&bCrew Top: "));
        field4.setScore(6);
        Score crew1 = obj.getScore(CC.translate("&61: &f<Crew1> <Points>"));
        crew1.setScore(5);
        Score crew2 = obj.getScore(CC.translate("&72: &f<Crew2> <Points>"));
        crew2.setScore(4);
        Score crew3 = obj.getScore(CC.translate("&c3: &f<Crew3> <Points>"));
        crew3.setScore(3);

        Score space4 = obj.getScore("    ");
        space4.setScore(2);


        Score field1 = obj.getScore(CC.translate("            &cPLAY.TEST.NET"));
        field1.setScore(1);

        Team crew = board.registerNewTeam("crew");
        crew.addEntry(ChatColor.AQUA.toString());
        crew.setPrefix(CC.translate("&eCrew: "));
        crew.setSuffix(CC.translate(PlaceholderAPI.setPlaceholders(player, "&7" + "%lightning_crew%")));
        obj.getScore(ChatColor.AQUA.toString()).setScore(8);

        Team points = board.registerNewTeam("points");
        points.addEntry(ChatColor.BOLD.toString());
        points.setPrefix(CC.translate("&ePoints: "));
        points.setSuffix(CC.translate(PlaceholderAPI.setPlaceholders(player, "&c" + player.getName() + " &f(" + "%lightning_points%" + ")")));
        obj.getScore(ChatColor.BOLD.toString()).setScore(10);

        player.setScoreboard(board);
    }
}
