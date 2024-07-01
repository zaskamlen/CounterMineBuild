package net.zaskamlen.counterminebuild.world;

import net.hollowcube.polar.AnvilPolar;
import net.hollowcube.polar.PolarWorld;
import net.hollowcube.polar.PolarWriter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Logger;

public class WorldConverter {

    private static final Logger logger = JavaPlugin.getProvidingPlugin(WorldConverter.class).getLogger();

    public void convert(String world) {
        Path anvilPath = Paths.get(world);
        Path resultPath = Paths.get(world + ".polar");

        try {
            Path tempDir = Files.createTempDirectory("bukkitWorldTemp");

            copyDirectory(anvilPath, tempDir);

            PolarWorld polarWorld = AnvilPolar.anvilToPolar(tempDir);
            byte[] polarWorldBytes = PolarWriter.write(polarWorld);

            Files.write(resultPath, polarWorldBytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

            // Optionally delete the temporary directory
            deleteDirectory(tempDir);

        } catch (IOException e) {
            logger.severe("Error during conversion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void copyDirectory(Path source, Path target) throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path targetDir = target.resolve(source.relativize(dir));
                Files.createDirectories(targetDir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (!file.getFileName().toString().equals("session.lock")) {
                    Files.copy(file, target.resolve(source.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void deleteDirectory(Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
