package de.uniquegame.ultimatespawn.command;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Greedy;
import de.uniquegame.ultimatespawn.UltimateSpawnPlugin;
import de.uniquegame.ultimatespawn.service.LanguageService;
import de.uniquegame.ultimatespawn.util.MessageUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class PluginCommands {

    private final UltimateSpawnPlugin plugin;
    private final LanguageService languageService;

    public PluginCommands(@NotNull UltimateSpawnPlugin plugin) {
        this.plugin = plugin;
        this.languageService = plugin.getLanguageService();
    }

    @CommandMethod("ultimatespawn config")
    @CommandPermission("ultimatespawn.command.config")
    public void executeMainCommand(CommandSender commandSender) {

        String prefix = this.languageService.getPluginPrefix();
        String language = this.plugin.getConfigurationService().getConfig().getLanguage();
        boolean joinAtSpawn = this.plugin.getConfigurationService().getConfig().isJoinAtSpawn();
        boolean respawnAtSpawn = this.plugin.getConfigurationService().getConfig().isRespawnAtSpawn();

        commandSender.sendMessage(MessageUtil.translateLegacyColorCodes("%s &7Language: &6%s".formatted(prefix, language)));
        commandSender.sendMessage(MessageUtil.translateLegacyColorCodes("%s &7Join at Spawn: &6%s".formatted(prefix, joinAtSpawn)));
        commandSender.sendMessage(MessageUtil.translateLegacyColorCodes("%s &7Respawn at Spawn: &6%s".formatted(prefix, respawnAtSpawn)));
    }

    @CommandMethod("ultimatespawn config reload")
    @CommandPermission("ultimatespawn.command.reload")
    @CommandDescription("Reload the Plugin")
    public void executeReloadCommand(CommandSender commandSender) {
        this.plugin.getConfigurationService().loadConfig();
        this.plugin.getLanguageService().loadMessages();
        commandSender.sendMessage(this.plugin.getLanguageService().getMessage("command.reload.success",
                languageService.getPluginPrefix(),
                this.plugin.getName()));
    }

    @CommandDescription("Shows the help menu")
    @CommandMethod("ultimatespawn help [query]")
    @CommandPermission("ultimatespawn.command.help")
    private void helpCommand(CommandSender sender, final @Argument("query") @Greedy String query) {
        this.plugin.getCommandService().getMinecraftHelp().queryCommands(query == null ? "" : query, sender);
    }
}
