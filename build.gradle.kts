import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `maven-publish`
    signing
    kotlin("multiplatform") version "2.2.20"
    kotlin("plugin.serialization") version "1.9.25"
    id("org.jetbrains.dokka") version "2.0.0"
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

group = property("GROUP")!!
version = property("VERSION")!!

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_1_8
        }
    }
    linuxX64()
    mingwX64()
    macosX64()
    macosArm64()

    sourceSets {
        all {
            languageSettings.optIn("kotlinx.serialization.ExperimentalSerializationApi")
        }
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(kotlin("test"))
                implementation("org.junit.platform:junit-platform-launcher")
                implementation("org.junit.jupiter:junit-jupiter:5.14.0")
            }
        }
    }
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }
    register<Jar>("dokkaJar") {
        group = JavaBasePlugin.DOCUMENTATION_GROUP
        archiveClassifier.set("javadoc")
        from(dokkaHtml)
    }
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

// https://github.com/gradle-nexus/publish-plugin/issues/208
val signingTasks: TaskCollection<Sign> = tasks.withType<Sign>()
tasks.withType<PublishToMavenRepository>().configureEach {
    mustRunAfter(signingTasks)
}

nexusPublishing {
    repositories {
        sonatype()
    }
}
