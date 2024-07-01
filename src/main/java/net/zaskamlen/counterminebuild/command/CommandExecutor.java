package net.zaskamlen.counterminebuild.command;

public interface CommandExecutor {
    CommandExecutor addTabOptions(String option, Tabs tabs, CommandExecutorWrapper executor);

    CommandExecutor addDefaultExecute(CommandExecutorWrapper executor);

    CommandExecutor permission(String option, CommandPermission permission);

    void build();
}