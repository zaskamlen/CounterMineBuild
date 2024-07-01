package net.zaskamlen.counterminebuild.world;

import net.hollowcube.polar.AnvilPolar;
import net.hollowcube.polar.PolarWorld;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class World {

    public static void convert(String world) {
        String path = "C:/Users/tolya/OneDrive/Рабочий стол/CountermineBuild/" + world;
        Path anvilPath = Paths.get(path);
        Path resultPath = Paths.get(path + ".polar");

        try {
            PolarWorld polarWorldBytes = AnvilPolar.anvilToPolar(anvilPath);
            Files.write(resultPath, polarWorldBytes.userData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
