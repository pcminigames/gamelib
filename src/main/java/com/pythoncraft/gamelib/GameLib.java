package com.pythoncraft.gamelib;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;


public class GameLib extends JavaPlugin {
    
    static GameLib instance;
    public static GameLib getInstance() { return instance; }

    public static final Material DEFAULT_MATERIAL = Material.STICK;

    @Override
    public void onEnable() {
        instance = this;
        Logger.info("GameLib is starting up...");
    }

    @Override
    public void onDisable() {
        Logger.info("GameLib is shutting down...");
    }

    public static ItemStack getItemStack(Material material, int amount, String displayName) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta meta = itemStack.getItemMeta();

        if (displayName != null && !displayName.isEmpty()) {
            meta.setDisplayName(displayName);
        }

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack getItemStack(Material material, int amount) {
        return getItemStack(material, amount, null);
    }

    public static ItemStack getItemStack(Material material) {
        return getItemStack(material, 1, null);
    }

    public static ItemStack getItemStack() {
        return getItemStack(DEFAULT_MATERIAL, 1, null);
    }

    public static ItemStack getItemStack(String displayName) {
        return getItemStack(DEFAULT_MATERIAL, 1, displayName);
    }

    public static void forceLoadChunk(World world, int x, int z) {
        world.getChunkAt(x, z).addPluginChunkTicket(GameLib.getInstance());
    }

    public static void forceLoadChunk(World world, int x, int z, int radius) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                forceLoadChunk(world, x + dx, z + dz);
            }
        }
    }

    public static void forceLoadChunkStop(World world, int x, int z) {
        world.getChunkAt(x, z).removePluginChunkTicket(GameLib.getInstance());
    }

    public static void forceLoadChunkStop(World world, int x, int z, int radius) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                forceLoadChunkStop(world, x + dx, z + dz);
            }
        }
    }
}
