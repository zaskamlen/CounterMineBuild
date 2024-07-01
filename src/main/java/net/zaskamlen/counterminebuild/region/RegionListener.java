package net.zaskamlen.counterminebuild.region;

import net.zaskamlen.counterminebuild.utils.text.TextUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class RegionListener implements Listener {

    private final List<RegionCuboid> selectedCuboids = new ArrayList<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        RegionPlayer regionPlayer = new RegionPlayer(player);
        regionPlayer.add();
    }

    @EventHandler
    public void onWand(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        RegionPlayer regionPlayer = RegionPlayer.getPlayer(player);

        if (event.getItem() != null && event.getItem().getType().equals(Material.GOLDEN_AXE)) {
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock == null) {
                return; // если блок не был щелкнут, просто выходим
            }
            Location clickedLocation = clickedBlock.getLocation();

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                regionPlayer.setPos2(clickedLocation);
                player.sendMessage(TextUtil.color("&aВы установили вторую точку!"));
            } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                regionPlayer.setPos1(clickedLocation);
                player.sendMessage(TextUtil.color("&aВы установили первую точку!"));
            }

            // Очистка предыдущего выделения
            clearSelectedCuboids();

            // Создание нового выделения
            Location pos1 = regionPlayer.getPos1();
            Location pos2 = regionPlayer.getPos2();
            if (pos1 != null && pos2 != null) {
                RegionCuboid cuboid = new RegionCuboid(pos1, pos2);
                selectedCuboids.add(cuboid);
                RegionCUI.showCUI(cuboid); // Показ выделения кубика
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (event.getItemDrop().getItemStack().getType().equals(Material.GOLDEN_AXE)) {
            // Очистка текущего выделения при выкидывании топора
            clearSelectedCuboids();
            player.sendMessage(TextUtil.color("&aВы удалили обводку региона!"));
            event.setCancelled(true);
        }
    }

    // Метод для очистки текущего выделения
    private void clearSelectedCuboids() {
        for (RegionCuboid cuboid : selectedCuboids) {
            RegionCUI.hideCUI(cuboid); // Удаление выделения кубика
        }
        selectedCuboids.clear();
    }
}
