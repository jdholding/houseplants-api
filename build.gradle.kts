import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
	java
	`java-library`
	application
	id("org.springframework.boot") version "3.0.1" apply false
	id("io.spring.dependency-management") version "1.1.0" apply false
	id("io.freefair.lombok") version "6.6.1" apply false
}

application {
	mainModule.set("rc.holding.houseplants")
	mainClass.set("rc.holding.houseplants.HouseplantsApplication")

	apply(plugin = "org.springframework.boot")
	dependencies {
		implementation("org.springframework.boot:spring-boot-starter:3.0.1")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
	}
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

	dependencies {
		implementation(project(":domain"))
		implementation(project(":repository-api"))
		implementation("org.springframework.boot:spring-boot-starter:3.0.1")
		implementation("org.springframework.boot:spring-boot-starter-hateoas:3.0.1")
		implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.0.1")
		implementation("org.springframework.boot:spring-boot-starter-web:3.0.1")
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

//tasks.named<BootJar>("bootJar") {
//    archiveFileName.set("houseplants.jar")
//    // mainClassName = "rc.holding.houseplants.HouseplantsApplication"
//}
//
//tasks.named<BootRun>("bootRun") {
//    mainClass.set("rc.holding.houseplants.HouseplantsApplication")
//    // args("--spring.profiles.active=demo")
//}

