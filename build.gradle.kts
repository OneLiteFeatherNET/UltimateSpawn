import net.minecrell.pluginyml.bukkit.BukkitPluginDescription.PluginLoadOrder

plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("xyz.jpenilla.run-paper") version "1.0.6"
}

group = "de.uniquegame"
version = "1.0.0-RELEASE"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    implementation("cloud.commandframework", "cloud-paper", "1.8.0")
    implementation("cloud.commandframework", "cloud-annotations", "1.8.0")
    implementation("cloud.commandframework", "cloud-minecraft-extras", "1.8.0")
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

    build {
        dependsOn(shadowJar)
    }

    runServer {
        minecraftVersion("1.19.3")
    }

    shadowJar {
        archiveFileName.set("${rootProject.name}.${archiveExtension.getOrElse("jar")}")
    }
}

bukkit {
    main = "${rootProject.group}.ultimatespawn.UltimateSpawnPlugin"
    apiVersion = "1.19"
    name = "UltimateSpawn"
    load = PluginLoadOrder.POSTWORLD
    author = "UniqueGame"
}