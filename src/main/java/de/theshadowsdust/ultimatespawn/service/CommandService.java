package de.theshadowsdust.ultimatespawn.service;

import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.arguments.parser.ParserParameters;
import cloud.commandframework.arguments.parser.StandardParameters;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.minecraft.extras.MinecraftHelp;
import cloud.commandframework.paper.PaperCommandManager;
import de.theshadowsdust.ultimatespawn.command.SpawnCommand;
import de.theshadowsdust.ultimatespawn.UltimateSpawnPlugin;
import de.theshadowsdust.ultimatespawn.command.PluginCommands;
import de.theshadowsdust.ultimatespawn.command.RemoveSpawnCommand;
import de.theshadowsdust.ultimatespawn.command.SetSpawnCommand;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public final class CommandService {

    private final UltimateSpawnPlugin plugin;
    private MinecraftHelp<CommandSender> minecraftHelp;
    private PaperCommandManager<CommandSender> paperCommandManager;

    public CommandService(@NotNull UltimateSpawnPlugin plugin) {
        this.plugin = plugin;
    }

    public void buildCommandSystem() {

        //Commands
        AnnotationParser<CommandSender> annotationParser;

        try {
            this.paperCommandManager = new PaperCommandManager<>(this.plugin,
                    CommandExecutionCoordinator.simpleCoordinator(), Function.identity(), Function.identity());
            if (this.paperCommandManager.hasCapability(CloudBukkitCapabilities.BRIGADIER)) {
                this.paperCommandManager.registerBrigadier();
            }

            if (this.paperCommandManager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
                this.paperCommandManager.registerAsynchronousCompletions();
            }

            final Function<ParserParameters, CommandMeta> commandMetaFunction = p -> CommandMeta.simple().with(CommandMeta.DESCRIPTION, p.get(StandardParameters.DESCRIPTION, "No description")).build();
            annotationParser = new AnnotationParser<>(this.paperCommandManager, CommandSender.class, commandMetaFunction);
            annotationParser.parse(new PluginCommands(this.plugin));
            annotationParser.parse(new SetSpawnCommand(this.plugin));
            annotationParser.parse(new SpawnCommand(this.plugin));
            annotationParser.parse(new RemoveSpawnCommand(this.plugin));

        } catch (final Exception e) {
            this.plugin.getLogger().warning("Failed to initialize Brigadier support: " + e.getMessage());
            this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
        }
    }

    public void buildHelpSystem() {
        this.minecraftHelp = MinecraftHelp.createNative("/ultimatespawn help", this.paperCommandManager);
        this.minecraftHelp.setHelpColors(MinecraftHelp.HelpColors.of(
                NamedTextColor.GOLD,
                NamedTextColor.GRAY,
                NamedTextColor.GOLD,
                NamedTextColor.GRAY,
                NamedTextColor.GOLD));
    }

    public MinecraftHelp<CommandSender> getMinecraftHelp() {
        return minecraftHelp;
    }
}
