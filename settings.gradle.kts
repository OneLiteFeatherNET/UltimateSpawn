rootProject.name = "UltimateSpawn"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://eldonexus.de/repository/maven-public/")
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {

            version("paper", "1.21.10-R0.1-SNAPSHOT")
            version("apacheCommons", "3.17.0")
            version("geyser", "2.4.2-SNAPSHOT")

            plugin("pluginYaml", "net.minecrell.plugin-yml.paper").version("0.6.0")
            plugin("shadow", "com.gradleup.shadow").version("8.3.3")
            plugin("runServer", "xyz.jpenilla.run-paper").version("2.1.0")

            library("adventureBukkit", "net.kyori", "adventure-platform-bukkit").version("4.3.3")
            library("paper", "io.papermc.paper", "paper-api").versionRef("paper")
            library("geyser", "org.geysermc.geyser", "api").versionRef("geyser")

            library("cloudPaper", "org.incendo", "cloud-paper").version("2.0.0-beta.10")
            library("cloudAnnotations", "org.incendo", "cloud-annotations").version("2.0.0")
            library("cloudExtras", "org.incendo", "cloud-minecraft-extras").version("2.0.0-beta.10")

            library("apacheCommons", "org.apache.commons", "commons-lang3").versionRef("apacheCommons")

            bundle("cloud", listOf("cloudPaper", "cloudAnnotations", "cloudExtras"))
        }
    }
}