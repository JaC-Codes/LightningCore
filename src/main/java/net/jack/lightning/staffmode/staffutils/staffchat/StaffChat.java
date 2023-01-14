package net.jack.lightning.staffmode.staffutils.staffchat;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.CC;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StaffChat {

    private final LightningCore core;



    public StaffChat(LightningCore core) {
        this.core = core;
    }

    public String getStaffChatPrefix() {
        return CC.translate(this.core.getStaffModeConfiguration().getString("StaffMode.prefix"));
    }

}
