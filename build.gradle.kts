plugins {
	java
	`java-library`
	application
	id("org.springframework.boot") version "3.0.1" apply false
	id("io.spring.dependency-management") version "1.1.0"
	id("io.freefair.lombok") version "8.0.1"
	id("com.diffplug.spotless") version "6.18.0"
}

group = "rc.holding"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_19

application {
	mainModule.set("rc.holding.houseplants")
	mainClass.set("rc.holding.houseplants.HouseplantsApplication")
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

allprojects {
	apply(plugin = "java")
	apply(plugin = "java-library")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "io.freefair.lombok")
	apply(plugin = "com.diffplug.spotless")

	repositories {
		mavenCentral()
	}

	spotless {
		java {
			googleJavaFormat()
		}
		format("misc") {
			target("**/*.gradle', 'application/src/**/*.yml")
			trimTrailingWhitespace()
			indentWithSpaces(2)
			endWithNewline()
		}
	}
}
apply(plugin= "org.springframework.boot")



dependencies {
		implementation(project(":domain"))
		implementation(project(":repository-api"))
		implementation(project(":repository-mybatis"))
		implementation(project(":service-api"))
		implementation(project(":service-spring"))
		implementation("org.springframework.boot:spring-boot-starter:3.0.1")
		implementation("org.springframework.boot:spring-boot-starter-web:3.0.1")
		implementation("org.springframework.boot:spring-boot-configuration-processor:3.0.1")
		testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.1")
		implementation("org.springframework.boot:spring-boot-starter-hateoas:3.0.1")
		implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.0.1")
		implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.1")
		implementation("org.springframework.boot:spring-boot-starter-validation:3.0.1")
		implementation("org.springframework.hateoas:spring-hateoas:2.0.1")
		implementation("com.diffplug.spotless:spotless-plugin-gradle:6.18.0")
		implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
		compileOnly("org.projectlombok:lombok")
		// developmentOnly("org.springframework.boot:spring-boot-devtools:3.0.1")
		runtimeOnly("org.postgresql:postgresql:42.5.1")
		annotationProcessor("org.projectlombok:lombok")
	}

tasks.withType<Test> {
	useJUnitPlatform()
}


