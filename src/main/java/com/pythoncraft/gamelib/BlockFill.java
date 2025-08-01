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
}
