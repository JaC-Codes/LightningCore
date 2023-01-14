package net.jack.lightning.staffmode.staffutils.staffchat;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class StaffChatListener implements Listener {

    private final LightningCore core;
    private final StaffChat staffChat;


    public StaffChatListener(LightningCore core) {
        this.core = core;
        this.staffChat = new StaffChat(core);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String receivedMessage = event.getMessage();
        String permCheck = this.core.getStaffModeConfiguration().getString("StaffMode.permissions.staffchat");

        if (permCheck == null) {
            System.out.println("staffchat perm null");
            return;
        }

        System.out.println("sc");
        if (core.getStaffMode().getStaffChat().contains(player.getUniqueId())) {
            event.setCancelled(true);
            for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
                if (staff.hasPermission(permCheck)) {
                    staff.sendMessage(staffChat.getStaffChatPrefix() + CC.translate(" &e") + player.getName() +
                            CC.translate("&7: " + CC.translate("&7" + receivedMessage)));
                }
            }
        }
    }
}
