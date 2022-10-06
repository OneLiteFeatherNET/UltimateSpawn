package de.uniquegame.ultimatespawn.service;

import de.uniquegame.ultimatespawn.UltimateSpawnPlugin;
import de.uniquegame.ultimatespawn.configuration.Configuration;
import de.uniquegame.ultimatespawn.util.Constants;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

public class ConfigurationService {

    private final UltimateSpawnPlugin plugin;
    private final File configFile;
    private Configuration configuration;
    private final Configuration defaultConfig;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

    public ConfigurationService(@NotNull UltimateSpawnPlugin plugin) {

        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "config.json");
        this.defaultConfig = new Configuration(getPluginVersion());

        if (Files.notExists(this.configFile.toPath())) {
            try {
                Files.createFile(this.configFile.toPath());
                this.configuration = this.defaultConfig;
                saveConfig();
            } catch (IOException e) {
                this.plugin.getLogger().log(Level.SEVERE, "An error occurred while loading the config file", e);
                this.configuration = this.defaultConfig;
                return;
            }
        }

        loadConfig();
    }

    public Configuration getConfig() {
        return configuration;
    }

    public void loadConfig() {

        boolean needUpdate = false;

        try (BufferedReader bufferedReader = Files.newBufferedReader(this.configFile.toPath())) {
            this.configuration = Constants.GSON.fromJson(bufferedReader, Configuration.class);
            needUpdate = !this.configuration.getPluginVersion().equals(defaultConfig.getPluginVersion());
        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "An error occurred while loading the config file", e);
            this.configuration = defaultConfig;
        }

        if (needUpdate) {

            File file = new File(this.plugin.getDataFolder(), String.format("bak_config_%s.json", dateFormat.format(new Date())));
            if (Files.notExists(file.toPath())) {
                try {
                    Files.copy(this.configFile.toPath(), file.toPath());
                } catch (IOException e) {
                    this.plugin.getLogger().log(Level.SEVERE, "An error occurred during the copy operation", e);
                }
            }

            this.configuration = defaultConfig;
            saveConfig();
        }
    }

    private void saveConfig() {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(this.configFile.toPath())) {
            bufferedWriter.write(Constants.GSON.toJson(this.configuration));
        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Cannot save config file", e);
        }
    }

    @NotNull
    private String getPluginVersion() {
        String version = this.plugin.getDescription().getVersion();
        String versionSplitter = "-";
        if (version.contains(versionSplitter)) {
            version = version.split(versionSplitter)[0];
        }
        return version;
    }
}
