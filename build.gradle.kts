plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.2"
    id("io.franzbecker.gradle-lombok") version "5.0.0"
}

group =
    "ru.itis.kpfu"
version =
    "1.0.1"

lombok {
    version ="1.18.22"
}

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set(
        "2023.2.5"
    )
    type.set(
        "IC"
    ) // Target IDE Platform

    plugins.set(
        listOf("Git4Idea")
    )
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility =
            "17"
        targetCompatibility =
            "17"
    }

    patchPluginXml {
        sinceBuild.set(
            "232"
        )
        untilBuild.set(
            "242.*"
        )
    }

    signPlugin {
        certificateChain.set(
            System.getenv(
                "CERTIFICATE_CHAIN"
            )
        )
        privateKey.set(
            System.getenv(
                "PRIVATE_KEY"
            )
        )
        password.set(
            System.getenv(
                "PRIVATE_KEY_PASSWORD"
            )
        )
    }

    publishPlugin {
        token.set(
            System.getenv(
                "PUBLISH_TOKEN"
            )
        )
    }
}
