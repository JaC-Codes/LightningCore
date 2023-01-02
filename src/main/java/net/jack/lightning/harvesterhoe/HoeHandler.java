package net.jack.lightning.harvesterhoe;

import net.jack.lightning.LightningCore;
import net.jack.lightning.crews.CrewUser;
import net.jack.lightning.harvesterhoe.essence.Essence;
import net.jack.lightning.harvesterhoe.menus.HoeMenu;
import net.jack.lightning.utilities.CC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class HoeHandler implements Listener {


    private final HashMap<UUID, ItemStack> drops = new HashMap<>();
    private final HashMap<UUID, Long> confirmationTime = new HashMap<>();


    private final LightningCore core;
    private final NamespacedKey key;
    private final HoeMenu hoeMenu;
    private final CrewUser crewUser;
    private final NamespacedKey key2;
    private final Essence essence;

    public HoeHandler(LightningCore core) {
        this.core = core;
        this.key = new NamespacedKey(core, "hoe");
        this.hoeMenu = new HoeMenu(core);
        this.crewUser = new CrewUser(core);
        this.key2 = new NamespacedKey(core, "confirm");
        this.essence = new Essence(core);
    }

    public ItemStack harvesterHoe() {
        ItemStack item = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.translate(this.core.getHarvesterHoeConfiguration().getString("HarvesterHoe.item.name")));
        List<String> lore = new ArrayList<>();
        for (String l : this.core.getHarvesterHoeConfiguration().getStringList("HarvesterHoe.item.lore")) {
            lore.add(CC.translate(l));
        }
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
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

    //Listeners

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
    public void inventoryInteract(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals(CC.translate(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.title")))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        event.getDrops().forEach(drop -> {
            if (!drop.hasItemMeta()) return;
            if (drop.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
                drops.put(player.getUniqueId(), drop);
            }
        });
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (drops.containsKey(player.getUniqueId())) {
            player.getInventory().addItem(drops.get(player.getUniqueId()));
            drops.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onCaneBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (!block.getType().equals(Material.SUGAR_CANE)) return;
        Location broke = block.getLocation();
        ItemStack item = player.getEquipment().getItemInMainHand();
        if (!item.hasItemMeta()) return;
        if (!item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) return;
        if (!broke.clone().subtract(0, 1, 0).getBlock().getType().equals(Material.SUGAR_CANE)) {
            event.setCancelled(true);
        } else {
            if (block.getType().equals(Material.SUGAR_CANE) && item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
                Random random = new Random();
                int randomEssence = random.nextInt(25, 300);
                essence.addEssence(player, randomEssence);
            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        ItemStack item = event.getItemDrop().getItemStack();
        if (!item.hasItemMeta()) return;
        if (!item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) return;
        long confirmed = System.currentTimeMillis();
        confirmationTime.put(player.getUniqueId(), confirmed);
        Long confirmedWhen = confirmationTime.get(player.getUniqueId());
        Long diff = System.currentTimeMillis() - confirmedWhen;
        int seconds = (int) (diff / 1000);
        if (pdc.has(key2, PersistentDataType.STRING)) {
            pdc.remove(key2);
            if (!confirmationTime.containsKey(player.getUniqueId())) return;
            confirmationTime.remove(player.getUniqueId());
        } else if (seconds == 10 || !pdc.has(key2, PersistentDataType.STRING)) {
            player.sendTitle(CC.translate("&a&lPress Q again to confirm"), CC.translate("&7Confirmation needed"));
            pdc.set(key2, PersistentDataType.STRING, "confirm");
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (crewUser.userExists(player)) return;
        giveHoe(player, 1);
    }
}
