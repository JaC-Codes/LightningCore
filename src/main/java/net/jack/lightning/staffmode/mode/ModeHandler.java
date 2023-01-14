package net.jack.lightning.staffmode.mode;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.CC;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ModeHandler {


    private final LightningCore core;
    private final ModeBuild modeBuild;

    public ModeHandler(LightningCore core) {
        this.core = core;
        this.modeBuild = new ModeBuild(core);
    }

    public void enterStaffMode(Player player) {
        core.getStaffMode().getSTAFF().add(player.getUniqueId());
        core.getStaffMode().getInventorySaver().put(player.getUniqueId(), player.getInventory().getContents());
        player.getInventory().clear();
        modeBuild.giveStaffMode(player);
        //Need to make vanish
        clearArmor(player);
        /// Make toggle vanish
        player.setGameMode(GameMode.CREATIVE);
        player.setFlying(true);
        player.setGlowing(true);
        player.sendMessage(CC.translate(core.getPrefix() + " &bStaff Mode enabled."));

    }

    public void exitStaffMode(Player player) {
        core.getStaffMode().getSTAFF().remove(player.getUniqueId());
        player.getInventory().setContents(core.getStaffMode().getInventorySaver().get(player.getUniqueId()));

        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
        player.setGlowing(false);
        player.sendMessage(CC.translate(core.getPrefix() + " &bStaff Mode disabled."));

    }

    public void clearArmor(Player player) {
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);

    }
}
