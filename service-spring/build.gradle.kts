dependencies {
    implementation(project(":service-api"))
    implementation(project(":domain"))
    implementation(project(":repository-api"))
    implementation("org.springframework.boot:spring-boot-starter:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-web:3.0.1")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}