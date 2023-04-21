package com.github.fntlv.onlinereward.util;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class ConfigUtil {

    private JavaPlugin plugin;
    private String fileName;
    private File dataFolder;
    private File file;

    public ConfigUtil(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.dataFolder = plugin.getDataFolder();
        this.file = new File(dataFolder, fileName);
    }


    public void load() {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }
    }


    public YamlConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(file);
    }


    public void save() {
        try {
            getConfig().save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void set(String path, Object value) {
        getConfig().set(path, value);
        save();
    }

    public Object get(String path) {
        return getConfig().get(path);
    }

}