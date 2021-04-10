tasks.register("buildBack") {
    subprojects {
        extra.apply {
            set("no_front", true)
        }
    }
    dependsOn(":backend:build")
}
