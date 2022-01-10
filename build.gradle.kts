import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") version "1.7.20"
    `maven-publish`
    signing
//    kotlin("jvm") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("org.owasp.dependencycheck") version "7.3.0"
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
                jvmTarget = "1.8"
                freeCompilerArgs = freeCompilerArgs + "-Xjvm-default=all"
//                withJavadocJar()
//                withSourcesJar()
            }
        }
    }
    linuxX64()
    mingwX64()
    macosX64()

    sourceSets {
        commonMain {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(kotlin("test"))
                implementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
                runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
            }
        }
    }
}

tasks {
    withType<KotlinCompile>().configureEach {
        this.kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-opt-in=kotlinx.serialization.ExperimentalSerializationApi")
        }
    }

    withType<Javadoc>().configureEach {
        val customArgs = projectDir.resolve("javadoc-silence.txt")
        customArgs.writeText(
            """-Xdoclint:none
            """.trimIndent()
        )
        options.optionFiles?.add(customArgs)
    }

    withType<Test>().configureEach {
        useJUnitPlatform()
    }
}

dependencyCheck {
    analyzers.assemblyEnabled = false
}

//publishing {
//    publications {
//        register<MavenPublication>(rootProject.name) {
//            groupId = project.group as? String
//            artifactId = project.name
//            version = project.version as? String
//            from(components["java"])
//            pom {
//                description.set("SARIF data models for Kotlinx serialization")
//                name.set(rootProject.name)
//                url.set("https://detekt.github.io/detekt")
//                licenses {
//                    license {
//                        name.set("The Apache Software License, Version 2.0")
//                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
//                        distribution.set("repo")
//                    }
//                }
//                developers {
//                    developer {
//                        id.set("Chao Zhang")
//                        name.set("Chao Zhang")
//                        email.set("zhangchao6865@gmail.com")
//                    }
//                }
//                scm {
//                    url.set("https://github.com/detekt/sarif4k")
//                }
//            }
//        }
//    }
//}

if (findProperty("signing.keyId") != null) {
    signing {
        sign(publishing.publications[rootProject.name])
    }
} else {
    logger.lifecycle("Signing Disabled as the PGP key was not found")
}

//nexusPublishing {
//    repositories {
//        sonatype()
//    }
//}
