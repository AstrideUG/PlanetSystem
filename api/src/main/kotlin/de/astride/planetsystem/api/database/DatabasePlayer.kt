/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.api.database

import de.astride.planetsystem.api.inline.Owner
import de.astride.planetsystem.api.inline.UniqueID

interface DatabasePlayer {

    val uniqueID: Owner
    val planetUniqueId: UniqueID

}
