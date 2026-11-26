plugins {
    java
    `java-library`
    `maven-publish`
    id("io.freefair.lombok")
}

// These are necessary for standalone build, but will be overridden when in mod project
group = "snw.mods.rfm"
version = "0.1.0"

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    "testImplementation"("org.junit.jupiter:junit-jupiter:6.1.3")
    "testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
}

extensions.configure<JavaPluginExtension> {
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(21)
}

// Configure Maven publishing.
extensions.configure<PublishingExtension> {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = extensions.getByType<BasePluginExtension>().archivesName.get()
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
        // Notice: This block does NOT have the same function as the block in the top level.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.
        mavenLocal()
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.jetbrains:annotations:26.1.0")
}

// The API is published documentation, so keep `:javadoc` clean: every doclint check stays enabled
// except `missing`, which cannot be satisfied for the members Lombok generates (e.g. the no-argument
// constructor and `canEqual` of @EqualsAndHashCode) because they have no source location to document.
tasks.withType<Javadoc>().configureEach {
    (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:all,-missing", "-quiet")
}
