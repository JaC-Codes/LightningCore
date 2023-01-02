package net.jack.lightning.harvesterhoe.menus;

import net.jack.lightning.LightningCore;
import net.jack.lightning.harvesterhoe.essence.Essence;
import net.jack.lightning.harvesterhoe.tokens.Tokens;
import net.jack.lightning.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class HoeMenu {


    private final Inventory hoeMenu;
    private final LightningCore core;
    private final Essence essence;
    private final Tokens tokens;

    public HoeMenu(LightningCore core) {
        this.core = core;
        this.essence = new Essence(core);
        this.tokens = new Tokens(core);
        hoeMenu = Bukkit.createInventory(null, this.core.getHarvesterHoeConfiguration().getInt("HoeMenu.inventory.size"),
                CC.translate(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.title")));

        ItemStack glasspane = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1, (short) 0);
        int slot;
        while ((slot = hoeMenu.firstEmpty()) != -1) hoeMenu.setItem(slot, glasspane);
    }

    public void openHoeMenu(Player player) {
        for (final String i : (this.core.getHarvesterHoeConfiguration().getConfigurationSection("HoeMenu.inventory.items").getKeys(false))) {
            String config = (this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.items." + i + ".item"));
            Material material = Material.matchMaterial(config);
            ItemStack item = new ItemStack(material);
            if (item.getItemMeta() == null) {
                System.out.println("Item meta null");
            }
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(CC.translate(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.items." + i + ".name")));
            ArrayList<String> lore = new ArrayList<>();
            for (final String l : this.core.getHarvesterHoeConfiguration().getStringList("HoeMenu.inventory.items." + i + ".lore")) {
                lore.add(CC.translate(l).replace("%essence%", String.valueOf(essence.getEssence(player)))
                        .replace("%tokens%", String.valueOf(tokens.getTokens(player))));
            }
            hoeMenu.setItem(4, playerHead(player));
            meta.setLore(lore);
            item.setItemMeta(meta);
            hoeMenu.setItem(this.core.getHarvesterHoeConfiguration().getInt("HoeMenu.inventory.items." + i + ".slot"), item);
        }

        player.openInventory(hoeMenu);
    }

    public ItemStack playerHead(Player player) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta metaskull = (SkullMeta) skull.getItemMeta();
        metaskull.setOwningPlayer(player);
        metaskull.setDisplayName(CC.translate("&6&l" + player.getName()));
        skull.setItemMeta(metaskull);
        return skull;
    }
}
