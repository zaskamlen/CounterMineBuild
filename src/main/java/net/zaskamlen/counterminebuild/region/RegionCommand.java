package net.zaskamlen.counterminebuild.region;

import net.zaskamlen.counterminebuild.utils.text.TextUtil;
import net.zaskamlen.counterminebuild.world.WorldConverter;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegionCommand implements CommandExecutor, TabCompleter {

    private static final List<String> COMMANDS = Arrays.asList(
            "create", "cui", "clearCui", "save", "convert", "list", "addCuboid", "clearCuboids", "addteamspawn", "removeteamspawn"
    );

    private RegionManager regionManager;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            RegionPlayer regionPlayer = RegionPlayer.getPlayer(player);
            if (args.length == 0) {
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
            } else {
                switch (args[0].toLowerCase()) {
                    case "create":
                        handleCreate(player, args, regionPlayer);
                        break;
                    case "cui":
                        handleCui(player, args);
                        break;
                    case "clearcui":
                        handleClearCui(player, args);
                        break;
                    case "list":
                        handleList(player, args);
                        break;
                    case "addcuboid":
                        handleAddCuboid(player, args, regionPlayer);
                        break;
                    case "clearcuboids":
                        handleClearCuboids(player, args);
                        break;
                    case "save":
                        handleSave(player, args);
                        break;
                    case "addteamspawn":
                        handleAddTeamSpawn(player, args);
                        break;
                    case "removeteamspawn":
                        handleRemoveTeamSpawn(player, args);
                        break;
                    case "convert":
                        handleConvert(player, args);
                        break;
                    default:
                        player.sendMessage(TextUtil.color("&cНеизвестная команда"));
                        break;
                }
            }
        }
        return true;
    }

    private void handleAddTeamSpawn(Player player, String[] args) {

        if (regionManager == null) {
            player.sendMessage(TextUtil.color("&cНе загружен конфиг"));
            return;
        }

        if (args.length == 2) {
            RegionType type;
            try {
                type = RegionType.valueOf(args[1]);
            } catch (IllegalArgumentException e) {
                player.sendMessage(TextUtil.color("&cНеверный тип региона. Допустимые значения: CT_SPAWN, T_SPAWN"));
                return;
            }
            Location location = player.getLocation();

            if (type == RegionType.CT_SPAWN || type == RegionType.T_SPAWN) {
                regionManager.saveTeamSpawnLocation(type, location);
                player.sendMessage(TextUtil.color("&aУспех! Вы добавили точку спауна для &e" + type.name()));
            } else {
                player.sendMessage(TextUtil.color("&cЭтот тип региона не поддерживает добавление точек спауна команды."));
            }
        } else {
            player.sendMessage(TextUtil.color("&cНедостаточно аргументов"));
            player.sendMessage(TextUtil.color("&cИспользование: /region addteamspawn <тип>"));
        }
    }

    private void handleRemoveTeamSpawn(Player player, String[] args) {

        if (regionManager == null) {
            player.sendMessage(TextUtil.color("&cНе загружен конфиг"));
            return;
        }

        if (args.length == 2 || args.length == 5) {
            RegionType type;
            try {
                type = RegionType.valueOf(args[1]);
            } catch (IllegalArgumentException e) {
                player.sendMessage(TextUtil.color("&cНеверный тип региона. Допустимые значения: CT_SPAWN, T_SPAWN"));
                return;
            }

            if (type == RegionType.CT_SPAWN || type == RegionType.T_SPAWN) {
                if (args.length == 2) {
                    // Remove by type only
                    regionManager.removeTeamSpawnLocation(type);
                    player.sendMessage(TextUtil.color("&aУспех! Вы удалили все точки спауна для &e" + type.name()));
                } else if (args.length == 5) {
                    // Remove by type and specific coordinates
                    try {
                        double x = Double.parseDouble(args[2]);
                        double y = Double.parseDouble(args[3]);
                        double z = Double.parseDouble(args[4]);

                        // Validate if this location exists and remove it
                        if (regionManager.removeTeamSpawnLocation(type, x, y, z)) {
                            player.sendMessage(TextUtil.color("&aУспех! Вы удалили точку спауна для &e" + type.name() + " на координатах (" + x + ", " + y + ", " + z + ")"));
                        } else {
                            player.sendMessage(TextUtil.color("&cТакой точки спауна для &e" + type.name() + " на координатах (" + x + ", " + y + ", " + z + ") не существует."));
                        }
                    } catch (NumberFormatException e) {
                        player.sendMessage(TextUtil.color("&cНекорректные координаты. Используйте числовые значения для x, y, z."));
                    }
                }
            } else {
                player.sendMessage(TextUtil.color("&cЭтот тип региона не поддерживает удаление точек спауна команды."));
            }
        } else {
            player.sendMessage(TextUtil.color("&cНедостаточно аргументов"));
            player.sendMessage(TextUtil.color("&cИспользование: /region removeteamspawn <тип> [x y z]"));
        }
    }

    private void handleCreate(Player player, String[] args, RegionPlayer regionPlayer) {
        if (args.length == 2) {
            if (regionPlayer.getPos1() != null && regionPlayer.getPos2() != null) {
                RegionType arg;
                try {
                    arg = RegionType.valueOf(args[1]);
                } catch (IllegalArgumentException e) {
                    player.sendMessage(TextUtil.color("&cНеверный тип региона. Допустимые значения: CT_SPAWN, T_SPAWN"));
                    return;
                }
                Region region = new Region(arg);
                region.addCuboid(new RegionCuboid(regionPlayer.getPos1(), regionPlayer.getPos2()));
                regionPlayer.setPos1(null);
                regionPlayer.setPos2(null);
                region.addRegion();
                player.sendMessage(TextUtil.color("&aУспех! Вы создали новый регион для &e" + arg.name()));
            } else {
                player.sendMessage(TextUtil.color("&cКакая-то точка не установлена"));
            }
        } else {
            player.sendMessage(TextUtil.color("&cНедостаточно аргументов"));
            player.sendMessage(TextUtil.color("&cИспользование: /region create <тип>"));
        }
    }

    private void handleCui(Player player, String[] args) {
        if (args.length == 2) {
            RegionType arg;
            try {
                arg = RegionType.valueOf(args[1]);
            } catch (IllegalArgumentException e) {
                player.sendMessage(TextUtil.color("&cНеверный тип региона. Допустимые значения: CT_SPAWN, T_SPAWN"));
                return;
            }
            Region region = Region.getRegions(arg);
            if (region != null) {
                region.getCuboids().forEach(RegionCUI::showCUI);
            } else {
                player.sendMessage(TextUtil.color("&cТакой регион отсутствует"));
            }
        } else {
            player.sendMessage(TextUtil.color("&cНедостаточно аргументов"));
            player.sendMessage(TextUtil.color("&cИспользование: /region cui <тип>"));
        }
    }

    private void handleClearCui(Player player, String[] args) {
        if (args.length == 2) {
            RegionType arg;
            try {
                arg = RegionType.valueOf(args[1]);
            } catch (IllegalArgumentException e) {
                player.sendMessage(TextUtil.color("&cНеверный тип региона. Допустимые значения: CT_SPAWN, T_SPAWN"));
                return;
            }
            Region region = Region.getRegions(arg);
            if (region != null) {
                region.getCuboids().forEach(RegionCUI::hideCUI);
            } else {
                player.sendMessage(TextUtil.color("&cТакой регион отсутствует"));
            }
        } else {
            player.sendMessage(TextUtil.color("&cНедостаточно аргументов"));
            player.sendMessage(TextUtil.color("&cИспользование: /region clearCui <тип>"));
        }
    }

    private void handleList(Player player, String[] args) {
        if (args.length == 2) {
            RegionType arg;
            try {
                arg = RegionType.valueOf(args[1]);
            } catch (IllegalArgumentException e) {
                player.sendMessage(TextUtil.color("&cНеверный тип региона. Допустимые значения: CT_SPAWN, T_SPAWN"));
                return;
            }
            RegionMenu regionMenu = new RegionMenu(arg);
            regionMenu.open(player);
        } else {
            player.sendMessage(TextUtil.color("&cНедостаточно аргументов"));
            player.sendMessage(TextUtil.color("&cИспользование: /region list <тип>"));
        }
    }

    private void handleAddCuboid(Player player, String[] args, RegionPlayer regionPlayer) {
        if (args.length == 2) {
            RegionType arg;
            try {
                arg = RegionType.valueOf(args[1]);
            } catch (IllegalArgumentException e) {
                player.sendMessage(TextUtil.color("&cНеверный тип региона. Допустимые значения: CT_SPAWN, T_SPAWN"));
                return;
            }
            Region region = Region.getRegions(arg);
            if (region != null) {
                region.addCuboid(new RegionCuboid(regionPlayer.getPos1(), regionPlayer.getPos2()));
                player.sendMessage(TextUtil.color("&aУспех! Вы добавили новый кубоид в &e" + arg.name()));
            } else {
                player.sendMessage(TextUtil.color("&cТакой регион отсутствует"));
            }
        } else {
            player.sendMessage(TextUtil.color("&cНедостаточно аргументов"));
            player.sendMessage(TextUtil.color("&cИспользование: /region addCuboid <тип>"));
        }
    }

    private void handleClearCuboids(Player player, String[] args) {
        if (args.length == 2) {
            RegionType arg;
            try {
                arg = RegionType.valueOf(args[1]);
            } catch (IllegalArgumentException e) {
                player.sendMessage(TextUtil.color("&cНеверный тип региона. Допустимые значения: CT_SPAWN, T_SPAWN"));
                return;
            }
            Region region = Region.getRegions(arg);
            if (region != null) {
                region.getCuboids().clear();
                player.sendMessage(TextUtil.color("&aУспех! Вы отчистили &e" + arg.name() + "&a от кубоидов"));
            } else {
                player.sendMessage(TextUtil.color("&cТакой регион отсутствует"));
            }
        } else {
            player.sendMessage(TextUtil.color("&cНедостаточно аргументов"));
            player.sendMessage(TextUtil.color("&cИспользование: /region clearCuboids <тип>"));
        }
    }

    private void handleSave(Player player, String[] args) {
        if (args.length == 2) {
            String name = args[1];
            RegionManager regionManager = new RegionManager(name);
            regionManager.saveRegionData(Region.getRegions());
            this.regionManager = regionManager;
            player.sendMessage(TextUtil.color("&aУспех! Вы сохранили регион в конфиг"));
        } else {
            player.sendMessage(TextUtil.color("&cНедостаточно аргументов"));
            player.sendMessage(TextUtil.color("&cИспользование: /region save <название>"));
        }
    }

    private void handleConvert(Player player, String[] args) {
        if (args.length == 2) {
            String name = args[1];
            WorldConverter worldConverter = new WorldConverter();
            worldConverter.convert(name);
        } else {
            player.sendMessage(TextUtil.color("&cНедостаточно аргументов"));
            player.sendMessage(TextUtil.color("&cИспользование: /region convert <название>"));
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return filterByPrefix(COMMANDS, args[0]);
        } else if (args.length == 2) {
            return switch (args[0].toLowerCase()) {
                case "create", "cui", "clearcui", "list", "addcuboid", "clearcuboids", "addteamspawn", "removeteamspawn" ->
                        filterByPrefix(Arrays.asList(RegionType.values()), args[1]);
                case "save", "convert" -> null; // No tab completion for names
                default -> null;
            };
        }
        return null;
    }

    private <T> List<String> filterByPrefix(List<T> items, String prefix) {
        List<String> result = new ArrayList<>();
        for (T item : items) {
            if (item.toString().toLowerCase().startsWith(prefix.toLowerCase())) {
                result.add(item.toString());
            }
        }
        return result;
    }
}
