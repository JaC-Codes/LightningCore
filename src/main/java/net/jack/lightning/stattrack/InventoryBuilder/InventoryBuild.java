package net.jack.lightning.stattrack.InventoryBuilder;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InventoryBuild {

    private final LightningCore core;
    private final Inventory topKills;


    public InventoryBuild(LightningCore core) {
        this.core = core;
        topKills = Bukkit.createInventory(null, this.core.getConfig().getInt("TopKills.inventory.size"),
                CC.translate(this.core.getConfig().getString("TopKills.inventory.title")));
    }



    public void openTopKills(Player player) {

        for (final String i : this.core.getConfig().getConfigurationSection("TopKills.inventory.items").getKeys(false)) {
            String findConf = this.core.getConfig().getString("Topkills.inventory.items." + i + ".item");

            if (findConf == null) {
                System.out.println("Error can't find item config section for TopKills");
            }

            Material material = Material.matchMaterial(findConf);

            if (material == null) {
                System.out.println("Can't find topkill material");
            }

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            if (meta == null) {
                System.out.println("Null meta top kills");
            }
            meta.setDisplayName(CC.translate(this.core.getConfig().getString("TopKills.inventory.items." + ".name")));
            List<String> lore = new ArrayList<>();
            for (String l : this.core.getConfig().getStringList("TopKills.inventory.items." + i + ".lore")) {
                lore.add(CC.translate(l));
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
            topKills.setItem(this.core.getConfig().getInt("TopKills.inventory.items." + i + ".slot"), item);
        }

        player.openInventory(topKills);
    }
}
