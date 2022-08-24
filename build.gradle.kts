import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.72"
    kotlin("kapt") version "1.4.10"
}

group = "com.app.tools"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8


repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation ("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.bouncycastle:bcprov-jdk15on:1.69")
}


tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.app.tools.ApplicationKt"
    }

    from(configurations.compileClasspath.map { config -> config.map { if (it.isDirectory) it else zipTree(it) } })

    exclude("META-INF/*.RSA", "META-INF/*.SF","META-INF/*.DSA")

}