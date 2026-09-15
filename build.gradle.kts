import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    id("java")
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
    id("com.gradleup.shadow").version("9.4.1")
    id("xyz.jpenilla.run-paper") version "3.1.0"
    `maven-publish`
}

group = "net.onelitefeather"
version = "2.0.2" // x-release-please-version

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.opencollab.dev/main/")
}

dependencies {
    compileOnly(libs.paper)
    compileOnly(libs.geyser)
    implementation(libs.adventureBukkit)
    implementation(libs.bundles.cloud)
    implementation(libs.apacheCommons)
}

tasks {

    compileJava {
        options.release.set(21)
        options.encoding = "UTF-8"
    }

    runServer {
        minecraftVersion("1.21.10")
        jvmArgs("-Dcom.mojang.eula.agree=true")
    }

    shadowJar {
        archiveFileName.set("${rootProject.name}-${rootProject.version}.${archiveExtension.getOrElse("jar")}")
    }
}

paper {

    name = rootProject.name
    author = "theShadowsDust"
    main = "de.theshadowsdust.ultimatespawn.UltimateSpawnPlugin"
    hasOpenClassloader = false

    generateLibrariesJson = false
    foliaSupported = false
    apiVersion = "1.20"
    load = BukkitPluginDescription.PluginLoadOrder.POSTWORLD
    bootstrapDependencies {
        register("Geyser-Spigot") {
            required = false
            load = PaperPluginDescription.RelativeLoadOrder.AFTER
        }
    }
    serverDependencies {
        register("Geyser-Spigot") {
            required = false
        }
    }
}

publishing {
    repositories {
        maven {
            authentication {
                credentials(PasswordCredentials::class) {
                    // Those credentials need to be set under "Settings -> Secrets -> Actions" in your repository
                    username = System.getenv("ONELITEFEATHER_MAVEN_USERNAME")
                    password = System.getenv("ONELITEFEATHER_MAVEN_PASSWORD")
                }
            }
            name = "OneLiteFeatherRepository"
            url = if (project.version.toString().contains("SNAPSHOT")) {
                uri("https://repo.onelitefeather.dev/onelitefeather-snapshots")
            } else {
                uri("https://repo.onelitefeather.dev/onelitefeather-releases")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            artifact(rootProject.tasks.getByName("shadowJar"))
            version = rootProject.version as String
            artifactId = "ultimate-spawn"
            groupId = rootProject.group as String
            pom {
                description.set("A simple spawn command plugin")
                name = "UltimateSpawn"
                url = "https://github.com/OneLiteFeatherNET/UltimateSpawn"
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        name.set("theShadowsDust")
                        contributors {
                            contributor {
                                name.set("TheMeinerLP")
                            }
                            contributor {
                                name.set("OneLiteFeather")
                            }
                            contributor {
                                name.set("theEvilReaper")
                            }
                        }
                    }
                }

                issueManagement {
                    system.set("Github")
                    url.set("https://github.com/OneLiteFeatherNET/UltimateSpawn/issues")
                }

                scm {
                    connection = "scm:git:git://github.com:OneLiteFeatherNET/UltimateSpawn.git"
                    developerConnection = "scm:git:ssh://git@github.com:OneLiteFeatherNET/UltimateSpawn.git"
                    url = "https://github.com/OneLiteFeatherNET/UltimateSpawn"
                }
            }
        }
    }
}