dependencies {
    implementation(project(":repository-api"))
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter:3.0.1")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:3.0.1")
    implementation("com.github.pagehelper:pagehelper-spring-boot-starter:1.4.6")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.1")
    testImplementation("org.testcontainers:postgresql:1.18.0")
    testImplementation("org.postgresql:postgresql:42.5.4")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform {
        excludeTags("db")
    }
}


val  integrationTest = task<Test> ("integrationTest"){
    group = "verification"
        useJUnitPlatform {
            includeTags("db")
        }
}
tasks.check {
    dependsOn(integrationTest)
}