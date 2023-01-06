package net.jack.lightning.harvesterhoe.customenchants;


import net.jack.lightning.LightningCore;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class EssenceEnhancer extends Enchantment {

    private final LightningCore core;
    private final NamespacedKey hoe;

    public EssenceEnhancer(LightningCore core) {
        super(NamespacedKey.minecraft("essence_enhancer"));
        this.core = core;
        this.hoe = new NamespacedKey(this.core, "hoe");
    }


    private void essenceEnchance(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!item.getType().equals(Material.DIAMOND_HOE)) return;
        ItemMeta meta = item.getItemMeta();
        if (!meta.getPersistentDataContainer().has(hoe, PersistentDataType.STRING)) return;
    }

    @NotNull
    @Override
    public String getName() {
        return "Essence Enhancer";
    }

    @Override
    public int getMaxLevel() {
        return 500;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @NotNull
    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.TOOL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        return item.getType().equals(Material.DIAMOND_HOE);
    }
}
