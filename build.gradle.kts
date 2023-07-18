import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("java")
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("xyz.jpenilla.run-paper") version "1.0.6"
}

group = "de.theshadowsdust"
version = "2.0.0-RELEASE"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    implementation("cloud.commandframework", "cloud-paper", "1.8.3")
    implementation("cloud.commandframework", "cloud-annotations", "1.8.3")
    implementation("cloud.commandframework", "cloud-minecraft-extras", "1.8.3")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("me.lucko:commodore:2.2") {
        isTransitive = false
    }
}

tasks {

    compileJava {
        options.release.set(17)
        options.encoding = "UTF-8"
    }

    runServer {
        minecraftVersion("1.20.1")
    }

    shadowJar {
        archiveFileName.set("${rootProject.name}.${archiveExtension.getOrElse("jar")}")
    }
}

paper {

    name = rootProject.name
    author = "theShadowsDust"

    main = "de.theshadowsdust.ultimatespawn.UltimateSpawnPlugin"
    bootstrapper = "de.theshadowsdust.ultimatespawn.UltimateSpawnPluginBootstrap"
    hasOpenClassloader = false

    generateLibrariesJson = true
    foliaSupported = true
    apiVersion = "1.20"
    load = BukkitPluginDescription.PluginLoadOrder.POSTWORLD
}

