package net.jack.lightning.harvesterhoe;

import net.jack.lightning.LightningCore;
import net.jack.lightning.crews.CrewUser;
import net.jack.lightning.harvesterhoe.customenchants.EnchantProfile;
import net.jack.lightning.harvesterhoe.customenchants.EnumEnchants;
import net.jack.lightning.harvesterhoe.essence.Essence;
import net.jack.lightning.harvesterhoe.menus.MainMenu;
import net.jack.lightning.harvesterhoe.tokens.Tokens;
import net.jack.lightning.utilities.CC;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
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
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class HoeHandler implements Listener {


    private final HashMap<UUID, ItemStack> drops = new HashMap<>();
    private final HashMap<UUID, Long> confirmationTime = new HashMap<>();


    private final LightningCore core;
    private final NamespacedKey key;
    private final MainMenu mainMenu;
    private final CrewUser crewUser;
    private final NamespacedKey key2;
    private final NamespacedKey key3;
    private final Essence essence;
    private final Tokens tokens;
    private final EnchantProfile enchantProfile;

    private boolean autoSell = false;

    public HoeHandler(LightningCore core) {
        this.core = core;
        this.key = new NamespacedKey(core, "hoe");
        this.mainMenu = new MainMenu(core);
        this.crewUser = new CrewUser(core);
        this.key2 = new NamespacedKey(core, "confirm");
        this.essence = new Essence(core);
        this.tokens = new Tokens(core);
        this.key3 = new NamespacedKey(core, "asenabled");
        this.enchantProfile = new EnchantProfile(core);
    }

    public ItemStack harvesterHoe(Player player) {
        ItemStack item = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.translate(this.core.getHarvesterHoeConfiguration().getString("HarvesterHoe.item.name")));
        List<String> lore = new ArrayList<>();
        for (String l : this.core.getHarvesterHoeConfiguration().getStringList("HarvesterHoe.item.lore")) {
            lore.add(CC.translate(l).replace("%tokengrabber%", EnumEnchants.TOKEN_GRABBER.getDisplayName())
                    .replace("%tokenlevel%", String.valueOf(enchantProfile.getTokenGrabberLevel(player)))
                    .replace("%essenceenhancer%", EnumEnchants.ESSENCE_ENHANCER.getDisplayName())
                    .replace("%essencelevel%", String.valueOf(enchantProfile.getEssenceEnhanceLevel(player))));
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

        ItemStack item = harvesterHoe(player);
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
                mainMenu.openMainMenu(player);
            }

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
        int essenceEnhanceLevel = enchantProfile.getEssenceEnhanceLevel(player);
        int tokenGrabberLevel = enchantProfile.getTokenGrabberLevel(player);
        Block block = event.getBlock();
        Material material = event.getBlock().getType();
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
                int bound = ThreadLocalRandom.current().nextInt(essenceEnhanceLevel);
                int randomEssence = random.nextInt(25, bound + 100);
                essence.addEssence(player, randomEssence);
                int bound2 = ThreadLocalRandom.current().nextInt(tokenGrabberLevel);
                int randomTokens = random.nextInt(1, bound2 + 10);
                tokens.addTokens(player, randomTokens);

                int findAutoSell = this.core.getHarvesterHoeConfiguration().getInt("AutoSell.Cane-Sell-Amount");
                Economy eco = this.core.getEconomy();
                PersistentDataContainer pdc = player.getPersistentDataContainer();
                final int[] scAmount = {0};
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!pdc.has(key3, PersistentDataType.STRING)) return;
                        for (int i = 0; i < player.getInventory().getSize(); i++) {
                            ItemStack item = player.getInventory().getItem(i);
                            if (item == null) return;
                            if (item.getType() == Material.SUGAR_CANE) {
                                scAmount[0] = item.getAmount();
                                item.setAmount(scAmount[0]);
                                player.getInventory().remove(item);
                                int finalValue = scAmount[0] * findAutoSell;
                                EconomyResponse response = eco.depositPlayer(player, finalValue);
                                player.sendMessage(CC.translate("&8&l** &a&lAUTO SELL &8&l** &fYou have sold " + scAmount[0] + " sugarcane for $" + finalValue));
                            }
                        }
                    }
                }.runTaskTimer(core, 400, 400);

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
