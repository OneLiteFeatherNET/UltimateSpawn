package de.theshadowsdust.ultimatespawn.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class Constants {

    public static final Gson GSON = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

}
