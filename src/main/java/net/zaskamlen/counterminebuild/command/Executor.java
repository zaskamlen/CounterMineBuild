package net.zaskamlen.counterminebuild.command;

import net.zaskamlen.counterminebuild.CounterMineBuild;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Executor {
    private final String name;
    private final Map<String, CommandExecutorWrapper> actions;
    private final Tabs tabs;

    private Executor(String name) {
        this.name = name;
        this.actions = new HashMap<>();
        this.tabs = Tabs.create();
    }

    public static Executor createCommand(String name) {
        return new Executor(name);
    }

    public Executor addTabOptions(String option, Tabs tabs, CommandExecutorWrapper executor) {
        this.tabs.addTabs(option, tabs);
        this.actions.put(option, executor);
        return this;
    }

    public Executor addDefaultExecute(CommandExecutorWrapper executor) {
        this.actions.put("", executor);  // Используем пустую строку как ключ для действия по умолчанию
        return this;
    }

    public void build() {
        PluginCommand commandAdder = CounterMineBuild.getInstance().getCommand(name);
        if (commandAdder == null) {
            CounterMineBuild.getInstance().getLogger().severe("Команда '" + name + "' не найдена!");
            return;
        }

        // Устанавливаем исполнителя команды
        commandAdder.setExecutor((sender, command, label, args) -> {
            try {
                if (args.length < 1) {
                    if (actions.containsKey("")) {
                        if (sender instanceof Player player) {
                            actions.get("").execute(player, args);
                        }

                        return true;
                    } else {
                        sender.sendMessage("Недостаточно аргументов!");
                        return false;
                    }
                }

                String option = args[0];
                if (actions.containsKey(option)) {
                    String[] subArgs = new String[args.length - 1];
                    System.arraycopy(args, 1, subArgs, 0, args.length - 1);

                    if (sender instanceof Player player) {
                        actions.get(option).execute(player, subArgs);
                    }
                    return true;
                } else {
                    sender.sendMessage("Неизвестный вариант команды: " + option);
                }
            } catch (NullPointerException e) {
                sender.sendMessage("Произошла внутренняя ошибка при выполнении команды.");
                e.printStackTrace();
            } catch (Exception e) {
                sender.sendMessage("Произошла ошибка при выполнении команды.");
                e.printStackTrace();
            }
            return false;
        });

        // Устанавливаем автодополнитель
        commandAdder.setTabCompleter((sender, command, alias, args) -> {
            try {
                if (args.length == 1) {
                    return tabs.getTabs();
                } else if (args.length > 1 && actions.containsKey(args[0])) {
                    return tabs.getTabs(args[0]);
                }
            } catch (NullPointerException e) {
                sender.sendMessage("Произошла внутренняя ошибка при автодополнении.");
                e.printStackTrace();
            } catch (Exception e) {
                sender.sendMessage("Произошла ошибка при автодополнении.");
                e.printStackTrace();
            }
            return null; // Возвращаем null для использования стандартного автодополнения
        });
    }
}
