package net.jack.lightning.staffmode.mode;

import net.jack.lightning.LightningCore;
import net.jack.lightning.staffmode.itembuilder.ItemBuild;
import net.jack.lightning.utilities.CC;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ModeBuild {

    private final LightningCore core;

    private final NamespacedKey compassKey;
    private final NamespacedKey freezeKey;
    private final NamespacedKey onlineStaffKey;
    private final NamespacedKey inventoryViewKey;

    public ModeBuild(LightningCore core) {
        this.core = core;

        this.compassKey = new NamespacedKey(core, "compass");
        this.freezeKey = new NamespacedKey(core, "freeze");
        this.onlineStaffKey = new NamespacedKey(core, "onlinestaff");
        this.inventoryViewKey = new NamespacedKey(core, "inventoryview");

    }

    public ItemStack[] getStaffMode() {

        ItemStack compass = new ItemBuild(Material.COMPASS, 1).setDisplayName
                        (CC.translate(this.core.getStaffModeConfiguration().getString("StaffMode.mode.compass.name")))
                .addKey(compassKey, "compass").build();

        ItemStack freeze = new ItemBuild(Material.PACKED_ICE, 1).setDisplayName
                        (CC.translate(this.core.getStaffModeConfiguration().getString("StaffMode.mode.freeze.name")))
                .addKey(freezeKey, "freeze").build();

        ItemStack onlineStaff = new ItemBuild(Material.ENDER_EYE, 1).setDisplayName
                        (CC.translate(this.core.getStaffModeConfiguration().getString("StaffMode.mode.online-staff.name")))
                .addKey(onlineStaffKey, "onlinestaff").build();

        ItemStack inventoryView = new ItemBuild(Material.BOOK, 1).setDisplayName
                        (CC.translate(this.core.getStaffModeConfiguration().getString("StaffMode.mode.inventory-view.name")))
                .addKey(inventoryViewKey, "inventoryview").build();

        return new ItemStack[]{compass, freeze, onlineStaff, inventoryView};
    }

    public void giveStaffMode(Player player) {
        for (ItemStack item : getStaffMode()) {
            player.getInventory().setItem(this.core.getStaffModeConfiguration().getInt("StaffMode.mode.compass.slot"), getStaffMode()[0]);
            player.getInventory().setItem(this.core.getStaffModeConfiguration().getInt("StaffMode.mode.freeze.slot"), getStaffMode()[1]);
            player.getInventory().setItem(this.core.getStaffModeConfiguration().getInt("StaffMode.mode.online-staff.slot"), getStaffMode()[2]);
            player.getInventory().setItem(this.core.getStaffModeConfiguration().getInt("StaffMode.mode.inventory-view.slot"), getStaffMode()[3]);
        }
    }
}
