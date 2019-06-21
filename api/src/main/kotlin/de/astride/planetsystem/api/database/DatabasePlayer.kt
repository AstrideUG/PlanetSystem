/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.database

import de.astride.planetsystem.api.proxies.Owner
import de.astride.planetsystem.api.proxies.UniqueID

interface DatabasePlayer {

    val owner: Owner
    val planetUniqueId: UniqueID

}
