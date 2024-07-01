package net.zaskamlen.counterminebuild.command;

import org.bukkit.permissions.Permission;

public class CommandPermission {
    private final String name;

    private CommandPermission(String name) {
        this.name = name;
    }

    public static CommandPermission of(String name) {
        return new CommandPermission(name);
    }

    public String getName() {
        return name;
    }

    public Permission getPermission() {
        return new Permission(name);
    }
}
