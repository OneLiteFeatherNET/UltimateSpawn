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
import de.uniquegame.ultimatespawn.service.LanguageService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
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

    @CommandMethod("removespawn <name>")
    @CommandPermission("ultimatespawn.command.setspawn")
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
