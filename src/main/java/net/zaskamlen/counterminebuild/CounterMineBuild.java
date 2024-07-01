package net.zaskamlen.counterminebuild;

import net.minestom.server.MinecraftServer;
import net.zaskamlen.counterminebuild.command.Executor;
import net.zaskamlen.counterminebuild.command.Tabs;
import net.zaskamlen.counterminebuild.menu.MenuListener;
import net.zaskamlen.counterminebuild.region.RegionCommand;
import net.zaskamlen.counterminebuild.region.RegionListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CounterMineBuild extends JavaPlugin {

    private static CounterMineBuild instance;

    @Override
    public void onEnable() {
        instance = this;

        MinecraftServer.init();

        Executor.createCommand("example")

                .addTabOptions("option1", Tabs.create(), (sender, args) -> {
                    sender.sendMessage("Executing option1 with arguments: " + String.join(", ", args));
                })

                .addTabOptions("option2", Tabs.create(), (sender, args) -> {
                    sender.sendMessage("Executing option2 with arguments: " + String.join(", ", args));
                })

                .addDefaultExecute((player, args) -> {
                    player.sendMessage("Информация о командах");
                    player.sendMessage("");
                    player.sendMessage("/region - список команд");
                    player.sendMessage("/region create <тип> - сохранить новый регион");
                    player.sendMessage("/region addteamspawn <тип> - добавить точку спавна для команды");
                    player.sendMessage("/region removeteamspawn <тип> - удалить точку спавна для команды");
                    player.sendMessage("/region cui <тип> - показать регион в обводке");
                    player.sendMessage("/region clearCui <тип> - убрать обводку региона");
                    player.sendMessage("/region save <название> - сохранить в конфиг регион");
                    player.sendMessage("/region convert <название> - конвертировать мир в .polar");
                    player.sendMessage("/region list <тип> - список регионов");
                    player.sendMessage("/region addCuboid <тип> - добавь новый кубоид в регион");
                    player.sendMessage("/region clearCuboids <тип> - отчистить регион от кубоидов");
                })
                .build();


        RegionCommand regionCommand = new RegionCommand();
        getCommand("region").setExecutor(regionCommand);
        getCommand("region").setTabCompleter(regionCommand);
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new RegionListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public static CounterMineBuild getInstance() {
        return instance;
    }

}
