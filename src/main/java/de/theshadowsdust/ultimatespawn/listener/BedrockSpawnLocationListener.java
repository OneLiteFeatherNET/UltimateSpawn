package de.theshadowsdust.ultimatespawn.listener;

import de.theshadowsdust.ultimatespawn.UltimateSpawnPlugin;
import de.theshadowsdust.ultimatespawn.position.SpawnPosition;
import de.theshadowsdust.ultimatespawn.position.WrappedLocation;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.geysermc.geyser.api.GeyserApi;
import org.jetbrains.annotations.Nullable;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

public final class BedrockSpawnLocationListener implements Listener {

    private final UltimateSpawnPlugin plugin;

    public BedrockSpawnLocationListener(UltimateSpawnPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerSpawnLocationEvent event) {
        Player player = event.getPlayer();
        if (GeyserApi.api().isBedrockPlayer(event.getPlayer().getUniqueId())) {
            try {
                if(!player.hasPlayedBefore() && plugin.getConfigurationService().getConfig().isFirstJoinSpawn()) {
                    if(getLocation() == null) return;
                    event.setSpawnLocation(getLocation());
                }
                else {
                    if (this.plugin.getConfigurationService().getConfig().isJoinAtSpawn()) {
                        getLocation();
                    }
                }
            } catch (ExecutionException | InterruptedException e) {
                this.plugin.getLogger().log(Level.SEVERE, "Cannot set spawn location for Bedrock player", e);
                Thread.currentThread().interrupt();
            }
            player.teleport(event.getSpawnLocation());
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handlePlayerRespawnLocation(PlayerRespawnEvent event) {
        if (GeyserApi.api().isBedrockPlayer(event.getPlayer().getUniqueId())) {
            if (!this.plugin.getConfigurationService().getConfig().isRespawnAtSpawn()) return;
            try {
                Location location = getLocation();
                if (location == null) return;
                event.setRespawnLocation(location);
            } catch (ExecutionException | InterruptedException e) {
                this.plugin.getLogger().log(Level.SEVERE, "Cannot set respawn location for Bedrock player", e);
                event.setRespawnLocation(event.getPlayer().getWorld().getSpawnLocation());
                Thread.currentThread().interrupt();
            }
        }
    }
        private @Nullable Location getLocation () throws InterruptedException, ExecutionException {
            SpawnPosition spawnPosition = this.plugin.getSpawnPositionService().getDefaultSpawn().get();
            return WrappedLocation.toLocation(spawnPosition.getWrappedLocation());
        }
}
