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
    jacoco
}

// test commands
// ./gradlew testDebugUnitTest → all unit tests in all modules
// ./gradlew connectedDebugAndroidTest → all Android tests (requires emulator/device)
// ./gradlew testDebugUnitTest/connectedDebugAndroidTest --rerun-tasks, Force Re-run with --rerun-tasks OR
// ./gradlew clean testDebugUnitTest/connectedDebugAndroidTest, Clean the Build Before Running

// ktlint commands
// ./gradlew ktlintCheck
// ./gradlew ktlintFormat

// detekt commands
// ./gradlew detekt

subprojects {
    // ✅ Apply JaCoCo to all subprojects
    apply(plugin = "jacoco")

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

    // ✅ Configure JaCoCo for each subproject
    configure<JacocoPluginExtension> {
        toolVersion = "0.8.11"
    }

    // ✅ Enable test coverage for Android modules
    pluginManager.withPlugin("com.android.application") {
        extensions.configure<com.android.build.gradle.AppExtension> {
            buildTypes {
                getByName("debug") {
                    enableUnitTestCoverage = true
                    enableAndroidTestCoverage = true
                }
            }
        }
    }

    pluginManager.withPlugin("com.android.library") {
        extensions.configure<com.android.build.gradle.LibraryExtension> {
            buildTypes {
                getByName("debug") {
                    enableUnitTestCoverage = true
                    enableAndroidTestCoverage = true
                }
            }
        }
    }

    // ✅ Unit test logs in console
    tasks.withType<Test>().configureEach {
        // Dynamically sets the maximum number of parallel test forks to the number of available processors.
        // This ensures optimal CPU utilization by allowing test tasks to run concurrently
        // without exceeding the system's processing capacity.
        maxParallelForks = Runtime.getRuntime().availableProcessors()
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

        // ✅ FIXED: Only finalize with jacocoTestReport if it exists
        // report is always generated after tests run
        tasks.findByName("jacocoTestReport")?.let { jacocoTask ->
            finalizedBy(jacocoTask)
        }
    }

    // ✅ FIXED: Create jacocoTestReport task for modules that have tests
    afterEvaluate {
        val hasUnitTests =
            file("$projectDir/src/test").exists() &&
                file("$projectDir/src/test").listFiles()?.isNotEmpty() == true

        val hasAndroidTests =
            file("$projectDir/src/androidTest").exists() &&
                file("$projectDir/src/androidTest").listFiles()?.isNotEmpty() == true

        if (hasUnitTests || hasAndroidTests) {
            tasks.register<JacocoReport>("jacocoTestReport") {
                group = "Reporting"
                description = "Generate JaCoCo coverage reports for ${project.name}"

                // Depend on appropriate test tasks based on what tests exist
                if (hasUnitTests) {
                    dependsOn(tasks.withType<Test>())
                }
                if (hasAndroidTests) {
                    // For Android modules, also depend on instrumented test tasks
                    tasks.findByName("createDebugCoverageReport")?.let { dependsOn(it) }
                    tasks.findByName("connectedDebugAndroidTest")?.let { dependsOn(it) }
                }

                reports {
                    xml.required.set(true)
                    html.required.set(true)
                    html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/test/html"))
                }

                // Configure source and class directories
                val javaMainSrc = "$projectDir/src/main/java"
                val kotlinMainSrc = "$projectDir/src/main/kotlin"
                val sourceDirs = mutableListOf<String>()

                if (file(javaMainSrc).exists()) {
                    sourceDirs.add(javaMainSrc)
                }
                if (file(kotlinMainSrc).exists()) {
                    sourceDirs.add(kotlinMainSrc)
                }

                sourceDirectories.setFrom(files(sourceDirs))

                val fileFilter =
                    listOf(
                        "**/R.class",
                        "**/R$*.class",
                        "**/BuildConfig.*",
                        "**/Manifest*.*",
                        "**/*Test*.*",
                        "**/*\$*.*",
                        "**/mapper/**",
                        "**/di/**",
                    )

                // Handle class directories based on project type
                pluginManager.withPlugin("com.android.library") {
                    val debugTree =
                        fileTree("${layout.buildDirectory.get()}/tmp/kotlin-classes/debug") {
                            exclude(fileFilter)
                        }
                    classDirectories.setFrom(debugTree)
                }

                pluginManager.withPlugin("com.android.application") {
                    val debugTree =
                        fileTree("${layout.buildDirectory.get()}/tmp/kotlin-classes/debug") {
                            exclude(fileFilter)
                        }
                    classDirectories.setFrom(debugTree)
                }

                pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
                    val mainTree =
                        fileTree("${layout.buildDirectory.get()}/classes/kotlin/main") {
                            exclude(fileFilter)
                        }
                    classDirectories.setFrom(mainTree)
                }

                // ✅ FIXED: Include execution data from both unit tests and Android tests
                val executionDataFiles = mutableListOf<String>()

                if (hasUnitTests) {
                    executionDataFiles.addAll(
                        listOf(
                            "jacoco/testDebugUnitTest.exec",
                            "jacoco/test.exec",
                            "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec",
                        ),
                    )
                }

                if (hasAndroidTests) {
                    executionDataFiles.addAll(
                        listOf(
                            "outputs/code_coverage/debugAndroidTest/connected/**/*.ec",
                            "jacoco/createDebugCoverageReport.exec",
                        ),
                    )
                }

                executionData.setFrom(
                    fileTree(layout.buildDirectory.get()).matching {
                        include(executionDataFiles)
                    },
                )
            }
        }
    }
}

