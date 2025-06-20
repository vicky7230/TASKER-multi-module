import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
    id("androidx.room") version "2.7.1" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.3.0" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.8" apply false
}

// test commands
// ./gradlew testDebugUnitTest → all unit tests in all modules
// ./gradlew connectedDebugAndroidTest → all Android tests (requires emulator/device)
// ./gradlew testDebugUnitTest/connectedDebugAndroidTest --rerun-tasks, Force Re-run with --rerun-tasks OR
// ./gradlew clean testDebugUnitTest/connectedDebugAndroidTest, Clean the Build Before Running

// ktlint commands
// ./gradlew ktlintCheck
// ./gradlew ktlintFormat

subprojects {
    // ✅ Ktlint for formatting
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    // Ktlint Jetpack Compose Rules
    dependencies {
        add("ktlintRuleset", "io.nlopez.compose.rules:ktlint:0.4.22")
    }

    // Configure ktlint
    extensions.configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("1.4.1") // ktlint version
        android.set(true)
        verbose.set(true)
        outputToConsole.set(true)
        ignoreFailures.set(false)
        reporters {
            reporter(ReporterType.PLAIN)
            reporter(ReporterType.CHECKSTYLE)
            reporter(ReporterType.SARIF)
        }
        // println("Using ktlint version: ${version.getOrElse("default")}") // to check the ktlint version
    }

    // ✅ detekt for static analysis (without detekt-formatting)
    apply(plugin = "io.gitlab.arturbosch.detekt")

    extensions.configure<DetektExtension> {
        config.setFrom("$rootDir/config/detekt/detekt.yml")
        buildUponDefaultConfig = true
        allRules = false
    }

    // ✅ Unit test logs in console
    tasks.withType<Test>().configureEach {
        testLogging {
            events =
                setOf(
                    TestLogEvent.PASSED,
                    TestLogEvent.SKIPPED,
                    TestLogEvent.FAILED,
                )
            exceptionFormat = TestExceptionFormat.FULL
            showStandardStreams = true
        }
    }
}
