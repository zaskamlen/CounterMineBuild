package net.zaskamlen.counterminebuild.utils.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Locations {

    public static Location of(String world,double x, double y, double z) {
        return new Location(Bukkit.getWorld(world), x, y, z);
    }

}
