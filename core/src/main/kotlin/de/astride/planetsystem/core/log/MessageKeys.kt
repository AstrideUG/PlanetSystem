package de.astride.planetsystem.core.log

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 15.02.2019 07:42.
 * Current Version: 1.0 (15.02.2019 - 15.02.2019)
 */
enum class MessageKeys(value: String? = null) {

    COMMANDS_MAIN_HELP,

    COMMANDS_HOME_TELEPORTATION_SUCCESS,

    COMMANDS_EXPAND_FAILED_NOT_ON_OWN_PLANET,

    COMMANDS_INFO_SUCCESS,
    COMMANDS_INFO_FAILED_NOT_OWN_PLANET,
    COMMANDS_RESTART_FAILED_NOT_OWN_PLANET,

    COMMANDS_INVITE_SUCCESSES_PLAYER,
    COMMANDS_INVITE_SUCCESSES_TARGET,
    COMMANDS_INVITE_FAILED_IS_NO_UUID_AND_TARGET_IS_NOT_ONLINE,
    COMMANDS_INVITE_FAILED_IS_ALREADY_A_PLANET_MEMBER,
    COMMANDS_INVITE_FAILED_NO_PERMS_FOR_MORE_MEMBER,
    COMMANDS_INVITE_FAILED_ARGS_SIZE_IS_NOT_1,

    COMMANDS_KICK_SUCCESSES_TARGET,
    COMMANDS_KICK_SUCCESSES_PLAYER,
    COMMANDS_KICK_FAILED_IS_NO_UUID_AND_TARGET_IS_NOT_ONLINE,
    COMMANDS_KICK_FAILED_IS_NOT_A_PLANET_MEMBER,
    COMMANDS_KICK_FAILED_ARGS_SIZE_IS_NOT_1,

    COMMANDS_SET_HOME_SUCCESSES,
    COMMANDS_SET_HOME_NOT_IN_PLANET_WORLD,

    COMMANDS_VISIT_FAILED_NO_ARGS,

    COMMANDS_TOP_FAILED_ARGS_SIZE_BIGGER_1,
    COMMANDS_TOP_SUCCESSES_SIZE_INFO,
    COMMANDS_TOP_SUCCESSES_ENTRY_INFO,

    LISTENER_PLANET_BUILD_DENY,


    NOT_IMPLEMENTED,
    ;

    private val value: String? = value
        get() = (field ?: name)/*.toLowerCase()*/.replace('_', '.')

    override fun toString(): String = value!!

}