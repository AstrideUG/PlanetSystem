/*
 * © Copyright - Lars Artmann aka. LartyHD 2018.
 */

rootProject.name = "PlanetSystem"
rootProject.buildFileName = "build.gradle"

private val api = "api"
private val common = "common"
private val spigot = "spigot"
//private val sponge = "sponge"
private val bungee = "bungee"
private val velocity = "velocity"

includeProject(api, common)

fun includeProject(vararg list: String) = list.forEach {
	include(":$it")
	findProject(":$it")?.name = "${name.split(":").last()}-${it.capitalize()}"
}