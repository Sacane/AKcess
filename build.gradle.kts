plugins {
	java
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
}

group = "fr.pentagone.akcess"
version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_19

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("jakarta.validation:jakarta.validation-api:3.0.2")
	implementation("org.hibernate:hibernate-jpamodelgen:6.4.0.Final")
	implementation("org.springframework.boot:spring-boot-starter-security")
	annotationProcessor("org.hibernate:hibernate-jpamodelgen:6.4.0.Final")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootBuildImage {
	builder.set("paketobuildpacks/builder-jammy-base:latest")
}

