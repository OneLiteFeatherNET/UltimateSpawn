package de.theshadowsdust.ultimatespawn.command;

import de.theshadowsdust.ultimatespawn.UltimateSpawnPlugin;
import de.theshadowsdust.ultimatespawn.service.LanguageService;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SetSpawnCommand {

    private final UltimateSpawnPlugin plugin;
    private final LanguageService languageService;

    public SetSpawnCommand(@NotNull UltimateSpawnPlugin plugin) {
        this.plugin = plugin;
        this.languageService = plugin.getLanguageService();
    }

    @Command("setspawn <name>")
    @Permission("ultimatespawn.command.setspawn")
    @CommandDescription("Set a spawn position")
    public void executeSetSpawnCommand(Player player,
                                       @Argument(value = "name") String name,
                                       @Nullable @Default(value = "false") @Flag(value = "force") Boolean override) {
        this.plugin.getSpawnPositionService().getSpawnPosition(name).thenAcceptAsync(spawnPosition -> {

            if (spawnPosition != null && override == Boolean.FALSE) {
                player.sendMessage(this.languageService.getMessage("spawn-already-exists",
                        this.languageService.getPluginPrefix(), name));
                return;
            }

            this.plugin.getSpawnPositionService().addSpawnPosition(name, player.getLocation());

            player.sendMessage(this.languageService.getMessage("command.setspawn.success",
                    this.languageService.getPluginPrefix(), name));
        });
    }
}
