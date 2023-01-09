package net.jack.lightning.harvesterhoe.customenchants;

import net.jack.lightning.utilities.CC;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public enum EnumEnchants {

    ESSENCE_ENHANCER(CC.translate("&b&lEssence Enhancer"), 1, 500, List.of(Material.DIAMOND_HOE), List.of(CC.translate(" "),
            CC.translate("&fIncrease essence finding"))),
    TOKEN_GRABBER(CC.translate("&a&lToken Grabber"), 1, 250, List.of(Material.DIAMOND_HOE), List.of(CC.translate(" "),
            CC.translate("&fIncrease Token finding"))),
    XP_GAINER(CC.translate("&e&lXP Gainer"), 1, 50, List.of(Material.DIAMOND_HOE), List.of(CC.translate(" "),
            CC.translate("&fXP Multiplier")));


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
