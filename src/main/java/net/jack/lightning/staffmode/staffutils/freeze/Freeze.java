package net.jack.lightning.staffmode.staffutils.freeze;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.CC;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Freeze {

    private final List<UUID> frozen = new ArrayList<>();

    private final LightningCore core;

    public Freeze(LightningCore core) {
        this.core = core;
    }

    public void frozenMessage(Player target) {
        target.sendMessage(CC.translate("&f█████████"));
        target.sendMessage(CC.translate("&f████&c█&f████"));
        target.sendMessage(CC.translate("&f███&c█&0█&c█&f███"));
        target.sendMessage(CC.translate("&f██&c█&6█&0█&6█&c█&f██"));
        target.sendMessage(CC.translate("&f██&c█&6█&0█&6█&c█&f██ &eYou have been frozen by a staff member."));
        target.sendMessage(CC.translate("&f██&c█&6█&0█&6█&c█&f██ &eIf you disconnect you will be &4&lBANNED&e."));
        target.sendMessage(CC.translate("&f█&c█&6█████&c█&f█ &ePlease connect to our TeamSpeak."));
        target.sendMessage(CC.translate("&c█&6███&0█&6███&c█ &7input ip"));
        target.sendMessage(CC.translate("&c█████████"));
        target.sendMessage(CC.translate("&f█████████"));
    }

    public List<UUID> getFrozen() {
        return frozen;
    }

    public void disconnectedWhileFrozen(Player player) {
        TextComponent startof = new TextComponent(CC.translate("&7" + player.getDisplayName() + " &7has logged out while &cFrozen&7. "));
        TextComponent clickable = new TextComponent(CC.translate("&7(&eClick to ban&7)"));

        clickable.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ban " + player.getDisplayName()));
        clickable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(CC.translate("&7Click to ban &e" + player.getDisplayName()))));
        startof.addExtra(clickable);
        player.spigot().sendMessage(startof);
    }

}
