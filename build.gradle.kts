plugins {
    `maven-publish`
    signing
    kotlin("multiplatform") version "1.8.21"
    kotlin("plugin.serialization") version "1.8.10"
    id("org.jetbrains.dokka") version "1.8.10"
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
    id("org.owasp.dependencycheck") version "8.2.1"
}

group = property("GROUP")!!
version = property("VERSION")!!

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
    }
    linuxX64()
    mingwX64()
    macosX64()

    sourceSets {
        all {
            languageSettings.optIn("kotlinx.serialization.ExperimentalSerializationApi")
        }
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(kotlin("test"))
                implementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
                runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
            }
        }
    }
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }
    create<Jar>("dokkaJar") {
        group = JavaBasePlugin.DOCUMENTATION_GROUP
        archiveClassifier.set("javadoc")
        from(dokkaHtml)
    }
}

dependencyCheck {
    analyzers.assemblyEnabled = false
}

publishing {
    publications {
        withType<MavenPublication> {
            artifact(tasks.findByName("dokkaJar"))
            pom {
                description.set("SARIF data models for Kotlinx serialization")
                name.set(rootProject.name)
                url.set("https://detekt.github.io/detekt")
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("Chao Zhang")
                        name.set("Chao Zhang")
                        email.set("zhangchao6865@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/detekt/sarif4k")
                }
            }
        }
    }
}

if (findProperty("signing.keyId") != null) {
    signing {
        publishing.publications.withType(MavenPublication::class).configureEach {
            sign(this)
        }
    }
} else {
    logger.lifecycle("Signing Disabled as the PGP key was not found")
}

nexusPublishing {
    repositories {
        sonatype()
    }
}
