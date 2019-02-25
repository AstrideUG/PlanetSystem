/*
 * © Copyright - Lars Artmann aka. LartyHD 2018.
 */

package de.astride.planetsystem.core.planets

import com.google.common.base.MoreObjects
import com.sk89q.worldedit.function.pattern.BlockPattern
import de.astride.planetsystem.api.holder.Holder
import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.core.service.ConfigService
import de.astride.planetsystem.core.utils.EconomyUtils
import net.darkdevelopers.darkbedrock.darkness.general.functions.toNonNull
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.messages
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendConfigurableMessage
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.CommandSender

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 31.08.2018 03:07.
 *
 * imported from Planets at the 24.02.2019
 *
 * Last edit 08.01.2019
 */
abstract class Planet(
    val owner: Owner,
    center: Location,
    size: Byte = config.defaultSize,
    maxSize: Byte = config.maxSize,
    val blockDamage: Int = config.defaultBlockPattern.damage,
    val blockID: Int = config.defaultBlockPattern.id
) {

    companion object {
        val planets = mutableSetOf<Planet>()
        internal val config get() = ConfigService.instance.config.planets

        fun getPlanetWithMessageOnFail(owner: Owner, sender: CommandSender, byTarget: Boolean = false): Planet? {
            val prefix = if (byTarget) "Target" else "Player"
            var planet: Planet? = null
            try {
                planet = getPlanet(owner)
                if (planet == null) sender.sendMessage(
                    messages["${prefix}AreNotAOwnerOfAIsland"]
                        ?.replace("<UUID>", owner.toString(), true)
                        ?.replace("<Target>", Bukkit.getOfflinePlayer(owner.uuid).name, true)
                        ?.replace("<Sender>", sender.name, true)
                )
            } catch (ex: IllegalStateException) {
                sender.sendMessage(
                    messages["${prefix}HasAnIslandButNotAPlanet"]
                        ?.replace("<UUID>", owner.toString(), true)
                        ?.replace("<Target>", Bukkit.getOfflinePlayer(owner.uuid).name, true)
                        ?.replace("<Sender>", sender.name, true)
                )
                System.err.println("$owner has an island but not a Planet")
            }
            return planet
        }

        fun getPlanet(owner: Owner, ignoreHasIslandCheck: Boolean = false): Planet? =
            if (!ignoreHasIslandCheck && Holder.instance.playerData.players.map { it.player.uniqueId }.none { Owner(it) == owner })
                throw IllegalStateException("Player has an island but not a Planet")
            else planets.find { it.owner == owner }

        fun getPrice(size: Double) = Math.pow(size, config.pow)

    }

    private val addToCenter = config.addToCenter
    val rawCenter: Location = center.clone()
    val center: Location = center.clone().add(addToCenter.x, addToCenter.y, addToCenter.z)
    var size = SizeChecker.checkSize(size, maxSize)
        protected set(value) {
            field = SizeChecker.checkSize(value, maxSize)
        }
    val maxSize = SizeChecker.checkSize(maxSize, maxSize)

    fun expand() {
        val player = Bukkit.getPlayer(owner.uuid).toNonNull("player")
        when {
            size >= maxSize -> player.sendConfigurableMessage("ExpandToBiggerSizeThanMaxSize")//TODO: CONVERT
            Holder.instance.playerData.players.any { Owner(it.player.uniqueId) == owner } ->
                EconomyUtils.removeWhenHasEnough(player, getPrice()) {
                    updateSize()
                    Holder.instance.planetData.getLoadedPlanet(owner)?.size = size
                }
            else -> player.sendConfigurableMessage("PlayerAreNotTheOwnerOfAIsland") //TODO: CONVERT
        }

    }

    private fun updateSize() {
        size++
        delete(size - 1.0)
        fix()
    }

    fun fix() = use(size.toDouble(), @Suppress("DEPRECATION") BlockPattern(blockID, blockDamage))

    fun delete(size: Double = this.size.toDouble()): Unit = use(size, @Suppress("DEPRECATION") BlockPattern(0))

    fun getPrice() = getPrice(size.toDouble())

    fun <P : Planet> edit(planet: P): P {
        planets.remove(this)
        planets.add(planet)
        return planet
    }

    fun edit(
        owner: Owner = this.owner,
        center: Location = this.rawCenter,
        size: Byte = this.size,
        maxSize: Byte = this.maxSize,
        blockDamage: Int = this.blockDamage,
        blockID: Int = this.blockID
    ): Planet = edit(copy(owner, center, size, maxSize, blockDamage, blockID))

    abstract fun copy(
        owner: Owner = this.owner,
        center: Location = this.rawCenter,
        size: Byte = this.size,
        maxSize: Byte = this.maxSize,
        blockDamage: Int = this.blockDamage,
        blockID: Int = this.blockID
    ): Planet

    override fun toString() = MoreObjects
        .toStringHelper(this)
        .add("owner", owner)
        .add("center", center)
        .add("size", size)
        .add("maxSize", maxSize)
        .add("blockDamage", blockDamage)
        .add("blockID", blockID)
        .toString()

    protected abstract fun use(size: Double, @Suppress("DEPRECATION") blockPattern: BlockPattern)

    object SizeChecker {
        private const val MIN_SIZE: Byte = 2
        private const val MAX_SIZE: Byte = 126

        internal fun checkSize(size: Byte, maxSize: Byte): Byte = when {
            checkMax(size, maxSize) != size -> checkMax(size, maxSize)
            checkMin(size) != size -> checkMin(size)
            else -> size
        }

        private fun checkMin(size: Byte) =
            checkSize(size, MIN_SIZE, "lower than MIN_SIZE($MIN_SIZE)") { size < MIN_SIZE }

        private fun checkMax(size: Byte, maxSize: Byte) =
            checkSize(size, maxSize, "higher than maxSize($maxSize) (MAX_SIZE($MAX_SIZE))") { size > maxSize }

        private fun checkSize(size: Byte, check: Byte, message: String, algorithm: () -> Boolean) = if (algorithm())
            if (!config.doNotThrowIllegalStateExceptionBySizeCheck) throw IllegalStateException("Size $message") else check
        else size
    }

}