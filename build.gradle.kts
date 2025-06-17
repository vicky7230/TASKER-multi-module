import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
    id("androidx.room") version "2.7.1" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.3.0" apply false
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
    plugins.withId("org.jlleitschuh.gradle.ktlint") {
        extensions.configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
            println("Using ktlint version: ${version.getOrElse("default")}")
            version = "1.4.1" // ktlint version
            verbose = true
            android = true
            outputToConsole = true
            ignoreFailures = false
            reporters {
                reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
                reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
                reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.SARIF)
            }
        }
    }
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
