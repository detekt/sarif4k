plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.vanniktechPublish)
    alias(libs.plugins.tapmoc)
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
                api(libs.kotlinx.io)
                api(libs.kotlinx.serialization.json)
                api(libs.kotlinx.serialization.json.io)
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
                runtimeOnly(libs.junit.launcher)
                implementation(libs.junit)
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
