plugins {
    kotlin("jvm") version "2.4.10"
    id("com.gradleup.shadow") version "8.3.8"
}

group = "ltd.jconet.lunchbox"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.2.build.102-stable")

    constraints {
        compileOnly("org.codehaus.plexus:plexus-utils:3.6.1") {
            because("CVE-2025-67030")
        }

        compileOnly("org.apache.commons:commons-lang3:3.18.0") {
            because("CVE-2025-48924")
        }
    }
}

kotlin {
    jvmToolchain(25)
}

tasks {
    processResources {
        filesMatching("paper-plugin.yml") {
            expand("version" to project.version)
        }
    }

    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveClassifier.set("")
    }
}