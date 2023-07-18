package de.theshadowsdust.ultimatespawn.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MessageUtil {

    private MessageUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Translates a {@link String} that contains the legacy color codes
     *
     * @param text the text to translate
     * @return the translated {@link Component}
     */
    public static Component translateLegacyColorCodes(@NotNull String text) {
        return MiniMessage.miniMessage().deserialize(MiniMessage.miniMessage().
                serialize(LegacyComponentSerializer.legacyAmpersand().deserialize(text)));
    }

    /**
     * @param text the text to remove the colors
     * @return the plain text without colors
     */
    public static String stripColors(@Nullable Component text) {
        return PlainTextComponentSerializer.plainText().serialize(text != null ? text : Component.text(""));
    }

    /**
     * @param text the text to remove the colors
     * @return the plain text without colors
     */
    public static Component stripColors(@NotNull String text) {
        return PlainTextComponentSerializer.plainText().deserialize(text);
    }
}
