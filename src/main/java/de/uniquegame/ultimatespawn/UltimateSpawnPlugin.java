package de.uniquegame.ultimatespawn;

import de.uniquegame.ultimatespawn.listener.SpawnLocationListener;
import de.uniquegame.ultimatespawn.service.CommandService;
import de.uniquegame.ultimatespawn.service.ConfigurationService;
import de.uniquegame.ultimatespawn.service.LanguageService;
import de.uniquegame.ultimatespawn.service.SpawnPositionService;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class UltimateSpawnPlugin extends JavaPlugin {

    private SpawnPositionService spawnPositionService;
    private LanguageService languageService;
    private CommandService commandService;
    private ConfigurationService configurationService;

    @Override
    public void onEnable() {

        PluginManager pluginManager = getServer().getPluginManager();
        this.configurationService = new ConfigurationService(this);
        this.languageService = new LanguageService(this);
        this.commandService = new CommandService(this);
        this.commandService.buildCommandSystem();
        this.commandService.buildHelpSystem();

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
