plugins {
  java
  id("org.springframework.boot") version "3.2.3"
  id("io.spring.dependency-management") version "1.1.4"
}

group = "io.chagchagchag.example"
version = "0.0.1-SNAPSHOT"

java {
  sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
  mavenCentral()
}

extra["springCloudVersion"] = "2023.0.0"

dependencies {
  implementation("org.springframework.cloud:spring-cloud-stream")
  testImplementation("org.springframework.cloud:spring-cloud-stream-test-binder")

  // web
  implementation("org.springframework.boot:spring-boot-starter-mustache")
  implementation("org.springframework.boot:spring-boot-starter-webflux")

  // springdoc
  implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.1.0")

  // reactor-core
  implementation("io.projectreactor:reactor-core:3.6.2")

  // rxjava
  implementation("io.reactivex.rxjava3:rxjava:3.1.8")

  // mutiny
  implementation("io.smallrye.reactive:mutiny:2.5.7")

  // reactor tool
  implementation("io.projectreactor:reactor-tools")


  // lombok
  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")
  testAnnotationProcessor("org.projectlombok:lombok")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("io.projectreactor:reactor-test")
}

dependencyManagement {
  imports {
    mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}
