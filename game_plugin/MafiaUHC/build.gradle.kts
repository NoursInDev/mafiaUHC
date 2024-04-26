plugins {
    kotlin("jvm") version "1.9.21"
}

group = "org.noursindev.mafiauhc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(files("spigot_lib/spigot-1.8.8-R0.1-SNAPSHOT-latest.jar"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(8)
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.noursindev.mafiauhc.MainKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    from("src/main/kotlin")
}