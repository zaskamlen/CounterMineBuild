package net.zaskamlen.counterminebuild.region;

import org.bukkit.Material;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class RegionCUI {

    // Хранилище выделенных кубоидов и их соответствующих ItemDisplay
    private static final Map<RegionCuboid, ItemDisplay> highlightedCuboids = new HashMap<>();

    public static void showCUI(RegionCuboid cuboid) {
        // Создание нового ItemDisplay для кубоида
        ItemDisplay display = cuboid.getCenter().toCenterLocation().getWorld().spawn(cuboid.getCenter().toCenterLocation(), ItemDisplay.class);
        display.setItemStack(new ItemStack(Material.LIME_STAINED_GLASS));
        display.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.NONE);

        Vector3f scale = getItemDisplayScale(cuboid, false);

        display.setTransformation(new Transformation(new Vector3f(), new Quaternionf(), new Vector3f(0,0,0), new Quaternionf()));
        display.setInterpolationDelay(20);
        display.setInterpolationDuration(20);
        display.setTransformation(new Transformation(new Vector3f(), new Quaternionf(), scale, new Quaternionf()));
        display.setPersistent(false);

        highlightedCuboids.put(cuboid, display); // Добавление в хранилище
    }

    public static void hideCUI(RegionCuboid cuboid) {
        ItemDisplay display = highlightedCuboids.remove(cuboid); // Удаление из хранилища
        if (display != null) {
            display.remove(); // Удаление ItemDisplay
        }
    }

    private static Vector3f getItemDisplayScale(RegionCuboid cuboid, boolean invert) {
        // Вычисление длины по каждой оси
        float xLength = (cuboid.getMaxX() - cuboid.getMinX() + 1.0f);
        float yLength = (cuboid.getMaxY() - cuboid.getMinY() + 1.0f);
        float zLength = (cuboid.getMaxZ() - cuboid.getMinZ() + 1.0f);

        // Установка масштаба с возможностью инвертирования
        float xScale = invert ? -xLength : xLength;
        float yScale = invert ? -yLength : yLength;
        float zScale = invert ? -zLength : zLength;

        // Немного увеличим масштаб, чтобы избежать артефактов
        float epsilon = 0.001f;
        xScale += invert ? -epsilon : epsilon;
        yScale += invert ? -epsilon : epsilon;
        zScale += invert ? -epsilon : epsilon;

        return new Vector3f(xScale, yScale, zScale);
    }
}
