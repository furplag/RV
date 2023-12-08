/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * To learn more about Gradle by exploring our Samples at https://docs.gradle.org/8.5/samples
 */
/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java library project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.4/userguide/building_java_projects.html in the Gradle documentation.
 */
group = "jp.furplag.ms365dev.rv"
version = "1.0.0-SNAPSHOT"
description = "Ram Ventilation. DO NOT stop, or lost Everything ."

plugins {
  `application`
  id("com.diffplug.spotless") version "6.22.0"
}

repositories {
  mavenCentral()
  maven("https://jitpack.io/") /* furplag/relic */
 }

dependencies {
  implementation("com.azure:azure-identity:1.11.1")
  implementation("com.github.furplag:relic:5.1.0")
  implementation("com.google.guava:guava:31.1-jre")
  implementation("com.microsoft.graph:microsoft-graph:5.77.0")

  compileOnly("org.projectlombok:lombok:1.18.30")
  annotationProcessor("org.projectlombok:lombok:1.18.30")
}

spotless {
  yaml {
    target("src/**/*.yml")
    jackson()
  }
  java {
    removeUnusedImports()
    indentWithSpaces(2)
    trimTrailingWhitespace()
    endWithNewline()
  }
}

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

application {
  mainClass = "${group}.Application"
}

tasks {
  compileJava {
    dependsOn(spotlessApply)
    options.encoding = Charsets.UTF_8.name()
    options.release.set(17)
  }
  javadoc {
    options.encoding = Charsets.UTF_8.name()
  }
  processResources {
    filteringCharset = Charsets.UTF_8.name()
  }
}