// =====================
// JaCoCo Report Task
// =====================
tasks.register<JacocoReport>("jacocoFullCoverageReportAllModules") {
    group = "Reports"
    description = "Generate JaCoCo coverage reports (Unit + Instrumented) for all modules"

    val fileFilter =
        listOf(
            // Android-specific generated files
            "**/R.class",
            "**/R$*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*",
            "**/resources/**",
            "**/values/**",
            // Test files
            "**/*Test*.*",
            "**/*Test$*.*",
            "**/androidTest/**",
            "**/test/**",
            // Hilt/Dagger-generated code
            "**/hilt_aggregated_deps/**",
            "**/dagger/hilt/internal/**",
            "**/dagger/hilt/android/internal/**",
            "**/*_MembersInjector.class",
            "**/Dagger*Component.class",
            "**/*Module_*Factory.class",
            "**/*_Factory.class",
            "**/*_Provide*Factory.class",
            "**/*_Impl.class",
            // Kotlin-generated classes
            "**/*\$Lambda$*.*",
            "**/*\$inlined$*.*",
            "**/*\$*.*", // anonymous classes and lambdas
            "**/Companion.class",
            // Navigation safe args (generated)
            "**/Directions*.class",
            "**/*Args.class",
            // Jetpack Compose compiler-generated
            "**/*Preview*.*",
            "**/*ComposableSingletons*.*",
            // Room and other annotation processors
            "**/*_Impl.class",
            "**/*Serializer.class", // For Moshi, Retrofit, etc.
            // Miscellaneous
            "android/**/*.*",
            // Project-specific exclusions
            "**/di/**",
            "**/state/**",
            "**/mapper/**",
            "**/domain/**",
            "**/Application.class",
            "**/MainActivity.class",
            "**/theme/**",
            "**/ui/theme/**",
        )

    val javaClasses = mutableListOf<FileTree>()
    val kotlinClasses = mutableListOf<FileTree>()
    val javaSrc = mutableListOf<String>()
    val kotlinSrc = mutableListOf<String>()
    val testSourceDirs = mutableListOf<String>()
    val execution = mutableListOf<FileTree>()

    rootProject.subprojects.forEach { proj ->
        // Check if test source directories exist before depending on tasks
        val hasUnitTests =
            file("${proj.projectDir}/src/test").exists() &&
                file("${proj.projectDir}/src/test").listFiles()?.isNotEmpty() == true
        val hasAndroidTests =
            file("${proj.projectDir}/src/androidTest").exists() &&
                file("${proj.projectDir}/src/androidTest").listFiles()?.isNotEmpty() == true

        // ✅ Fixed: Proper task dependency management
        if (hasUnitTests) {
            proj.tasks.findByName("testDebugUnitTest")?.let { dependsOn(it) }
        }
        if (hasAndroidTests) {
            proj.tasks.findByName("createDebugCoverageReport")?.let { dependsOn(it) }
            proj.tasks.findByName("connectedDebugAndroidTest")?.let { dependsOn(it) }
        }

        // ✅ Improved: Better class file detection
        val javacDir = "${proj.layout.buildDirectory.get()}/intermediates/javac/debug"
        val kotlinClassesDir = "${proj.layout.buildDirectory.get()}/tmp/kotlin-classes/debug"

        if (file(javacDir).exists()) {
            javaClasses.add(
                proj.fileTree(javacDir) {
                    exclude(fileFilter)
                },
            )
        }

        if (file(kotlinClassesDir).exists()) {
            kotlinClasses.add(
                proj.fileTree(kotlinClassesDir) {
                    exclude(fileFilter)
                },
            )
        }

        // Only add source directories if they exist
        val javaMainSrc = "${proj.projectDir}/src/main/java"
        val kotlinMainSrc = "${proj.projectDir}/src/main/kotlin"
        val javaTestSrc = "$projectDir/src/test/java"
        val kotlinTestSrc = "$projectDir/src/test/kotlin"

        if (file(javaMainSrc).exists()) {
            javaSrc.add(javaMainSrc)
        }
        if (file(kotlinMainSrc).exists()) {
            kotlinSrc.add(kotlinMainSrc)
        }
        if (file(javaTestSrc).exists()) {
            testSourceDirs.add(javaTestSrc)
        }
        if (file(kotlinTestSrc).exists()) {
            testSourceDirs.add(kotlinTestSrc)
        }

        // ✅ Fixed: More comprehensive execution data collection
        execution.add(
            proj.fileTree("${proj.layout.buildDirectory.get()}") {
                include(
                    "jacoco/testDebugUnitTest.exec",
                    "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec",
                    "outputs/code_coverage/debugAndroidTest/connected/**/*.ec",
                    "jacoco/*.exec", // All JaCoCo execution files
                )
            },
        )
    }

    sourceDirectories.setFrom(files(javaSrc + kotlinSrc + testSourceDirs))
    classDirectories.setFrom(files(javaClasses + kotlinClasses))
    executionData.setFrom(files(execution))

    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(file("${rootProject.layout.buildDirectory.get()}/coverage-report"))
    }

    doLast {
        println("✅ Combined coverage report generated at:")
        println("📄 file://${reports.html.outputLocation.get()}/index.html")
    }
}

