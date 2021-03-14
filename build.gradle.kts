plugins {
    `java-library`
    `maven-publish`
    signing
    kotlin("jvm") version "1.4.31"
    kotlin("plugin.serialization") version "1.4.31"
    id("io.codearte.nexus-staging") version "0.22.0"
}

group = "io.github.detekt.${rootProject.name}"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
    testImplementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    testImplementation("junit:junit:4.13.2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withJavadocJar()
    withSourcesJar()
}

tasks.withType(Javadoc::class).configureEach {
    val customArgs = projectDir.resolve("javadoc-silence.txt")
    customArgs.writeText("""
        -Xdoclint:none
    """.trimIndent())
    options.optionFiles?.add(customArgs)
}

val sonatypeUsername: String? =
        findProperty("sonatypeUsername")?.toString()
                ?: System.getenv("MAVEN_CENTRAL_USER")

val sonatypePassword: String? =
        findProperty("sonatypePassword")?.toString()
                ?: System.getenv("MAVEN_CENTRAL_PW")

publishing {
    repositories {
        maven {
            name = "mavenCentral"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
            credentials {
                username = sonatypeUsername
                password = sonatypePassword
            }
        }
        maven {
            name = "mavenSnapshot"
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
            credentials {
                username = sonatypeUsername
                password = sonatypePassword
            }
        }
    }
    publications.register<MavenPublication>(rootProject.name) {
        groupId = project.group as? String
        artifactId = project.name
        version = project.version as? String
        from(components["java"])
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

if (findProperty("signing.keyId") != null) {
    signing {
        sign(publishing.publications[rootProject.name])
    }
}

nexusStaging {
    packageGroup = "io.github.detekt"
    stagingProfileId = "117c7a00a4d531"
    username = sonatypeUsername
    password = sonatypePassword
}
