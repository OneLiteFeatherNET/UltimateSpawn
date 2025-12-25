package de.theshadowsdust.ultimatespawn.command;

import de.theshadowsdust.ultimatespawn.UltimateSpawnPlugin;
import de.theshadowsdust.ultimatespawn.position.SpawnPosition;
import de.theshadowsdust.ultimatespawn.position.WrappedLocation;
import de.theshadowsdust.ultimatespawn.service.LanguageService;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.incendo.cloud.annotation.specifier.Greedy;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.annotations.suggestion.Suggestions;
import org.incendo.cloud.context.CommandContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

public final class SpawnCommand {

    private final UltimateSpawnPlugin plugin;
    private final LanguageService languageService;

    public SpawnCommand(@NotNull UltimateSpawnPlugin plugin) {
        this.plugin = plugin;
        this.languageService = plugin.getLanguageService();
    }

    @Command("spawn [name]")
    @Permission("ultimatespawn.command.spawn")
    @CommandDescription("Teleport to the spawn")
    public void executeSpawnCommand(Player player, final @Argument(value = "name", suggestions = "spawn_names") @Greedy String name) {

        String positionName = name == null ? "spawn" : name;
        if (positionName.isEmpty()) positionName = "spawn";

        try {
            SpawnPosition spawnPosition = this.plugin.getSpawnPositionService().getSpawnPosition(positionName).get();
            if (spawnPosition == null) {
                player.sendMessage(this.languageService.getMessage("spawn-not-exists",
                        this.languageService.getPluginPrefix(), name));
                return;
            }

            Location location = WrappedLocation.toLocation(spawnPosition.getWrappedLocation());
            if (location == null) {
                player.sendMessage(this.languageService.getMessage("invalid-world", this.languageService.getPluginPrefix()));
                return;
            }

            player.teleportAsync(location).thenAccept(success -> {
                if(Boolean.TRUE.equals(success)) {
                    player.sendMessage(this.languageService.getMessage("spawn-teleport-success",
                            this.languageService.getPluginPrefix()));
                } else {
                    player.sendMessage(this.languageService.getMessage("spawn-teleport-failure",
                            this.languageService.getPluginPrefix()));
                }
            });

        } catch (ExecutionException | InterruptedException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Something went wrong on teleportation", e);
            Thread.currentThread().interrupt();
        }
    }

    @NotNull
    @Suggestions("spawn_names")
    public List<String> getSpawnNames(@NotNull CommandContext<CommandSender> context, @NotNull String input) {
        List<String> spawnNames = new ArrayList<>();
        for (SpawnPosition spawnPosition : this.plugin.getSpawnPositionService().getSpawnPositionList()) {
            if (StringUtil.startsWithIgnoreCase(spawnPosition.getName(), input.toLowerCase())) {
                spawnNames.add(spawnPosition.getName());
            }
        }

        return spawnNames;
    }
}
