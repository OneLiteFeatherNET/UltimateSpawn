rootProject.name = "UltimateSpawn"
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {

            version("paper", "26.3.build.+")
            version("apacheCommons", "3.20.0")
            version("geyser", "2.11.2-SNAPSHOT")

            plugin("pluginYaml", "net.minecrell.plugin-yml.paper").version("0.6.0")
            plugin("shadow", "com.gradleup.shadow").version("9.6.1")
            plugin("runServer", "xyz.jpenilla.run-paper").version("3.1.0")

            library("adventureBukkit", "net.kyori", "adventure-platform-bukkit").version("4.4.1")
            library("paper", "io.papermc.paper", "paper-api").versionRef("paper")
            library("geyser", "org.geysermc.geyser", "api").versionRef("geyser")

            library("cloudPaper", "org.incendo", "cloud-paper").version("2.0.1")
            library("cloudAnnotations", "org.incendo", "cloud-annotations").version("2.1.0")
            library("cloudExtras", "org.incendo", "cloud-minecraft-extras").version("2.0.1")

            library("apacheCommons", "org.apache.commons", "commons-lang3").versionRef("apacheCommons")

            bundle("cloud", listOf("cloudPaper", "cloudAnnotations", "cloudExtras"))
        }
    }
}
