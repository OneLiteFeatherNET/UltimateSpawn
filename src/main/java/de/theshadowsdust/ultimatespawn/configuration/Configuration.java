package de.theshadowsdust.ultimatespawn.configuration;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

public final class Configuration {

    private String language;
    private boolean joinAtSpawn;
    private boolean respawnAtSpawn;
    private boolean firstJoinSpawn;
    private double version;

    public Configuration(double version) {
        this(Locale.getDefault().toString(), true, true, true, 1.0D);
    }

    public Configuration(@NotNull String language,
                         boolean joinAtSpawn,
                         boolean respawnAtSpawn,
                         boolean firstJoinSpawn,
                         double version) {
        this.language = language;
        this.joinAtSpawn = joinAtSpawn;
        this.respawnAtSpawn = respawnAtSpawn;
        this.firstJoinSpawn = firstJoinSpawn;
        this.version = version;
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

    public void setVersion(double version) {
        this.version = version;
    }

    public double getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Configuration that)) return false;

        return joinAtSpawn == that.joinAtSpawn &&
                respawnAtSpawn == that.respawnAtSpawn &&
                firstJoinSpawn == that.firstJoinSpawn &&
                Double.compare(version, that.version) == 0 &&
                Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(language);
        result = 31 * result + Boolean.hashCode(joinAtSpawn);
        result = 31 * result + Boolean.hashCode(respawnAtSpawn);
        result = 31 * result + Boolean.hashCode(firstJoinSpawn);
        result = 31 * result + Double.hashCode(version);
        return result;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "language='" + language + '\'' +
                ", joinAtSpawn=" + joinAtSpawn +
                ", respawnAtSpawn=" + respawnAtSpawn +
                ", firstJoinSpawn=" + firstJoinSpawn +
                ", version=" + version +
                '}';
    }
}
