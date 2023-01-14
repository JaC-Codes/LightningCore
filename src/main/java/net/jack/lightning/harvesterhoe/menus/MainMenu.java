package net.jack.lightning.harvesterhoe.menus;

import net.jack.lightning.LightningCore;
import net.jack.lightning.harvesterhoe.essence.Essence;
import net.jack.lightning.harvesterhoe.tokens.Tokens;
import net.jack.lightning.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {


    private final Inventory mainMenu;
    private final LightningCore core;
    private final Essence essence;
    private final Tokens tokens;
    private final NamespacedKey guiHoeKey;

    public MainMenu(LightningCore core) {
        this.core = core;
        this.essence = new Essence(core);
        this.tokens = new Tokens(core);
        this.guiHoeKey = new NamespacedKey(core, "guihoe");
        mainMenu = Bukkit.createInventory(null, this.core.getHarvesterHoeConfiguration().getInt("MainMenu.inventory.size"),
                CC.translate(this.core.getHarvesterHoeConfiguration().getString("MainMenu.inventory.title")));

        ItemStack glasspane = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1, (short) 0);
        int slot;
        while ((slot = mainMenu.firstEmpty()) != -1) mainMenu.setItem(slot, glasspane);
    }

    public void openMainMenu(Player player) {
        for (final String i : (this.core.getHarvesterHoeConfiguration().getConfigurationSection("MainMenu.inventory.items").getKeys(false))) {
            String config = (this.core.getHarvesterHoeConfiguration().getString("MainMenu.inventory.items." + i + ".item"));
            Material material = Material.matchMaterial(config);
            ItemStack item = new ItemStack(material);
            if (item.getItemMeta() == null) {
                System.out.println("Item meta null");
            }
            ItemMeta meta = item.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.setDisplayName(CC.translate(this.core.getHarvesterHoeConfiguration().getString("MainMenu.inventory.items." + i + ".name")));
            ArrayList<String> lore = new ArrayList<>();
            for (final String l : this.core.getHarvesterHoeConfiguration().getStringList("MainMenu.inventory.items." + i + ".lore")) {
                lore.add(CC.translate(l).replace("%essence%", String.valueOf(essence.getEssence(player)))
                        .replace("%tokens%", String.valueOf(tokens.getTokens(player))));
            }
            mainMenu.setItem(4, playerHead(player));
            mainMenu.setItem(this.core.getHarvesterHoeConfiguration().getInt("MainMenu.inventory.hoe-slot"), hardCodeHoe(player));
            meta.setLore(lore);
            item.setItemMeta(meta);
            mainMenu.setItem(this.core.getHarvesterHoeConfiguration().getInt("MainMenu.inventory.items." + i + ".slot"), item);
        }

        player.openInventory(mainMenu);
    }

    public ItemStack playerHead(Player player) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta metaskull = (SkullMeta) skull.getItemMeta();
        metaskull.setOwningPlayer(player);
        metaskull.setDisplayName(CC.translate("&6&l" + player.getName()));
        skull.setItemMeta(metaskull);
        return skull;
    }

    public ItemStack hardCodeHoe(Player player) {
        ItemStack item = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(guiHoeKey, PersistentDataType.STRING, "guihoe");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(CC.translate(this.core.getHarvesterHoeConfiguration().getString("MainMenu.inventory.hoe-name")));
        List<String> lore = new ArrayList<>();
        for (final String l : this.core.getHarvesterHoeConfiguration().getStringList("MainMenu.inventory.hoe-lore")) {
            lore.add(CC.translate(l).replace("%essence%", String.valueOf(essence.getEssence(player)))
                    .replace("%tokens%", String.valueOf(tokens.getTokens(player))));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
