plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("io.github.goooler.shadow") version "8.1.7"
}

group = "net.zaskamlen.counterminebuild"
version = "SNAPSHOT"

bukkit {
    name = "CounterMineBuild"
    main = "net.zaskamlen.counterminebuild.CounterMineBuild"
    apiVersion = "1.13"
    authors = listOf("zaskamlen")
    commands {
        register("region")
        register("example") {
            description = "Executes the example command"
            usage = "/example"
        }
    }
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") // PaperAPI
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://jitpack.io")
}

dependencies {
    paperweight.paperDevBundle("1.21-R0.1-SNAPSHOT")

    implementation("net.minestom:minestom-snapshots:8ea7760e6a")
    implementation("dev.hollowcube:polar:1.10.0")

    implementation("com.fasterxml.jackson.core:jackson-core:2.17.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.17.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    jar {
        enabled = false
    }
    assemble {
        dependsOn(reobfJar)
    }
}
