import com.github.gradle.node.npm.task.NpmTask
import com.github.gradle.node.npm.task.NpmInstallTask

plugins {
  java
  id("com.github.node-gradle.node") version "3.5.0"
}

repositories {
  mavenCentral()
}

node {
  version.set("18.12.1")
  npmVersion.set("8.19.2")
  download.set(true)
  npmInstallCommand.set("install")
}

val npmInstallTask = tasks.named<NpmInstallTask>("npmInstall")

tasks.register<NpmTask>("buildNpm") {
  dependsOn(npmInstallTask)
  npmCommand.set(listOf("run", "build"))
}

tasks.build {
  finalizedBy("buildNpm")
}
