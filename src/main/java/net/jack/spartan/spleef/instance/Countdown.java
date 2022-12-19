package net.jack.spartan.spleef.instance;

import net.jack.spartan.SpartanCore;
import net.jack.spartan.spleef.GameState;
import net.jack.spartan.spleef.handler.SpleefConfig;
import net.jack.spartan.utilities.CC;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private final Arena arena;
    private final SpartanCore core;
    private final SpleefConfig spleefConfig;
    private int countdownSeconds;

    public Countdown(SpartanCore core, Arena arena) {
        this.core = core;
        this.arena = arena;
        this.spleefConfig = new SpleefConfig(core);
        this.countdownSeconds = spleefConfig.getCountdownSeconds();
    }

    public void start() {
        arena.setState(GameState.COUNTDOWN);
        runTaskTimer(core, 0, 20);
    }

    @Override
    public void run() {

        if (countdownSeconds == 0) {
            cancel();
            arena.start();
            return;
        }

        if (countdownSeconds <= 10 || countdownSeconds % 10 == 0) {

            arena.sendMessage(CC.translate("&bGame will start in &c" + countdownSeconds + " &bsecond" + (countdownSeconds == 1 ? "" : "s") + "."));
        }

        arena.sendTitle(CC.translate("&b&l" + countdownSeconds + " second" + (countdownSeconds == 1 ? "" : "s")), "&7until game commences.");

        countdownSeconds--;

    }

}
