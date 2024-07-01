package net.zaskamlen.counterminebuild.utils.item;

import org.bukkit.Material;

public class ItemData {

    private final String name;
    private final int amount;
    private final int customModelData;
    private final Material material;
    private String[] lore;

    public ItemData(Material material, int customModelData, int amount, String name, String... lore) {
        this.lore = lore;
        this.material = material;
        this.customModelData = customModelData;
        this.amount = amount;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public Material getMaterial() {
        return material;
    }

    public String[] getLore() {
        return lore;
    }

    public void setLore(String[] lore) {
        this.lore = lore;
    }
}
