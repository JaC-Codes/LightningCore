package net.jack.lightning.staffmode.staffutils.onlinestaffviewer;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class OnlineStaff {

    private final LightningCore core;
    private final Inventory onlineStaff;


    public OnlineStaff(LightningCore core) {
        this.core = core;
        onlineStaff = Bukkit.createInventory(null, this.core.getStaffModeConfiguration().getInt("StaffMode.online-staff.inventory.size"),
                CC.translate(this.core.getStaffModeConfiguration().getString("StaffMode.online-staff.inventory.title")));
    }


    public void openOnlineStaff(Player player) {
        inventoryFiller();
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (!core.getStaffMode().getSTAFF().contains(player.getUniqueId())) continue;
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setOwningPlayer(players);
            meta.setDisplayName(CC.translate("&c" + players.getName()));
            skull.setItemMeta(meta);
            if (onlineStaff.contains(skull)) continue;
            int i = onlineStaff.firstEmpty();
            onlineStaff.setItem(i, skull);

            }
        player.openInventory(onlineStaff);
        }

    public void inventoryFiller() {
        int invSize = (this.core.getStaffModeConfiguration().getInt("StaffMode.online-staff.inventory.size"));
        ItemStack item = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        int rowCount = invSize / 9;
        for (int i = 0; i < invSize; i++) {
            int row = i / 9;
            int column = (i % 9) + 1;

            if (row == 0 || row == rowCount - 1 || column == 1 || column == 9) {
                onlineStaff.setItem(i, item);
            }
        }
    }

    // 0, 2, 1, 9 (36 slots)
    // 0 tick, 1,
}
