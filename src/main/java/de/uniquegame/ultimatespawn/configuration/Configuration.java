package de.uniquegame.ultimatespawn.configuration;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

public final class Configuration {

    private String language;
    private boolean overrideFirstSpawn;
    private boolean joinAtSpawn;
    private boolean respawnAtSpawn;

    private String pluginVersion;

    public Configuration(String pluginVersion) {
        this(Locale.getDefault().toString(), true, true, true, pluginVersion);
    }

    public Configuration(@NotNull String language,
                         boolean overrideFirstSpawn,
                         boolean joinAtSpawn,
                         boolean respawnAtSpawn,
                         @NotNull String pluginVersion) {
        this.language = language;
        this.overrideFirstSpawn = overrideFirstSpawn;
        this.joinAtSpawn = joinAtSpawn;
        this.respawnAtSpawn = respawnAtSpawn;
        this.pluginVersion = pluginVersion;
    }

    @NotNull
    public String getLanguage() {
        return language;
    }

    public void setLanguage(@NotNull String language) {
        this.language = language;
    }

    public boolean isOverrideFirstSpawn() {
        return overrideFirstSpawn;
    }

    public void setOverrideFirstSpawn(boolean overrideFirstSpawn) {
        this.overrideFirstSpawn = overrideFirstSpawn;
    }

    public boolean isJoinAtSpawn() {
        return joinAtSpawn;
    }

    public void setJoinAtSpawn(boolean joinAtSpawn) {
        this.joinAtSpawn = joinAtSpawn;
    }

    public boolean isRespawnAtSpawn() {
        return respawnAtSpawn;
    }

    public void setRespawnAtSpawn(boolean respawnAtSpawn) {
        this.respawnAtSpawn = respawnAtSpawn;
    }

    @NotNull
    public String getPluginVersion() {
        return pluginVersion;
    }

    public void setPluginVersion(@NotNull String pluginVersion) {
        this.pluginVersion = pluginVersion;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "language='" + language + '\'' +
                ", overrideFirstSpawn=" + overrideFirstSpawn +
                ", overrideSpawnLocation=" + joinAtSpawn +
                ", overrideRespawnLocation=" + respawnAtSpawn +
                ", pluginVersion='" + pluginVersion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Configuration that)) return false;

        if (overrideFirstSpawn != that.overrideFirstSpawn) return false;
        if (joinAtSpawn != that.joinAtSpawn) return false;
        if (respawnAtSpawn != that.respawnAtSpawn) return false;
        if (!Objects.equals(language, that.language)) return false;
        return Objects.equals(pluginVersion, that.pluginVersion);
    }

    @Override
    public int hashCode() {
        int result = language != null ? language.hashCode() : 0;
        result = 31 * result + (overrideFirstSpawn ? 1 : 0);
        result = 31 * result + (joinAtSpawn ? 1 : 0);
        result = 31 * result + (respawnAtSpawn ? 1 : 0);
        result = 31 * result + (pluginVersion != null ? pluginVersion.hashCode() : 0);
        return result;
    }
}
