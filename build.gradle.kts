plugins {
    java
}

tasks.register("createAppDirectory") {
    doLast {
        mkdir("createAppDirectory")
    }
}

tasks.register<Copy>("copyJar") {
    from("elevator-backend/build/libs/elevator-backend-1.0.jar")
    into("app/")
}

tasks.build {
    finalizedBy("createAppDirectory", "copyJar")
}