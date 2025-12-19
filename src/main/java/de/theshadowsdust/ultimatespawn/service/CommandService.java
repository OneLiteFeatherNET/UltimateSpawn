package de.theshadowsdust.ultimatespawn.service;

import de.theshadowsdust.ultimatespawn.UltimateSpawnPlugin;
import de.theshadowsdust.ultimatespawn.command.PluginCommands;
import de.theshadowsdust.ultimatespawn.command.RemoveSpawnCommand;
import de.theshadowsdust.ultimatespawn.command.SetSpawnCommand;
import de.theshadowsdust.ultimatespawn.command.SpawnCommand;
import de.theshadowsdust.ultimatespawn.command.mapper.BukkitSenderMapper;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.minecraft.extras.MinecraftHelp;
import org.incendo.cloud.paper.PaperCommandManager;
import org.jetbrains.annotations.NotNull;

public final class CommandService {

    private final UltimateSpawnPlugin plugin;
    private final PaperCommandManager<CommandSender> paperCommandManager;
    private final AnnotationParser<CommandSender> annotationParser;
    private final MinecraftHelp<CommandSender> minecraftHelp;
    private final BukkitAudiences bukkitAudiences;

    public CommandService(@NotNull UltimateSpawnPlugin plugin) {
        this.plugin = plugin;
        this.paperCommandManager = buildCommandSystem();
        this.annotationParser = new AnnotationParser<>(this.paperCommandManager, CommandSender.class);
        this.bukkitAudiences = BukkitAudiences.create(plugin);
        this.minecraftHelp = buildHelpSystem(this.paperCommandManager);
        registerCommands();
    }

    public PaperCommandManager<CommandSender> buildCommandSystem() {
        return PaperCommandManager
                .builder(new BukkitSenderMapper())
                .executionCoordinator(ExecutionCoordinator.asyncCoordinator())
                .buildOnEnable(this.plugin);
    }

    private void registerCommands() {
        annotationParser.parse(new PluginCommands(this.plugin));
        annotationParser.parse(new SetSpawnCommand(this.plugin));
        annotationParser.parse(new SpawnCommand(this.plugin));
        annotationParser.parse(new RemoveSpawnCommand(this.plugin));
    }

    public MinecraftHelp<CommandSender> buildHelpSystem(PaperCommandManager<CommandSender> paperCommandManager) {
        return MinecraftHelp.<CommandSender>builder()
                .commandManager(paperCommandManager)
                .audienceProvider(this.bukkitAudiences::sender)
                .commandPrefix("/ultimatespawn")
                .colors(MinecraftHelp.helpColors(
                        NamedTextColor.GOLD,
                        NamedTextColor.GRAY,
                        NamedTextColor.GOLD,
                        NamedTextColor.GRAY,
                        NamedTextColor.GOLD))
                .build();
    }

    public MinecraftHelp<CommandSender> getMinecraftHelp() {
        return minecraftHelp;
    }
}
