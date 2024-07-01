package net.zaskamlen.counterminebuild.region;

import net.zaskamlen.counterminebuild.menu.Menu;
import net.zaskamlen.counterminebuild.utils.item.ItemData;
import net.zaskamlen.counterminebuild.utils.item.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RegionMenu extends Menu {
    public RegionMenu(RegionType type) {
        super("Регионы " + type.name(), 54);

        Region.getRegions().forEach((regionType, region) -> {
            if (regionType.equals(type)) {
                addItem(ItemUtil.item(new ItemData(Material.GRASS_BLOCK,1,1,"","&fТип &e" + region.getType().name(),"&fКоличество кубоидов &e" + region.getCuboids().size())));
            }
        });

    }

    @Override
    public void onClick(int slot, ItemStack item, Player player) {

    }
}
