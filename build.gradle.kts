plugins {
    id("java")
    war
    id("io.freefair.aspectj.post-compile-weaving") version "6.4.1"
}

group = "ru.clevertec.task"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
project.extra["aspectjVersion"] = "1.8.4"
buildscript{
    repositories{
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies{
        classpath("gradle.plugin.aspectj:gradle-aspectj:0.1.6")
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
    compileOnly("javax:javaee-web-api:8.0.1")
    implementation("javax.servlet:jstl:1.2")
    implementation("com.google.code.gson:gson:2.3.1")
    compileOnly("org.aspectj:aspectjweaver:1.8.4")
    implementation("org.aspectj:aspectjrt:1.9.8")
    compileOnly("org.aspectj:aspectjtools:1.8.4")

}

tasks.test {
    useJUnitPlatform()
}
