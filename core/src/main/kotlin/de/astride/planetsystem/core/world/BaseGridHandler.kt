/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.world

import de.astride.planetsystem.api.handler.GridHandler
import de.astride.planetsystem.core.functions.deleteWorld
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import java.util.*

//TODO refactor
class BaseGridHandler(name: String, override val maxSize: Int) : GridHandler {

    override val world: World
    private val used = TreeSet<Int>()

    init {

        deleteWorld(name)
        world = Bukkit.getWorld(name) ?: generateVoidWorld(name)

    }

    override fun getId(location: Location): Int = location.clone().blockX / maxSize

    override fun removeEntry(i: Int) {
        used.remove(i)
    }

    override fun findEmptyLocation(): Location {
        val x = findFreeAndAdd() * maxSize
        return Location(world, x.toDouble(), 126.0, 0.0)
    }

    private fun findFreeAndAdd(): Int = findFree().apply { used.add(this) }

    private fun findFree(): Int {
        val integer = used.lastOrNull() ?: return 0
        for (i in 1 until integer) if (i !in used) return i
        return integer.inc()
    }

}
