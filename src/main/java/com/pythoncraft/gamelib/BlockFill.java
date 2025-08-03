package com.pythoncraft.gamelib;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class BlockFill {
    public Location pos1;
    public Location pos2;
    public Material material;

    public BlockFill(Location pos1, Location pos2, Material material) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.material = material;
    }

    public void fill(World world) {
        int minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
        int maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        int minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
        int maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
        int minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        int maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    world.getBlockAt(x, y, z).setType(material);
                }
            }
        }
    }

    public static BlockFill fromString(String pos1, String pos2, String separator, World world, Material material) {
        String[] pos1Parts = pos1.split(" ");
        String[] pos2Parts = pos2.split(" ");

        Location loc1 = new Location(world, Double.parseDouble(pos1Parts[0]), Double.parseDouble(pos1Parts[1]), Double.parseDouble(pos1Parts[2]));
        Location loc2 = new Location(world, Double.parseDouble(pos2Parts[0]), Double.parseDouble(pos2Parts[1]), Double.parseDouble(pos2Parts[2]));

        return new BlockFill(loc1, loc2, material);
    }

    public static BlockFill fromString(String pos1, String pos2, World world, Material material) {
        return fromString(pos1, pos2, " ", world, material);
    }
}
