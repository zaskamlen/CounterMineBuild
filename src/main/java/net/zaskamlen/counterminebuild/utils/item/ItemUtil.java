package net.zaskamlen.counterminebuild.utils.item;

import net.zaskamlen.counterminebuild.utils.text.TextUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemUtil {

    public static ItemStack item(ItemData itemData) {
        ItemStack itemStack = new ItemStack(itemData.getMaterial(), itemData.getAmount());

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(TextUtil.color(itemData.getName()));
        itemMeta.setLore(TextUtil.color(Arrays.asList(itemData.getLore())));
        itemMeta.setCustomModelData(itemData.getCustomModelData());

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
