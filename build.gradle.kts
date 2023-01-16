plugins {
	java
	`java-library`
	id("org.springframework.boot") version "3.0.1" apply false
	id("io.spring.dependency-management") version "1.1.0" apply false
	id("io.freefair.lombok") version "6.6.1" apply false
}

allprojects{
	apply(plugin = "java")
	apply(plugin = "java-library")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "io.freefair.lombok")

	group = "rc.holding"
	version = "0.0.1-SNAPSHOT"
	java.sourceCompatibility = JavaVersion.VERSION_17

	configurations {
		compileOnly {
			extendsFrom(configurations.annotationProcessor.get())
		}
	}

	repositories {
		mavenCentral()
	}
}

project(":application"){
	apply(plugin = "org.springframework.boot")

	dependencies {
		implementation(project(":domain"))
		implementation(project(":repository-api"))
		implementation("org.springframework.boot:spring-boot-starter-hateoas")
		implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.1")
		compileOnly("org.projectlombok:lombok")
		// developmentOnly("org.springframework.boot:spring-boot-devtools")
		runtimeOnly("org.postgresql:postgresql")
		annotationProcessor("org.projectlombok:lombok")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
	}
}

project(":domain"){
	dependencies{
		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")
	}
}

project(":repository-api"){
	dependencies{
		implementation(project(":domain"))
		implementation("org.springframework.boot:spring-boot-starter:3.0.1")
		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")
	}
}

project(":repository-mybatis"){
	dependencies {
		implementation(project(":repository-api"))
		implementation(project(":domain"))
		implementation("org.springframework.boot:spring-boot-starter:3.0.1")
		implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.1")
		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
