package de.theshadowsdust.ultimatespawn;

import de.theshadowsdust.ultimatespawn.listener.BedrockSpawnLocationListener;
import de.theshadowsdust.ultimatespawn.listener.SpawnLocationListener;
import de.theshadowsdust.ultimatespawn.service.CommandService;
import de.theshadowsdust.ultimatespawn.service.ConfigurationService;
import de.theshadowsdust.ultimatespawn.service.LanguageService;
import de.theshadowsdust.ultimatespawn.service.SpawnPositionService;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;

public final class UltimateSpawnPlugin extends JavaPlugin {

    private SpawnPositionService spawnPositionService;
    private LanguageService languageService;
    private CommandService commandService;
    private ConfigurationService configurationService;

    @Override
    public void onEnable() {

        PluginManager pluginManager = getServer().getPluginManager();
        var dataFolder = getDataFolder();
        if(Files.notExists(dataFolder.toPath())) {
            try {
                Files.createDirectory(dataFolder.toPath());
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "Cannot create plugin folder!");
                pluginManager.disablePlugin(this);
                return;
            }
        }
        if(pluginManager.isPluginEnabled("Geyser-Spigot")) {
            pluginManager.registerEvents(new BedrockSpawnLocationListener(this), this);
        }

        this.configurationService = new ConfigurationService(this);
        this.languageService = new LanguageService(this);
        this.commandService = new CommandService(this);
        this.spawnPositionService = new SpawnPositionService(this);

        pluginManager.registerEvents(new SpawnLocationListener(this), this);
    }

    @NotNull
    public ConfigurationService getConfigurationService() {
        return configurationService;
    }

    @NotNull
    public LanguageService getLanguageService() {
        return languageService;
    }

    @NotNull
    public CommandService getCommandService() {
        return commandService;
    }

    @NotNull
    public SpawnPositionService getSpawnPositionService() {
        return spawnPositionService;
    }
}
