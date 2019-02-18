/*
 * © Copyright - Lars Artmann aka. LartyHD 2018.
 */

rootProject.name = "PlanetSystem"
rootProject.buildFileName = "build.gradle"

includeProject("api", "core")

fun includeProject(vararg list: String) = list.forEach {
    include(":$it")
    findProject(":$it")?.name = "${rootProject.name}-${it.capitalize()}"
}