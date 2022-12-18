package net.jack.spartan.serverutils;

import net.jack.spartan.SpartanCore;
import net.jack.spartan.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class SpartanBoard implements Listener {

    private final SpartanCore core;

    public SpartanBoard(SpartanCore core) {
        this.core = core;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("scoreboard", "spartan");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(CC.translate(this.core.getConfig().getString("Scoreboard.Title")));


        Score space1 = obj.getScore(" ");
        space1.setScore(11);

        Score field2 = obj.getScore(CC.translate("&ePoints: &c" + player.getName() + " &f(%spartan_points%)"));
        field2.setScore(10);

        Score space2 = obj.getScore("  ");
        space2.setScore(9);

        Score field3 = obj.getScore(CC.translate("&eCrew: &7%spartan_crew%" ));
        field3.setScore(8);

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

        player.setScoreboard(board);
    }
}
