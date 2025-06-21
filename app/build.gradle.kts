import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    kotlin("plugin.serialization") version "2.0.21"
    id("com.google.devtools.ksp")
    // id("org.jlleitschuh.gradle.ktlint")
}

android {
    namespace = "com.vicky7230.tasker2"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.vicky7230.tasker2"
        minSdk = 24
        targetSdk = 35
        versionCode = 3
        versionName = "0.1.1" // Semantic Versioning (Major.Minor.Patch)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            // Attempt to load from environment variables first (for CI/CD)
            val storeFilePath = System.getenv("RELEASE_STORE_FILE")
            val storePassword = System.getenv("RELEASE_STORE_PASSWORD")
            val keyAlias = System.getenv("RELEASE_KEY_ALIAS")
            val keyPassword = System.getenv("RELEASE_KEY_PASSWORD")

            if (storeFilePath != null &&
                storePassword != null &&
                keyAlias != null &&
                keyPassword != null
            ) {
                // Keystore path will be relative to the project root on the runner
                storeFile = file(storeFilePath)
                this.storePassword = storePassword
                this.keyAlias = keyAlias
                this.keyPassword = keyPassword
                logger.lifecycle("✅ Release signing config loaded from environment variables.")
            } else {
                // Fallback for local development using local.properties
                val keystoreProperties = Properties()
                val keystorePropertiesFile = rootProject.file("local.properties")

                if (keystorePropertiesFile.exists()) {
                    keystoreProperties.load(FileInputStream(keystorePropertiesFile))

                    val localStoreFilePath = keystoreProperties["RELEASE_STORE_FILE"] as? String
                    val localStorePassword = keystoreProperties["RELEASE_STORE_PASSWORD"] as? String
                    val localKeyAlias = keystoreProperties["RELEASE_KEY_ALIAS"] as? String
                    val localKeyPassword = keystoreProperties["RELEASE_KEY_PASSWORD"] as? String

                    if (!localStoreFilePath.isNullOrEmpty() &&
                        !localStorePassword.isNullOrEmpty() &&
                        !localKeyAlias.isNullOrEmpty() &&
                        !localKeyPassword.isNullOrEmpty()
                    ) {
                        storeFile = file(localStoreFilePath)
                        this.storePassword = localStorePassword
                        this.keyAlias = localKeyAlias
                        this.keyPassword = localKeyPassword
                        logger.lifecycle("✅ Release signing config loaded from local.properties.")
                    } else {
                        logger.warn("⚠️ local.properties found but incomplete for release signing. Release build might fail.")
                    }
                } else {
                    logger.warn("⚠️ local.properties not found and release signing environment variables not set. Release build might fail.")
                }
            }
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            applicationIdSuffix = null
            versionNameSuffix = null
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            // Uses default debug keystore
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // ktlintRuleset(libs.ktlint)

    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(project(":core:common"))
    implementation(project(":core:feature_api"))

    implementation(project(":feature:notes:data"))
    implementation(project(":feature:notes:domain"))
    implementation(project(":feature:notes:ui"))

    implementation(project(":feature:add_edit_note:domain"))
    implementation(project(":feature:add_edit_note:ui"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // dagger2
    implementation(libs.dagger)
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)
    kapt(libs.dagger.android.processor)
    kapt(libs.dagger.compiler)

    // navigation compose
    implementation(libs.androidx.navigation.compose)

    // room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    testImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.androidx.room.testing)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.kotlinx.serialization)
    implementation(libs.logging.interceptor)

    // kotlinx-serialization
    implementation(libs.kotlinx.serialization.json)
}
