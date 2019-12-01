import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.1.RELEASE"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	kotlin("jvm") version "1.3.61"
	kotlin("plugin.spring") version "1.3.61"
	kotlin("plugin.jpa") version "1.3.61"
	`java-library`
	`maven-publish`
}

group = "cz.petrbalat"
version = "0.1.0"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	testImplementation("org.springframework.boot:spring-boot-starter-web")

	testImplementation("org.flywaydb:flyway-core")

	testImplementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	testRuntime("org.hsqldb:hsqldb")
	testImplementation("org.jetbrains.kotlin:kotlin-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable", "-progressive", "-Xuse-experimental=kotlin.Experimental")
		jvmTarget = "1.8"
	}
}

repositories {
	maven("https://petrbalat.bintray.com/kdsl-jpa-spec") {
		credentials {
			username = "petrbalat1"
			password = System.getenv("JCENTER_API_KEY")
		}
	}
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			groupId = "cz.petrbalat"
			artifactId = "kdsl-jpa-spec"
			version = "0.1.0"

			from(components["java"])
		}
	}
}
