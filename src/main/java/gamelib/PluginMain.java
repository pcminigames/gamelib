package gamelib;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import gamelib.gui.GUIClickEvent;
import gamelib.gui.GUIIdentifier;
import gamelib.gui.GUIManager;


public class PluginMain extends JavaPlugin implements Listener {
    
    static PluginMain instance;
    public static PluginMain getInstance() { return instance; }

    private File configFile;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(GUIManager.getInstance(), this);

        this.configFile = new File(getDataFolder(), "config.yml");
        this.config = YamlConfiguration.loadConfiguration(this.configFile);
    }

    @Override
    public void onDisable() {}
}