// =============================
// Run-All Test + Report Task : ./gradlew runAllCoverageAndReport
// =============================
tasks.register("runAllCoverageAndReport") {
    group = "Verification"
    description = "Runs unit + UI tests across all modules and generates a full Jacoco report"

    val testTaskPaths = mutableListOf<String>()

    rootProject.subprojects.forEach { proj ->
        // ✅ Fixed: Use same conditional logic as main JaCoCo task
        val hasUnitTests =
            file("${proj.projectDir}/src/test").exists() &&
                file("${proj.projectDir}/src/test").listFiles()?.isNotEmpty() == true
        val hasAndroidTests =
            file("${proj.projectDir}/src/androidTest").exists() &&
                file("${proj.projectDir}/src/androidTest").listFiles()?.isNotEmpty() == true

        if (hasUnitTests) {
            proj.tasks.matching { it.name == "testDebugUnitTest" }.forEach {
                testTaskPaths.add(it.path)
            }
        }
        if (hasAndroidTests) {
            proj.tasks.matching { it.name == "connectedDebugAndroidTest" }.forEach {
                testTaskPaths.add(it.path)
            }
            proj.tasks.matching { it.name == "createDebugCoverageReport" }.forEach {
                testTaskPaths.add(it.path)
            }
        }
    }

    println("Running test tasks:")
    testTaskPaths.forEach { println(" - $it") }

    dependsOn(testTaskPaths)
    dependsOn("jacocoFullCoverageReportAllModules")
}
