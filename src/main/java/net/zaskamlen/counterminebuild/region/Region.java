package net.zaskamlen.counterminebuild.region;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Region {

    private final RegionType type;
    private final List<RegionCuboid> cuboids;
    private static final Map<RegionType,Region> regions = new ConcurrentHashMap<>();

    public Region(RegionType type) {
        this.type = type;
        this.cuboids = new ArrayList<>();
    }

    public void addCuboid(RegionCuboid cuboid) {
        cuboids.add(cuboid);
    }

    public void removeCuboid(RegionCuboid cuboid) {
        cuboids.remove(cuboid);
    }

    public static Region getRegions(RegionType type)  {
        return regions.get(type);
    }

    public void addRegion() {
        regions.put(type, this);
    }

    public void removeRegion() {
        regions.remove(this.type, this);
    }

    public RegionType getType() {
        return type;
    }

    public void save() {

    }

    public List<RegionCuboid> getCuboids() {
        return cuboids;
    }

    public static Map<RegionType, Region> getRegions() {
        return regions;
    }
}
