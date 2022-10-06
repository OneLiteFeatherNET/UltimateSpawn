package de.uniquegame.ultimatespawn.command;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Greedy;
import cloud.commandframework.annotations.suggestions.Suggestions;
import cloud.commandframework.context.CommandContext;
import de.uniquegame.ultimatespawn.UltimateSpawnPlugin;
import de.uniquegame.ultimatespawn.position.SpawnPosition;
import de.uniquegame.ultimatespawn.position.WrappedLocation;
import de.uniquegame.ultimatespawn.service.LanguageService;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
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

    @CommandMethod("spawn [name]")
    @CommandPermission("ultimatespawn.command.spawn")
    @CommandDescription("Teleport to the spawn")
    public void executeSpawnCommand(Player player, final @Argument(value = "name", suggestions = "spawn_names") @Greedy String name) {

        String positionName = name == null ? "spawn" : name;

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

            player.teleport(location);
            player.sendMessage(this.languageService.getMessage("spawn-teleport-success", this.languageService.getPluginPrefix()));
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
