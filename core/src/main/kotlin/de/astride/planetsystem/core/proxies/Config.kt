package de.astride.planetsystem.core.proxies

import de.astride.planetsystem.core.service.ConfigService

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 18.03.2019 02:50.
 * Current Version: 1.0 (18.03.2019 - 18.03.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 18.03.2019 02:50.
 * Current Version: 1.0 (18.03.2019 - 18.03.2019)
 */
val configs get() = ConfigService.instance

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 18.03.2019 02:50.
 * Current Version: 1.0 (18.03.2019 - 18.03.2019)
 */
val config get() = configs.config

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 18.03.2019 02:53.
 * Current Version: 1.0 (18.03.2019 - 18.03.2019)
 */
val planets get() = config.planets