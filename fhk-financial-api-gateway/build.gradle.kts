plugins {
    java
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
}

val springCloudVersion = "2025.0.0"

group = "com.fhk"
version = "v0.0.1"
description = "fhk-financial-api-gateway"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {

    implementation(files(
        "libs/fhk-common-1.0.0-plain.jar",
        "libs/fhk-core-1.0.0-plain.jar",
        "libs/fhk-security-core-1.0.0-plain.jar"
    ))

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    implementation ("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation ("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation ("io.jsonwebtoken:jjwt-jackson:0.11.5")
    runtimeOnly ("io.jsonwebtoken:jjwt-api:0.11.5")

    compileOnly ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}
