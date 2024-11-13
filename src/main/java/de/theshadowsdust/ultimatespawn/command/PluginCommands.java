package de.theshadowsdust.ultimatespawn.command;

import de.theshadowsdust.ultimatespawn.UltimateSpawnPlugin;
import de.theshadowsdust.ultimatespawn.service.LanguageService;
import de.theshadowsdust.ultimatespawn.util.MessageUtil;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotation.specifier.Greedy;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;

public final class PluginCommands {

    private final UltimateSpawnPlugin plugin;
    private final LanguageService languageService;

    public PluginCommands(@NotNull UltimateSpawnPlugin plugin) {
        this.plugin = plugin;
        this.languageService = plugin.getLanguageService();
    }

    @Command("ultimatespawn config")
    @Permission("ultimatespawn.command.config")
    public void executeMainCommand(CommandSender commandSender) {

        String prefix = this.languageService.getPluginPrefix();
        String language = this.plugin.getConfigurationService().getConfig().getLanguage();
        boolean joinAtSpawn = this.plugin.getConfigurationService().getConfig().isJoinAtSpawn();
        boolean respawnAtSpawn = this.plugin.getConfigurationService().getConfig().isRespawnAtSpawn();

        commandSender.sendMessage(MessageUtil.translateLegacyColorCodes("%s &7Language: &6%s".formatted(prefix, language)));
        commandSender.sendMessage(MessageUtil.translateLegacyColorCodes("%s &7Join at Spawn: &6%s".formatted(prefix, joinAtSpawn)));
        commandSender.sendMessage(MessageUtil.translateLegacyColorCodes("%s &7Respawn at Spawn: &6%s".formatted(prefix, respawnAtSpawn)));
    }

    @Command("ultimatespawn config reload")
    @Permission("ultimatespawn.command.reload")
    @CommandDescription("Reload the Plugin")
    public void executeReloadCommand(CommandSender commandSender) {
        this.plugin.getConfigurationService().loadConfig();
        this.plugin.getLanguageService().loadMessages();
        commandSender.sendMessage(this.plugin.getLanguageService().getMessage("command.reload.success",
                languageService.getPluginPrefix(),
                this.plugin.getName()));
    }

    @Command("ultimatespawn help [query]")
    @CommandDescription("Shows the help menu")
    @Permission("ultimatespawn.command.help")
    private void helpCommand(CommandSender sender, final @Argument("query") @Greedy String query) {
        this.plugin.getCommandService().getMinecraftHelp().queryCommands(query == null ? "" : query, sender);
    }
}
