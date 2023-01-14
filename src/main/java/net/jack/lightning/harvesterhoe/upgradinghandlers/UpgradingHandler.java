package net.jack.lightning.harvesterhoe.upgradinghandlers;

import net.jack.lightning.LightningCore;
import net.jack.lightning.harvesterhoe.customenchants.EnchantProfile;
import net.jack.lightning.harvesterhoe.customenchants.EnumEnchants;
import net.jack.lightning.harvesterhoe.menus.HoeMenu;
import net.jack.lightning.utilities.CC;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class UpgradingHandler {

    private final LightningCore core;
    private final EnchantProfile enchantProfile;

    private final NamespacedKey key;

    public UpgradingHandler(LightningCore core) {
        this.core = core;
        this.enchantProfile = new EnchantProfile(core);

        this.key = new NamespacedKey(core, "hoe");
    }

    public void loopInventory(Player player) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item == null) return;
            if (!(item.getType() == Material.DIAMOND_HOE)) return;
            ItemMeta hoeMeta = item.getItemMeta();
            if (hoeMeta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
                List<String> lore = new ArrayList<>();
                for (final String l : this.core.getHarvesterHoeConfiguration().getStringList("HarvesterHoe.item.lore")) {
                    lore.add(CC.translate(l)
                            .replace("%tokengrabber%", EnumEnchants.TOKEN_GRABBER.getDisplayName())
                            .replace("%tokenlevel%", String.valueOf(enchantProfile.getTokenGrabberLevel(player)))
                            .replace("%essenceenhancer%", EnumEnchants.ESSENCE_ENHANCER.getDisplayName())
                            .replace("%essencelevel%", String.valueOf(enchantProfile.getEssenceEnhanceLevel(player))));
                }
                hoeMeta.setLore(lore);
                item.setItemMeta(hoeMeta);
            }
        }
    }

  //  public void updater(Player player) {
    //    hoeMenu.getHoeMenu().remove(Material.valueOf(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.essence-enhancer.item")));
     //   hoeMenu.getHoeMenu().remove(Material.valueOf(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.token-grabber.item")));
     //   hoeMenu.getHoeMenu().remove(Material.valueOf(this.core.getHarvesterHoeConfiguration().getString("HoeMenu.inventory.xp-gainer.item")));

     //   hoeMenu.getHoeMenu().setItem(this.core.getHarvesterHoeConfiguration().getInt("HoeMenu.inventory.essence-enhancer.slot"),
      //          hoeMenuHandler.essenceEnhancer(player));
     //   hoeMenu.getHoeMenu().setItem(this.core.getHarvesterHoeConfiguration().getInt("HoeMenu.inventory.token-grabber.slot"),
      //          hoeMenuHandler.tokenGrabber(player));
     //   hoeMenu.getHoeMenu().setItem(this.core.getHarvesterHoeConfiguration().getInt("HoeMenu.inventory.xp-gainer.slot"),
      //          hoeMenuHandler.tokenGrabber(player));

 //       hoeMenu.updateHoeMenu(player);
  //  }
}
