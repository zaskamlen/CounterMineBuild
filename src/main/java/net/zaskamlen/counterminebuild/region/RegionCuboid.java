package net.zaskamlen.counterminebuild.region;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class RegionCuboid {
    private final World world;
    public final int minX, maxX, minY, maxY, minZ, maxZ;
    public final Location pos1;
    public final Location pos2;

    public RegionCuboid(Location pos1, Location pos2) {
        this.world = pos1.getWorld();

        int x1 = pos1.getBlockX();
        int y1 = pos1.getBlockY();
        int z1 = pos1.getBlockZ();

        int x2 = pos2.getBlockX();
        int y2 = pos2.getBlockY();
        int z2 = pos2.getBlockZ();

        this.minX = Math.min(x1, x2);
        this.maxX = Math.max(x1, x2);
        this.minY = Math.min(y1, y2);
        this.maxY = Math.max(y1, y2);
        this.minZ = Math.min(z1, z2);
        this.maxZ = Math.max(z1, z2);
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public List<Block> getBlocks() {
        List<Block> blocks = new ArrayList<>();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    blocks.add(world.getBlockAt(x, y, z));
                }
            }
        }

        return blocks;
    }

    public List<Block> outline() {
        List<Block> outline = new ArrayList<>();

        // Верхняя и нижняя грани
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                outline.add(world.getBlockAt(x, minY, z));
                outline.add(world.getBlockAt(x, maxY, z));
            }
        }

        // Боковые грани
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                outline.add(world.getBlockAt(x, y, minZ));
                outline.add(world.getBlockAt(x, y, maxZ));
            }
        }

        // Передняя и задняя грани
        for (int y = minY; y <= maxY; y++) {
            for (int z = minZ; z <= maxZ; z++) {
                outline.add(world.getBlockAt(minX, y, z));
                outline.add(world.getBlockAt(maxX, y, z));
            }
        }

        return outline;
    }

    public Location getCenter() {
        int centerX = (minX + maxX) / 2;
        int centerY = (minY + maxY) / 2;
        int centerZ = (minZ + maxZ) / 2;

        return new Location(world, centerX, centerY, centerZ);
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMaxZ() {
        return maxZ;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMinZ() {
        return minZ;
    }

    public World getWorld() {
        return world;
    }
}
