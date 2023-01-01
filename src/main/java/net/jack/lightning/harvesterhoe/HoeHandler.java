package net.jack.lightning.harvesterhoe;

import net.jack.lightning.LightningCore;
import net.jack.lightning.harvesterhoe.menus.HoeMenu;
import net.jack.lightning.utilities.CC;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class HoeHandler implements Listener {


    private final LightningCore core;
    private final NamespacedKey key;
    private final HoeMenu hoeMenu;

    public HoeHandler(LightningCore core) {
        this.core = core;
        this.key = new NamespacedKey(core, "hoe");
        this.hoeMenu = new HoeMenu(core);
    }

    public ItemStack harvesterHoe() {
        ItemStack item = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.translate(this.core.getHarvesterHoeConfiguration().getString("HarvesterHoe.item.name")));
        List<String> lore = new ArrayList<>();
        for (String l : this.core.getHarvesterHoeConfiguration().getStringList("HarvesterHoe.item.lore")) {
            lore.add(CC.translate(l));
        }
        meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE);
        meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED);
        item.addEnchantment(Enchantment.SILK_TOUCH, 1);
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

    @EventHandler
    public void onInteractWithHoe(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getEquipment() == null) return;
        ItemStack item = player.getEquipment().getItemInMainHand();
        if (!item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();
        if (meta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                player.sendMessage(CC.translate("&eOpening HarvesterHoe menu..."));
                hoeMenu.openHoeMenu(player);


            }

        }
    }

    @EventHandler
    public void inventoryInteract(InventoryInteractEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase(CC.translate(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.title")))) {
            event.setCancelled(true);
        }
     }
}
