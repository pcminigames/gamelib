package com.pythoncraft.gamelib;

import org.bukkit.plugin.java.JavaPlugin;


public class GameLib extends JavaPlugin {
    
    static GameLib instance;
    public static GameLib getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("GameLib is starting up...");
    }

    @Override
    public void onDisable() {
        getLogger().info("GameLib is shutting down...");
    }
}
