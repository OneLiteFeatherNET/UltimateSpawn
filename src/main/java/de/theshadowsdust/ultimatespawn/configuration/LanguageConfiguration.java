package de.theshadowsdust.ultimatespawn.configuration;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public final class LanguageConfiguration {

    private Locale locale;
    private final File file;
    private YamlConfiguration configuration;

    public LanguageConfiguration(@NotNull Locale locale, @NotNull File file) {
        this.locale = locale;
        this.file = file;
        loadConfig();
    }

    @NotNull
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(@Nullable Locale locale) {
        this.locale = locale != null ? locale : Locale.getDefault();
    }

    @NotNull
    public File getFile() {
        return file;
    }

    @NotNull
    public YamlConfiguration getConfig() {
        return configuration;
    }

    public void setConfiguration(@NotNull YamlConfiguration configuration) {
        this.configuration = configuration;
    }

    public void loadConfig() {
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    @Override
    public String toString() {
        return "LanguageConfiguration{" +
                "locale=" + locale +
                ", configuration=" + configuration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LanguageConfiguration that)) return false;

        if (!Objects.equals(locale, that.locale)) return false;
        return Objects.equals(configuration, that.configuration);
    }

    @Override
    public int hashCode() {
        int result = locale != null ? locale.hashCode() : 0;
        result = 31 * result + (configuration != null ? configuration.hashCode() : 0);
        return result;
    }

    public void save() throws IOException {
        this.configuration.save(this.file);
    }
}
