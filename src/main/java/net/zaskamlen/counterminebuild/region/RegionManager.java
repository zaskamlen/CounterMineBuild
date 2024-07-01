package net.zaskamlen.counterminebuild.region;

import net.zaskamlen.counterminebuild.CounterMineBuild;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RegionManager {

    private final String fileName;
    private final File configFile;
    private final YamlConfiguration config;

    public RegionManager(String fileName) {
        this.configFile = new File(CounterMineBuild.getInstance().getDataFolder(), fileName + ".yml");
        this.config = YamlConfiguration.loadConfiguration(configFile);
        this.fileName = fileName;
    }

    public void saveRegionData(Map<RegionType, Region> regions) {
        for (RegionType type : regions.keySet()) {
            Region region = regions.get(type);
            List<RegionCuboid> cuboids = region.getCuboids();

            for (int i = 0; i < cuboids.size(); i++) {
                RegionCuboid cuboid = cuboids.get(i);
                Location pos1 = cuboid.getPos1();
                Location pos2 = cuboid.getPos2();

                config.set("regions." + type.name() + ".cuboids." + i + ".pos1.x", pos1.getX());
                config.set("regions." + type.name() + ".cuboids." + i + ".pos1.y", pos1.getY());
                config.set("regions." + type.name() + ".cuboids." + i + ".pos1.z", pos1.getZ());

                config.set("regions." + type.name() + ".cuboids." + i + ".pos2.x", pos2.getX());
                config.set("regions." + type.name() + ".cuboids." + i + ".pos2.y", pos2.getY());
                config.set("regions." + type.name() + ".cuboids." + i + ".pos2.z", pos2.getZ());
            }
        }

        try {
            File saveFile = new File(configFile.getParentFile(), fileName + ".yml");
            config.save(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveTeamSpawnLocation(RegionType type, Location location) {
        config.set("teamspawns." + type.name() + ".world", location.getWorld().getName());
        config.set("teamspawns." + type.name() + ".x", location.getX());
        config.set("teamspawns." + type.name() + ".y", location.getY());
        config.set("teamspawns." + type.name() + ".z", location.getZ());

        saveConfig();
    }

    public void removeTeamSpawnLocation(RegionType type) {
        config.set("teamspawns." + type.name(), null);

        saveConfig();
    }

    public boolean removeTeamSpawnLocation(RegionType type, double x, double y, double z) {
        String spawnPath = "teamspawns." + type.name();

        if (config.isSet(spawnPath + ".x") && config.isSet(spawnPath + ".y") && config.isSet(spawnPath + ".z")) {
            double configX = config.getDouble(spawnPath + ".x");
            double configY = config.getDouble(spawnPath + ".y");
            double configZ = config.getDouble(spawnPath + ".z");

            if (configX == x && configY == y && configZ == z) {
                config.set(spawnPath, null);
                saveConfig();
                return true;
            }
        }

        System.out.println("No matching team spawn location found for RegionType " + type.name() + " at coordinates (" + x + ", " + y + ", " + z + ").");
        return false;
    }


    private void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
