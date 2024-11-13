package de.theshadowsdust.ultimatespawn.command;

import de.theshadowsdust.ultimatespawn.UltimateSpawnPlugin;
import de.theshadowsdust.ultimatespawn.position.SpawnPosition;
import de.theshadowsdust.ultimatespawn.service.LanguageService;
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

public final class RemoveSpawnCommand {

    private final UltimateSpawnPlugin plugin;
    private final LanguageService languageService;

    public RemoveSpawnCommand(@NotNull UltimateSpawnPlugin plugin) {
        this.plugin = plugin;
        this.languageService = plugin.getLanguageService();
    }

    @Command("removespawn <name>")
    @Permission("ultimatespawn.command.removespawn")
    @CommandDescription("Remove a spawn position")
    public void execute(Player player,
                        @NotNull
                        @Argument(value = "name", suggestions = "spawn_names") @Greedy String name) {
        this.plugin.getSpawnPositionService().getSpawnPosition(name).thenAcceptAsync(spawnPosition -> {

            if (spawnPosition == null) {
                player.sendMessage(this.languageService.getMessage("spawn-not-exists",
                        this.languageService.getPluginPrefix(), name));
                return;
            }

            this.plugin.getSpawnPositionService().removeSpawnPosition(name);
            player.sendMessage(this.languageService.getMessage("command.removespawn.success",
                    this.languageService.getPluginPrefix(), name));
        });
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
