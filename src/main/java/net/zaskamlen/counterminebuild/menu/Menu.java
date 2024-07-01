package net.zaskamlen.counterminebuild.menu;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class Menu implements MenuManager {

    private final String title;
    private final int size;
    private final Inventory inventory;
    private static final Map<String,Menu> menuMap = new HashMap<>();

    protected Menu(String title, int size) {
        this.title = title;
        this.size = size;
        this.inventory = Bukkit.createInventory(null,size, title);
        menuMap.put(title,this);
    }

    @Override
    public void setItem(int slot, ItemStack item) {
        this.inventory.setItem(slot,item);
    }

    @Override
    public void addItem(ItemStack... items) {
        this.inventory.addItem(items);
    }

    @Override
    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    public static Menu getMenu(String title) {
        return menuMap.get(title);
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    public Inventory getInventory() {
        return inventory;
    }
}