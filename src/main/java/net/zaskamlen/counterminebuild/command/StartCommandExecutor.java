package net.zaskamlen.counterminebuild.command;

import org.bukkit.entity.Player;

public class StartCommandExecutor implements CommandExecutorWrapper {
    @Override
    public void execute(Player player, String[] args) {
        player.sendMessage("Команда start выполнена!");
    }
}