plugins {
    kotlin("multiplatform") version "2.4.10"
    kotlin("plugin.serialization") version "2.3.20"
    id("org.jetbrains.dokka") version "2.2.0"
    id("com.vanniktech.maven.publish") version "0.37.0"
    id("com.gradleup.tapmoc") version "0.4.2"
}

group = property("GROUP")!!
version = property("VERSION")!!

repositories {
    mavenCentral()
}

kotlin {
    compilerOptions {
        explicitApi()
        allWarningsAsErrors = true
        extraWarnings = true
    }
    @OptIn(org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation::class)
    abiValidation {
        enabled = true
    }
    jvm()
    linuxX64()
    linuxArm64()
    mingwX64()
    macosArm64()

    sourceSets {
        all {
            languageSettings.optIn("kotlinx.serialization.ExperimentalSerializationApi")
        }
        getByName("commonMain") {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-io-core:0.8.2")
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-io:1.9.0")
            }
        }
        getByName("commonTest") {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        getByName("jvmTest") {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(kotlin("test"))
                implementation("org.junit.platform:junit-platform-launcher")
                implementation("org.junit.jupiter:junit-jupiter:5.14.4")
            }
        }
    }
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)

    coordinates(artifactId = rootProject.name)

    pom {
        name = rootProject.name
        description = "SARIF data models for Kotlinx serialization"
        url = "https://detekt.github.io/detekt"
        licenses {
            license {
                name = "The Apache Software License, Version 2.0"
                url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "repo"
            }
        }
        developers {
            developer {
                id = "Detekt Maintainers"
                name = "Detekt Maintainers"
                email = "info@detekt.dev"
            }
        }
        scm {
            url = "https://github.com/detekt/sarif4k"
        }
    }
}

tapmoc {
    java(8)
    kotlin("2.2.0")

    checkDependencies()
}
