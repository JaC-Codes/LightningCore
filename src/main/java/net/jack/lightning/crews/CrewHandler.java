package net.jack.lightning.crews;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CrewHandler {

    private final HashMap<UUID, List<String>> inviteList = new HashMap<>();
    private final HashMap<UUID, String> invite = new HashMap<>();

    public List<String> getInviteList(Player player) {
        return inviteList.get(player.getUniqueId());
    }

    public HashMap<UUID, String> getInvite() {
        return invite;
    }

    public void mapChange(Player player, String crew) {
        if (inviteList.get(player.getUniqueId()) == null) {
            List<String> list = new ArrayList<>();
            list.add(crew);
            inviteList.put(player.getUniqueId(), list);
            invite.put(player.getUniqueId(), crew);
        } else {
            List<String> list = inviteList.get(player.getUniqueId());
            list.add(crew);
            inviteList.replace(player.getUniqueId(), inviteList.get(player.getUniqueId()), list);
            invite.put(player.getUniqueId(), crew);
        }
    }

    public void removeFromMap(Player player, String crew) {
        List<String> list = inviteList.get(player.getUniqueId());
        list.remove(crew);
        inviteList.replace(player.getUniqueId(), inviteList.get(player.getUniqueId()), list);
    }
}
