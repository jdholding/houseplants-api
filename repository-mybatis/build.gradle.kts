dependencies {
    implementation(project(":repository-api"))
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter:3.0.1")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.1")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}