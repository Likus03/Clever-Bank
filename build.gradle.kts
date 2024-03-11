plugins {
    id("java")
    war
}

group = "ru.clevertec.task"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
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

}

tasks.test {
    useJUnitPlatform()
}
