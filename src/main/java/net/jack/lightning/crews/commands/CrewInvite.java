package net.jack.lightning.crews.commands;

import net.jack.lightning.LightningCore;
import net.jack.lightning.crews.CrewHandler;
import net.jack.lightning.crews.CrewUser;
import net.jack.lightning.crews.commandmanager.SubCommand;
import net.jack.lightning.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CrewInvite extends SubCommand {

    private final CrewUser crewUser;
    private final LightningCore core;
    private final CrewHandler crewHandler;

    public CrewInvite(LightningCore core) {
        this.crewUser = new CrewUser(core);
        this.core = core;
        this.crewHandler = new CrewHandler();
    }


    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public void perform(Player player, String[] args) {


        if (args.length < 1) {
            getUsage(player);
        }

         String crew = crewUser.getUserCrew(player);
         String role = crewUser.getUserCrewRole(player);

         if (crew == null) {
             player.sendMessage(CC.translate(this.core.getCrewSettingsConfiguration().getString("messages.no-crew")));
             return;
         }

         if (role.equalsIgnoreCase("boss")) {
             if (args.length > 1) {
                 Player invite = Bukkit.getPlayer(args[1]);
                 assert invite != null;
                 if (invite == null) {
                     player.sendMessage(CC.translate("&7Player is not online"));
                 }
                 if (crewUser.getUserCrew(invite).equals(crew)) {
                     player.sendMessage(CC.translate(this.core.getCrewSettingsConfiguration().getString("messages.in-crew")));
                     return;
                 }

                 if (!crewHandler.getInviteList(player).contains(crew)) {
                     player.sendMessage(CC.translate("&cThis player is already invited."));
                 }

                 invite.sendMessage(CC.translate(this.core.getCrewSettingsConfiguration().getString("messages.invitation").replace("%crew%", crew)));
                 crewHandler.getInvite().put(invite.getUniqueId(), crew);
             } else {
                 player.sendMessage(CC.translate("&7Cannot find this player."));
             }
         } else {
             player.sendMessage(CC.translate(this.core.getCrewSettingsConfiguration().getString("messages.boss-permission")));
         }

    }

    @Override
    public void getUsage(Player player) {
        for (final String i : this.core.getCrewSettingsConfiguration().getStringList(("Usage"))) {
            player.sendMessage(CC.translate(i));
        }
    }
}
