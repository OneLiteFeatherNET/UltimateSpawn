package de.uniquegame.ultimatespawn.position;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class WrappedLocation {

    private String world;
    private double posX;
    private double posY;
    private double posZ;
    private float yaw;
    private float pitch;

    public WrappedLocation(String world, double posX, double posY, double posZ, float yaw, float pitch) {
        this.world = world;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @NotNull
    public String getWorld() {
        return world;
    }

    public void setWorld(@NotNull String world) {
        this.world = world;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    @Override
    public String toString() {
        return "WrappedLocation{" +
                "world='" + world + '\'' +
                ", posX=" + posX +
                ", posY=" + posY +
                ", posZ=" + posZ +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WrappedLocation that)) return false;

        if (Double.compare(that.posX, posX) != 0) return false;
        if (Double.compare(that.posY, posY) != 0) return false;
        if (Double.compare(that.posZ, posZ) != 0) return false;
        if (Float.compare(that.yaw, yaw) != 0) return false;
        if (Float.compare(that.pitch, pitch) != 0) return false;
        return Objects.equals(world, that.world);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = world != null ? world.hashCode() : 0;
        temp = Double.doubleToLongBits(posX);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(posY);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(posZ);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (yaw != +0.0f ? Float.floatToIntBits(yaw) : 0);
        result = 31 * result + (pitch != +0.0f ? Float.floatToIntBits(pitch) : 0);
        return result;
    }

    @NotNull
    public static WrappedLocation of(@NotNull Location location) {
        return new WrappedLocation(
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch());
    }

    @Nullable
    public static Location toLocation(@NotNull WrappedLocation wrappedLocation) {
        World bukkitWorld = Bukkit.getWorld(wrappedLocation.getWorld());
        if (bukkitWorld == null) return null;
        return new Location(bukkitWorld,
                wrappedLocation.getPosX(),
                wrappedLocation.getPosY(),
                wrappedLocation.getPosZ(),
                wrappedLocation.getYaw(),
                wrappedLocation.getPitch());
    }
}
