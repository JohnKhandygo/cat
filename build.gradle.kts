import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"

    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    id("io.gitlab.arturbosch.detekt") version "1.21.0"
}

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

tasks {
    named<Wrapper>("wrapper") {
        gradleVersion = "7.5"
        distributionType = Wrapper.DistributionType.ALL
    }
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
}

dependencies {

    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("io.mockk:mockk:1.12.4")
}

detekt {
    buildUponDefaultConfig = true
}

tasks.withType<Test> {
    useJUnitPlatform()

    testLogging {
        events("passed", "skipped", "failed")
    }
}
