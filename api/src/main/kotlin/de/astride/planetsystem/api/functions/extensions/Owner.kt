/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.functions.extensions

import de.astride.planetsystem.api.proxies.Owner
import org.bukkit.entity.Player

/*
 * Created on 21.06.2019 15:43.
 * @author Lars Artmann | LartyHD
 */
val Player.owner get() = Owner(uniqueId)
