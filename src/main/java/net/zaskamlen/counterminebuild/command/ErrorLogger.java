package net.zaskamlen.counterminebuild.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ErrorLogger {

    private static final ChatColor ERROR_COLOR = ChatColor.RED;
    private static final ChatColor WARNING_COLOR = ChatColor.YELLOW;
    private static final ChatColor INFO_COLOR = ChatColor.GREEN;
    private static final ChatColor DEFAULT_COLOR = ChatColor.WHITE;

    public static void logError(CommandSender sender, String message) {
        sender.sendMessage(ERROR_COLOR + "Error: " + DEFAULT_COLOR + message);
    }

    public static void logWarning(CommandSender sender, String message) {
        sender.sendMessage(WARNING_COLOR + "Warning: " + DEFAULT_COLOR + message);
    }

    public static void logInfo(CommandSender sender, String message) {
        sender.sendMessage(INFO_COLOR + "Info: " + DEFAULT_COLOR + message);
    }

    public static void logException(CommandSender sender, Exception e) {
        sender.sendMessage(ERROR_COLOR + "An exception occurred: " + e.getClass().getName());
        e.printStackTrace();
    }
}
