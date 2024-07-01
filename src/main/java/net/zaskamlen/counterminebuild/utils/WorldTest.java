package net.zaskamlen.counterminebuild.utils;

import net.hollowcube.polar.AnvilPolar;
import net.hollowcube.polar.PolarWorld;
import net.hollowcube.polar.PolarWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WorldTest {

    public void convert() {
        String pathStr = "E:/Desktop/CounterMine Shit/maps/worlds/gd_rialto/v2";
        Path path = Paths.get(pathStr);
        Path resultPath = Paths.get(pathStr + ".polar");

        try {
            PolarWorld polarWorld = AnvilPolar.anvilToPolar(path);
            byte[] polarWorldBytes = PolarWriter.write(polarWorld);
            Files.write(resultPath, polarWorldBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
