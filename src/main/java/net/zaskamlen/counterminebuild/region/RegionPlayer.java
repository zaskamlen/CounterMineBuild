package net.zaskamlen.counterminebuild.region;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegionPlayer {

    private final String name;
    private final UUID uuid;
    private final Player player;
    private Location pos1;
    private Location pos2;
    private static final Map<Player, RegionPlayer> players = new HashMap<>();

    public RegionPlayer(Player player) {
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        this.player = player;
    }

    public static RegionPlayer getPlayer(Player player) {
        return players.get(player);
    }

    public void add() {
        players.put(this.player,this);
    }

    public static Map<Player, RegionPlayer> getPlayers() {
        return players;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }
}
