package net.jack.lightning.spleef.instance;

import net.jack.lightning.LightningCore;
import net.jack.lightning.spleef.GameState;
import net.jack.lightning.spleef.handler.SpleefConfig;
import net.jack.lightning.utilities.CC;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private final Arena arena;
    private final LightningCore core;
    private final SpleefConfig spleefConfig;
    private int countdownSeconds;

    public Countdown(LightningCore core, Arena arena) {
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

        arena.sendTitle(CC.translate("&b&l" + countdownSeconds + " second" + (countdownSeconds == 1 ? "" : "s")), CC.translate("&7until the game commences"));

        countdownSeconds--;

    }

}
