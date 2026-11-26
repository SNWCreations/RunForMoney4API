plugins {
    java
    `java-library`
    `maven-publish`
    // Keep the version in sync with the one declared in the root project of the mod project.
    // It must be declared here as well because the root project's `plugins` block is not visible
    //  when this project is built as a standalone project (e.g. by JitPack).
    id("io.freefair.lombok") version "9.8.2"
}

// These are necessary for standalone build, but will be overridden when in mod project.
// JitPack passes the requested coordinates through -Pgroup / -Pversion, so read them from there
//  to make sure the artifacts are published under the coordinates JitPack expects.
if (path == ":") {
    group = providers.gradleProperty("group").getOrElse("snw.mods.rfm")
    version = providers.gradleProperty("version").getOrElse("0.1.0")
}

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
