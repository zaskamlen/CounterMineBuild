package net.zaskamlen.counterminebuild.utils.text;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class TextUtil {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&',message);
    }


    public static List<String> color(List<String> messages) {
        return messages.stream().map(TextUtil::color).collect(Collectors.toList());
    }

}
