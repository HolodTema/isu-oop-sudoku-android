// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.test.report.aggregation)
}

// configure internal Gradle plugin test-report-aggregation
// to put all the unit-test reports from all the modules to the one .html file
//
// so, to run all the unit-tests with one big .html report-file you need to run:
// ./gradlew test aggregateTestReport
val aggregateTestReport = tasks.register<TestReport>("aggregateTestReport") {
    group = "verification"
    description = "Generates an aggregated HTML report from all unit-tests from all the modules"

    destinationDirectory.set((layout.buildDirectory.dir("reports/all-tests")))

    val testTasks = subprojects.map { subproject ->
        subproject.tasks.matching { task ->
            task.name == "test"
                    || (task.name.startsWith("test") && task.name.endsWith("UnitTest"))
        }
    }
    testResults.from(testTasks)
}
