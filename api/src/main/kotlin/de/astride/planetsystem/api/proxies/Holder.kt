package de.astride.planetsystem.api.proxies

import de.astride.planetsystem.api.holder.Holder

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 18.03.2019 02:16.
 * Current Version: 1.0 (18.03.2019 - 18.03.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 18.03.2019 02:14.
 * Current Version: 1.0 (18.03.2019 - 18.03.2019)
 */
val holder get() = Holder.instance

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 18.03.2019 02:12.
 * Current Version: 1.0 (18.03.2019 - 18.03.2019)
 */
val databaseHandler get() = holder.databaseHandler

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 18.03.2019 02:13.
 * Current Version: 1.0 (18.03.2019 - 18.03.2019)
 */
val gridHandler get() = holder.gridHandler

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 18.03.2019 02:13.
 * Current Version: 1.0 (18.03.2019 - 18.03.2019)
 */
val players get() = holder.players

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 18.03.2019 02:13.
 * Current Version: 1.0 (18.03.2019 - 18.03.2019)
 */
val loadedPlanets get() = holder.loadedPlanets

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 18.03.2019 02:19.
 * Current Version: 1.0 (18.03.2019 - 18.03.2019)
 */
val gameWorld get() = gridHandler.world