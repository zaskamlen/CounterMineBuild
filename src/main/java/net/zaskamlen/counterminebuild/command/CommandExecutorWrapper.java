package net.zaskamlen.counterminebuild.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface CommandExecutorWrapper {
    void execute(Player sender, String[] args);
}
