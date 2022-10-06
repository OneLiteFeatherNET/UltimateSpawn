package de.uniquegame.ultimatespawn.service;

import de.uniquegame.ultimatespawn.UltimateSpawnPlugin;
import de.uniquegame.ultimatespawn.configuration.LanguageConfiguration;
import de.uniquegame.ultimatespawn.util.MessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.apache.commons.lang3.LocaleUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

public final class LanguageService {

    private static final String YAML_FILE_EXTENSION = ".yml";
    private final UltimateSpawnPlugin plugin;
    private ResourceBundle defaultMessages;
    private final File translationDir;
    private LanguageConfiguration languageConfiguration;

    public LanguageService(UltimateSpawnPlugin plugin) {
        this.plugin = plugin;
        this.translationDir = new File(plugin.getDataFolder(), "translations");
        loadMessages();
    }

    @NotNull
    public LanguageConfiguration getLanguageConfig() {
        return this.languageConfiguration;
    }

    @NotNull
    public String getRawMessage(@NotNull String key) {
        return this.languageConfiguration.getConfig().getString(key, this.defaultMessages.getString(key));
    }

    @NotNull
    public String getPluginPrefix() {
        return getRawMessage("prefix");
    }

    @NotNull
    public Component getMessage(@NotNull String key, Object... placeholders) {
        return MessageUtil.translateLegacyColorCodes(MessageFormat.format(getRawMessage(String.format("%s", key)), placeholders));
    }

    public void loadMessages() {
        try {
            if (Files.notExists(this.translationDir.toPath())) Files.createDirectory(this.translationDir.toPath());
            this.defaultMessages = ResourceBundle.getBundle("ultimatespawn_%s".formatted(
                    this.plugin.getConfigurationService().getConfig().getLanguage()), new UTF8ResourceBundleControl());
            this.languageConfiguration = loadLanguageConfig();
        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Can't load messages", e);
        }
    }

    private LanguageConfiguration loadLanguageConfig() throws IOException {

        Locale locale = LocaleUtils.toLocale(this.plugin.getConfigurationService().getConfig().getLanguage());
        File file = new File(this.translationDir, "%s%s".formatted(locale, YAML_FILE_EXTENSION));
        if (Files.notExists(file.toPath())) Files.createFile(file.toPath());

        LanguageConfiguration configuration = new LanguageConfiguration(locale, file);
        updateConfig(configuration);

        return configuration;
    }

    private void updateConfig(LanguageConfiguration configuration) {
        boolean saveConfig = false;
        Enumeration<String> defaultKeys = this.defaultMessages.getKeys();
        while (defaultKeys.hasMoreElements()) {
            String key = defaultKeys.nextElement();
            String path = String.format("%s", key);
            if (!configuration.getConfig().isSet(path)) {
                configuration.getConfig().set(path, this.defaultMessages.getString(key));
                if (!saveConfig) {
                    saveConfig = true;
                }
            }
        }

        if (saveConfig) {
            try {
                configuration.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
