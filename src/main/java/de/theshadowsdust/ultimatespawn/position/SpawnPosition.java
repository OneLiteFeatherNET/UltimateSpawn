package de.theshadowsdust.ultimatespawn.position;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class SpawnPosition {

    private String name;
    private WrappedLocation wrappedLocation;
    private boolean defaultSpawn;

    public SpawnPosition(@NotNull String name, @NotNull WrappedLocation wrappedLocation, boolean defaultSpawn) {
        this.name = name;
        this.wrappedLocation = wrappedLocation;
        this.defaultSpawn = defaultSpawn;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public WrappedLocation getWrappedLocation() {
        return wrappedLocation;
    }

    public void setWrappedLocation(@NotNull WrappedLocation wrappedLocation) {
        this.wrappedLocation = wrappedLocation;
    }

    public boolean isDefaultSpawn() {
        return defaultSpawn;
    }

    public void setDefaultSpawn(boolean defaultSpawn) {
        this.defaultSpawn = defaultSpawn;
    }

    @Override
    public String toString() {
        return "SpawnPosition{" +
                "name='" + name + '\'' +
                ", wrappedLocation=" + wrappedLocation +
                ", defaultSpawn=" + defaultSpawn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpawnPosition that)) return false;

        if (defaultSpawn != that.defaultSpawn) return false;
        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(wrappedLocation, that.wrappedLocation);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (wrappedLocation != null ? wrappedLocation.hashCode() : 0);
        result = 31 * result + (defaultSpawn ? 1 : 0);
        return result;
    }
}
