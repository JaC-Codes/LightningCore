package net.jack.lightning.staffmode.staffutils.onlinestaffviewer;

import net.jack.lightning.LightningCore;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class OnlineStaffListener implements Listener {

    private final LightningCore core;
    private final NamespacedKey onlineStaffKey;
    private final OnlineStaff onlineStaff;

    public OnlineStaffListener(LightningCore core) {
        this.core = core;
        this.onlineStaffKey = new NamespacedKey(core, "onlinestaff");
        this.onlineStaff = new OnlineStaff(core);
    }

    // (onlineStaffKey, "onlinestaff").build();

    @EventHandler
    public void osInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        var action = event.getAction().equals(Action.RIGHT_CLICK_AIR) || (event.getAction().equals(Action.RIGHT_CLICK_BLOCK));
        ItemMeta meta = item.getItemMeta();
        if (!meta.getPersistentDataContainer().has(onlineStaffKey, PersistentDataType.STRING)) return;
        if (action) {
            onlineStaff.openOnlineStaff(player);
        }
    }
}
