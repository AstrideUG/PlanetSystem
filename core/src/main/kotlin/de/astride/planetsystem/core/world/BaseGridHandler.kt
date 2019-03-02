package de.astride.planetsystem.core.world

import de.astride.planetsystem.api.handler.GridHandler
import org.bukkit.*
import java.io.File

//TODO refactor
class BaseGridHandler(name: String, override val maxSize: Int) : GridHandler {

    override val world: World
    private val used = /*TreeSet<Int>()*/ mutableSetOf<Int>()

    init {
        val file = File(Bukkit.getWorldContainer(), name)
        if (file.exists() && file.isDirectory) {
            Bukkit.unloadWorld(name, false)
            file.delete()
        }

        world = generateVoidWorld(name)
    }

    override fun getId(location: Location): Int = location.clone().blockX / (maxSize / 2)

    override fun removeEntry(i: Int) {
        used.remove(i)
    }

    override fun findEmptyLocation(): Location {
        val x = findFreeAndAdd() * maxSize
        return Location(world, x + 0.5, 126.0, 0.5)
    }

    private fun findFreeAndAdd(): Int = findFree().apply { used.add(this) }

    private fun findFree(): Int {
        val integer = used.lastOrNull() ?: 0
        for (i in 0 until integer) if (i !in used) return i
        return integer.inc()
    }

}

private fun generateVoidWorld(name: String) = WorldCreator.name(name).type(WorldType.NORMAL)
    .generateStructures(false)
    .environment(World.Environment.NORMAL)
    .generator(VoidWorldGenerator())
    .createWorld()