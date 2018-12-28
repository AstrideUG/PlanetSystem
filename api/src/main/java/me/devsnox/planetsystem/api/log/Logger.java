package me.devsnox.planetsystem.api.log;

public interface Logger {

    default void info(Object... object) {
        log(Level.INFO, object);
    }

    default void warn(Object... object) {
        log(Level.WARNING, object);
    }

    default void successes(Object... object) {
        log(Level.SUCCESSFULLY, object);
    }

    void log(Level level, Object... message);

    enum Level {
        INFO,
        WARNING,
        SUCCESSFULLY
    }
}
