package de.theshadowsdust.ultimatespawn.command.mapper;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.incendo.cloud.SenderMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public final class BukkitSenderMapper implements SenderMapper<CommandSourceStack, CommandSender> {

    private final ConsoleSenderMapper consoleSenderMapper = new ConsoleSenderMapper();

    @Override
    public @NonNull CommandSender map(@NonNull CommandSourceStack base) {
        if (base.getSender() instanceof Player player) {
            return player;
        } else if (base.getSender() instanceof BlockCommandSender blockCommandSender) {
            return blockCommandSender;
        }

        return Bukkit.getConsoleSender();
    }

    @Override
    public @NonNull CommandSourceStack reverse(@NonNull CommandSender mapped) {
        if (mapped instanceof ConsoleCommandSender) return consoleSenderMapper;
        if (mapped instanceof Player player) return new PlayerSenderMapper(player);
        return new BlockSenderMapper((BlockCommandSender) mapped);
    }

    private record BlockSenderMapper(BlockCommandSender blockCommandSender) implements CommandSourceStack {

        @Override
        public @NotNull Location getLocation() {
            return this.blockCommandSender.getBlock().getLocation();
        }

        @Override
        public @NotNull CommandSender getSender() {
            return this.blockCommandSender;
        }

        @Override
        public @Nullable Entity getExecutor() {
            return null; // Block command senders do not have an associated entity.
        }

        @Override
        public @NonNull Player getPlayerOrThrow() {
            return getLocation().getNearbyEntitiesByType(Player.class, 16)
                    .stream()
                    .filter(player -> player.getLocation().distance(getLocation()) <= 1.0D)
                    .findFirst()
                    .orElseThrow();
        }

        @Override
        public @NonNull Entity getEntityOrThrow() {
            return getLocation().getNearbyEntitiesByType(Entity.class, 16)
                    .stream()
                    .filter(entity -> entity.getLocation().distance(getLocation()) <= 1.0D)
                    .findFirst()
                    .orElseThrow();
        }

        @Override
        @NotNull
        public CommandSourceStack withLocation(@NotNull Location location) {
            return this; //Not needed for block command senders.
        }

        @Override
        @NotNull
        public CommandSourceStack withExecutor(@NotNull Entity executor) {
            return this;
        }
    }

    private record ConsoleSenderMapper() implements CommandSourceStack {

        @Override
        public @NotNull Location getLocation() {
            return Bukkit.getWorlds().getFirst().getSpawnLocation();
        }

        @Override
        public @NotNull CommandSender getSender() {
            return Bukkit.getConsoleSender();
        }

        @Override
        public @Nullable Entity getExecutor() {
            return null;
        }

        @Override
        public @NonNull Player getPlayerOrThrow() {
            throw new IllegalCallerException("A Console cannot be an Player.");
        }

        @Override
        public @NonNull Entity getEntityOrThrow() {
            throw new IllegalCallerException("A Console cannot be an Entity.");
        }

        @Override
        @NotNull
        public CommandSourceStack withLocation(@NotNull Location location) {
            throw new UnsupportedOperationException("Cannot change location of console sender");
        }

        @Override
        @NotNull
        public CommandSourceStack withExecutor(@NotNull Entity executor) {
            return this;
        }
    }

    private record PlayerSenderMapper(Player player) implements CommandSourceStack {
        @Override
        public @NotNull Location getLocation() {
            return this.player.getLocation();
        }

        @Override
        public @NotNull CommandSender getSender() {
            return this.player;
        }

        @Override
        public @Nullable Entity getExecutor() {
            return this.player;
        }

        @Override
        public @NonNull Player getPlayerOrThrow() {
            return this.player();
        }

        @Override
        public @NonNull Entity getEntityOrThrow() {
            return this.player();
        }

        @Override
        @NotNull
        public CommandSourceStack withLocation(@NotNull Location location) {
            return this;
        }

        @Override
        @NotNull
        public CommandSourceStack withExecutor(@NotNull Entity executor) {
            return this;
        }
    }
}

