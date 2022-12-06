plugins {
    java
}

tasks.register<Copy>("copyJar") {
    from("elevator-backend/build/libs/elevator-system-control-1.0.jar")
    into("app/")
}

tasks.build {
    finalizedBy( "copyJar")
}