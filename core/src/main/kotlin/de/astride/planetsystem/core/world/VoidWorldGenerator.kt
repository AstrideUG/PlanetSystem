/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.world

import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import org.bukkit.generator.BlockPopulator
import org.bukkit.generator.ChunkGenerator
import java.util.*

class VoidWorldGenerator : ChunkGenerator() {

    override fun getDefaultPopulators(world: World?): List<BlockPopulator> = emptyList()

    override fun canSpawn(world: World, x: Int, z: Int): Boolean = true

    @Suppress("OverridingDeprecatedMember")
    override fun generate(world: World?, rand: Random?, chunkx: Int, chunkz: Int) = ByteArray(32768)

}

fun generateVoidWorld(name: String): World = WorldCreator.name(name).type(WorldType.NORMAL)
    .generateStructures(false)
    .environment(World.Environment.NORMAL)
    .generator(VoidWorldGenerator())
    .createWorld()