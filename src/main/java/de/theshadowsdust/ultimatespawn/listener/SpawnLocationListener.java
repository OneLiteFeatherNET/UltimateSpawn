package de.theshadowsdust.ultimatespawn.listener;

import de.theshadowsdust.ultimatespawn.position.SpawnPosition;
import de.theshadowsdust.ultimatespawn.position.WrappedLocation;
import de.theshadowsdust.ultimatespawn.UltimateSpawnPlugin;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.jetbrains.annotations.NotNull;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

public final class SpawnLocationListener implements Listener {

    private final UltimateSpawnPlugin plugin;

    public SpawnLocationListener(@NotNull UltimateSpawnPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handlePlayerSpawnLocation(PlayerSpawnLocationEvent event) {
        try {
            if (this.plugin.getConfigurationService().getConfig().isJoinAtSpawn()) {
                SpawnPosition spawnPosition = this.plugin.getSpawnPositionService().getDefaultSpawn().get();
                Location location = WrappedLocation.toLocation(spawnPosition.getWrappedLocation());
                if (location == null) return;
                event.setSpawnLocation(location);
            }
        } catch (ExecutionException | InterruptedException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Cannot set spawn location", e);
            Thread.currentThread().interrupt();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handlePlayerRespawnLocation(PlayerRespawnEvent event) {
        if (!this.plugin.getConfigurationService().getConfig().isRespawnAtSpawn()) return;
        try {
            SpawnPosition spawnPosition = this.plugin.getSpawnPositionService().getDefaultSpawn().get();
            Location location = WrappedLocation.toLocation(spawnPosition.getWrappedLocation());
            if (location == null) return;
            event.setRespawnLocation(location);
        } catch (ExecutionException | InterruptedException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Cannot set respawn location", e);
            event.setRespawnLocation(event.getPlayer().getWorld().getSpawnLocation());
            Thread.currentThread().interrupt();
        }
    }
}
