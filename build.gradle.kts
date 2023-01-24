import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
	java
	`java-library`
	application
	id("org.springframework.boot") version "3.0.1" apply false
	id("io.spring.dependency-management") version "1.1.0"
	id("io.freefair.lombok") version "6.6.1"
}

group = "rc.holding"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

application {
	mainModule.set("rc.holding.houseplants")
	mainClass.set("rc.holding.houseplants.HouseplantsApplication")
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

allprojects{
	apply(plugin = "java")
	apply(plugin = "java-library")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "io.freefair.lombok")

	repositories {
		mavenCentral()
	}

}

apply(plugin= "org.springframework.boot")

dependencies {
		implementation(project(":domain"))
		implementation(project(":repository-api"))
		implementation(project(":repository-mybatis"))
		implementation("org.springframework.boot:spring-boot-starter:3.0.1")
		implementation("org.springframework.boot:spring-boot-starter-web:3.0.1")
		implementation("org.springframework.boot:spring-boot-configuration-processor:3.0.1")
		testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.1")
		implementation("org.springframework.boot:spring-boot-starter-hateoas:3.0.1")
		implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.0.1")
		implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.1")
		implementation("org.springframework.hateoas:spring-hateoas:2.0.1")
		// implementation("org.springframework.boot:spring-boot-starter-jdbc:3.0.1")
		compileOnly("org.projectlombok:lombok")
		// developmentOnly("org.springframework.boot:spring-boot-devtools")
		runtimeOnly("org.postgresql:postgresql:42.5.1")
		annotationProcessor("org.projectlombok:lombok")
	}





tasks.withType<Test> {
	useJUnitPlatform()
}

//tasks.named<BootJar>("bootJar") {
//   archiveFileName.set("houseplants.jar")
//   // mainClassName = "rc.holding.houseplants.HouseplantsApplication"
//}

//tasks.named<BootRun>("bootRun") {
//   mainClass.set("rc.holding.houseplants.HouseplantsApplication")
//   // args("--spring.profiles.active=demo")
//}

