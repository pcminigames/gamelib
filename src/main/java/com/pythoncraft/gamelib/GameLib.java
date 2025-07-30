package com.pythoncraft.gamelib;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;


public class GameLib extends JavaPlugin {
    
    static GameLib instance;
    public static GameLib getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;
        Logger.info("GameLib is starting up...");
    }

    @Override
    public void onDisable() {
        Logger.info("GameLib is shutting down...");
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
