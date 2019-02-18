package de.astride.planetsystem.core.world

import org.bukkit.World
import org.bukkit.generator.BlockPopulator
import org.bukkit.generator.ChunkGenerator
import java.util.*

class VoidWorldGenerator : ChunkGenerator() {

	override fun getDefaultPopulators(world: World?): List<BlockPopulator> = emptyList()

	override fun canSpawn(world: World, x: Int, z: Int) = true

	@Suppress("OverridingDeprecatedMember")
	override fun generate(world: World?, rand: Random?, chunkx: Int, chunkz: Int) = ByteArray(32768)

}
