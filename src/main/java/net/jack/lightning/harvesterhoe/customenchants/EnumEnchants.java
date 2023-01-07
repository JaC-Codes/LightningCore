package net.jack.lightning.harvesterhoe.customenchants;

import net.jack.lightning.utilities.CC;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum EnumEnchants {

    ESSENCE_ENHANCER("Essence Enhancer", 1, 500, List.of(Material.DIAMOND_HOE), List.of(CC.translate("&fIncrease essence finding"))),
    TOKEN_GRABBER("Token Grabber", 1, 250, List.of(Material.DIAMOND_HOE), List.of(CC.translate("&fIncrease token finding")));


    private final String key;
    private final int startLevel;
    private final int maxLevel;
    private final List<Material> itemSupport;
    private final List<String> info;

    EnumEnchants(String key, int startLevel, int maxLevel, List<Material> itemSupport, List<String> info) {
        this.key = key;
        this.startLevel = startLevel;
        this.maxLevel = maxLevel;
        this.itemSupport = itemSupport;
        this.info = info;
    }

    public String getKey() {
        return this.key;
    }

    public String getDisplayName() {
        return this.getKey().toUpperCase();
    }

    public int getStartLevel() {
        return  this.startLevel;
    }

    public int getMaxLevel() {
        return  this.maxLevel;
    }

    public List<Material> getItemSupport() {
        List<Material> isList = new ArrayList<>(this.itemSupport);
        return isList;
    }
}
