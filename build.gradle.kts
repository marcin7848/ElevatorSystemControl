plugins {
    java
}

repositories {
    mavenCentral()
}

tasks.register<Copy>("copyJar") {
    mkdir("app")
    from("elevator-backend/build/libs/elevator-system-control-1.0.jar")
    into("app/")
}

tasks.build {
    dependsOn(":elevator-backend:build")
    finalizedBy( "copyJar")
}