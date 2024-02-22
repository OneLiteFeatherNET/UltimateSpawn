package de.theshadowsdust.ultimatespawn.configuration;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

public final class Configuration {

    private String language;
    private boolean joinAtSpawn;
    private boolean respawnAtSpawn;
    private boolean firstJoinSpawn;
    private String pluginVersion;

    public Configuration(String pluginVersion) {
        this(Locale.getDefault().toString(), true, true, true, pluginVersion);
    }

    public Configuration(@NotNull String language,
                         boolean joinAtSpawn,
                         boolean respawnAtSpawn,
                         boolean firstJoinSpawn,
                         @NotNull String pluginVersion) {
        this.language = language;
        this.joinAtSpawn = joinAtSpawn;
        this.respawnAtSpawn = respawnAtSpawn;
        this.firstJoinSpawn = firstJoinSpawn;
        this.pluginVersion = pluginVersion;
    }

    @NotNull
    public String getLanguage() {
        return language;
    }

    public void setLanguage(@NotNull String language) {
        this.language = language;
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

    public boolean isFirstJoinSpawn() { return firstJoinSpawn; }

    public void setFirstJoinSpawn(boolean firstJoinSpawn) { this.firstJoinSpawn = firstJoinSpawn; }

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
                ", overrideSpawnLocation=" + joinAtSpawn +
                ", overrideRespawnLocation=" + respawnAtSpawn +
                ", pluginVersion='" + pluginVersion + '\'' +
                ", firstJoinSpawn='" + firstJoinSpawn + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return joinAtSpawn == that.joinAtSpawn && respawnAtSpawn == that.respawnAtSpawn && firstJoinSpawn == that.firstJoinSpawn && Objects.equals(language, that.language) && Objects.equals(pluginVersion, that.pluginVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, joinAtSpawn, respawnAtSpawn, firstJoinSpawn, pluginVersion);
    }
}
