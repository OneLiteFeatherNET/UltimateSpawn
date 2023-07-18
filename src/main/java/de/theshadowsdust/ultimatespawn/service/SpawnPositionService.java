package de.theshadowsdust.ultimatespawn.service;

import de.theshadowsdust.ultimatespawn.position.SpawnPosition;
import de.theshadowsdust.ultimatespawn.position.WrappedLocation;
import de.theshadowsdust.ultimatespawn.UltimateSpawnPlugin;
import de.theshadowsdust.ultimatespawn.util.Constants;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public final class SpawnPositionService {

    private final UltimateSpawnPlugin plugin;
    private List<SpawnPosition> spawnPositionList;

    private final File configFile;
    private SpawnPosition defaultSpawn;

    public SpawnPositionService(@NotNull UltimateSpawnPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "spawns.json");
        loadConfig();
    }

    @NotNull
    public CompletableFuture<SpawnPosition> getDefaultSpawn() {
        return CompletableFuture.supplyAsync(() -> {
            SpawnPosition defaultSpawnPosition = null;
            for (int i = 0; i < this.spawnPositionList.size() && defaultSpawnPosition == null; i++) {
                SpawnPosition position = this.spawnPositionList.get(i);
                if (position.isDefaultSpawn()) {
                    defaultSpawnPosition = position;
                }
            }

            return defaultSpawnPosition != null ? defaultSpawnPosition : defaultSpawn;
        });
    }

    public CompletableFuture<SpawnPosition> getSpawnPosition(@NotNull String name) {
        return CompletableFuture.supplyAsync(() -> {
            SpawnPosition spawnPosition = null;
            for (int i = 0; i < this.spawnPositionList.size() && spawnPosition == null; i++) {
                SpawnPosition position = this.spawnPositionList.get(i);
                if (position.getName().equalsIgnoreCase(name)) {
                    spawnPosition = position;
                }
            }

            return spawnPosition;
        });
    }

    public void removeSpawnPosition(@NotNull String name) {
        getSpawnPosition(name).thenAccept(spawnPosition -> {
            if (spawnPosition == null) return;
            this.spawnPositionList.remove(spawnPosition);
        }).thenRunAsync(this::saveConfig);
    }

    public void addSpawnPosition(@NotNull String name, @NotNull Location location) {
        getSpawnPosition(name).thenAcceptAsync(spawnPosition -> {

            WrappedLocation wrappedLocation = WrappedLocation.of(location);
            if (spawnPosition != null) {
                spawnPosition.setWrappedLocation(wrappedLocation);
            } else {
                this.spawnPositionList.add(new SpawnPosition(name, wrappedLocation, this.spawnPositionList.isEmpty()));
            }

        }).thenRunAsync(this::saveConfig);
    }

    private void loadConfig() {

        this.spawnPositionList = new ArrayList<>();
        this.defaultSpawn = new SpawnPosition(
                "default_spawn",
                WrappedLocation.of(Bukkit.getWorlds().get(0).getSpawnLocation()),
                true);

        if (!Files.exists(this.configFile.toPath())) {
            try {
                Files.createFile(this.configFile.toPath());
            } catch (IOException e) {
                this.plugin.getLogger().log(Level.SEVERE, "Cannot create file", e);
                return;
            }
        }

        try (BufferedReader bufferedReader = Files.newBufferedReader(this.configFile.toPath())) {
            var data = Constants.GSON.fromJson(bufferedReader, SpawnPosition[].class);
            if (data != null) {
                this.spawnPositionList.addAll(List.of(data));
            }
        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Cannot read file", e);
        }
    }

    public void saveConfig() {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(this.configFile.toPath())) {
            bufferedWriter.write(Constants.GSON.toJson(this.spawnPositionList));
        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Cannot save file", e);
        }
    }

    public List<SpawnPosition> getSpawnPositionList() {
        return spawnPositionList;
    }
}
