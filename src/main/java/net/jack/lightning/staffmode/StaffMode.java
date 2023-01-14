package net.jack.lightning.staffmode;

import net.jack.lightning.LightningCore;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class StaffMode {


    private final List<UUID> frozen = new ArrayList<>();
    private List<UUID> staffChat = new ArrayList<>();
    public HashMap<UUID, ItemStack[]> inventorySaver = new HashMap<>();
    private final List<UUID> STAFF = new ArrayList<>();



    private final LightningCore core;

    public List<UUID> getFrozen() {
        return frozen;
    }

    public List<UUID> getStaffChat() {
        return staffChat;
    }

    public HashMap<UUID, ItemStack[]> getInventorySaver() {
        return inventorySaver;
    }

    public List<UUID> getSTAFF() {
        return STAFF;
    }

    public StaffMode(LightningCore core) {
        this.core = core;
    }


}
