package de.gunnablescum.raldisystem;

import de.gunnablescum.raldisystem.commands.ConfigCommand;
import de.gunnablescum.raldisystem.commands.ReloadConfigCommand;
import de.gunnablescum.raldisystem.commands.remotecontrol.ConsoleCommand;
import de.gunnablescum.raldisystem.commands.remotecontrol.FileDownloadCommand;
import de.gunnablescum.raldisystem.listeners.DeathListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    public static Main instance;
    public boolean remoteControlAllowed = true;
    public double deathBreakFactor = 0.5;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("reloadconfig").setExecutor(new ReloadConfigCommand());
        getCommand("filedownload").setExecutor(new FileDownloadCommand());
        getCommand("console").setExecutor(new ConsoleCommand());
        getCommand("config").setExecutor(new ConfigCommand());
        Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
        loadConfig();
    }

    public static Main getInstance() {
        return instance;
    }

    public void loadConfig() {
        File file = new File("plugins/RaldiSystem/", "config.yml");
        if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // Welp, rip.
                throw new RuntimeException(e);
            }
        }
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if(cfg.get("RemoteControlAllowed") == null) cfg.set("RemoteControlAllowed", true);
        if(cfg.get("DeathBreakFactor") == null) cfg.set("DeathBreakFactor", 0.5f);
        try {
            cfg.save(file);
        } catch (IOException e) {
            // Welp, rip again.
            throw new RuntimeException(e);
        }
        remoteControlAllowed = cfg.getBoolean("RemoteControlAllowed");
        deathBreakFactor = cfg.getDouble("DeathBreakFactor");
    }

    public void setConfig(String key, Object data) {
        File file = new File("plugins/RaldiSystem/", "config.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set(key, data);
        try {
            cfg.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}