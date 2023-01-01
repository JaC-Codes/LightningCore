package net.jack.lightning.harvesterhoe;

import net.jack.lightning.LightningCore;
import net.jack.lightning.utilities.CC;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ItemBuild {


    private final LightningCore core;
    private final NamespacedKey key;

    public ItemBuild(LightningCore core) {
        this.core = core;
        this.key = new NamespacedKey(core, "hoe");
    }

    public ItemStack harvesterHoe() {
        ItemStack item = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.translate(this.core.getHarvesterHoeConfiguration().getString("HarvesterHoe.item.name")));
        List<String> lore = new ArrayList<>();
        for (String l : this.core.getHarvesterHoeConfiguration().getStringList("HarvesterHoe.item.lore")) {
            lore.add(CC.translate(l));
        }
        item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "hoe");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public void giveHoe(Player player, int amount) {

        ItemStack item = harvesterHoe();
        item.setAmount(amount);
        player.getInventory().addItem(item);
    }
}
