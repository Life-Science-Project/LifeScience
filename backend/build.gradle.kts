import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.useIR = true

plugins {
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
    kotlin("jvm") version "1.4.31"
    kotlin("plugin.spring") version "1.4.31"
    kotlin("plugin.allopen") version "1.4.31"
    kotlin("plugin.noarg") version "1.4.31"
    kotlin("plugin.serialization") version "1.4.31"
}

group = "com.jetbrains"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":frontend"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.jsonwebtoken:jjwt:0.9.0")
    implementation("io.jsonwebtoken:jjwt-api:0.10.6")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")

    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2:1.4.200")
    implementation("org.springdoc:springdoc-openapi-webmvc-core:1.5.6")
    implementation("org.springdoc:springdoc-openapi-ui:1.5.6")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation(group = "org.springframework.data", name = "spring-data-elasticsearch", version = "4.1.7")

    implementation(group = "org.postgresql", name = "postgresql")

    implementation("org.hibernate.search:hibernate-search-mapper-orm:6.0.2.Final")
    implementation("org.hibernate.search:hibernate-search-backend-elasticsearch:6.0.2.Final")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
    dependsOn(":frontend:bundle")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

ktlint {
    disabledRules.add("import-ordering")
    disabledRules.add("no-wildcard-imports")
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    reporters {
        reporter(ReporterType.CHECKSTYLE)
    }
    filter {
        exclude("**/test/**")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register<Copy>("copyFrontend") {
    from("$rootDir/frontend/build")
    into("$rootDir/backend/build/resources/main/static/.")
}

tasks.named("bootJar") {
    dependsOn(":backend:copyFrontend")
}
