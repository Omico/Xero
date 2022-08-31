@file:Suppress("UnstableApiUsage")

import me.omico.age.dsl.configureAndroidCommon

plugins {
    id("build-logic.root-project.base")
    id("build-logic.spotless")
}

allprojects {
    configureAppSigningConfigsForRelease()
    configureAndroidCommon {
        namespace = androidNamespace
        compileSdk = 33
        buildToolsVersion = "33.0.0"
        defaultConfig {
            minSdk = 26
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
        composeOptions {
            kotlinCompilerExtensionVersion = versions.androidx.compose.compiler
        }
    }
}

val wrapper: Wrapper by tasks.named<Wrapper>("wrapper") {
    gradleVersion = versions.gradle
    distributionType = Wrapper.DistributionType.BIN
}

val Project.androidNamespace
    get() = path.replace(":", ".")
        .let { if (it == ".app") "" else it.replace("-", ".") }
        .let { extra["xero.project.group"] as String + it }
