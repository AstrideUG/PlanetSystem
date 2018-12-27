package me.devsnox.planetsystem.api.log;

public interface Logger {

    default void log(Object... object) {
        log(Level.INFO, object);
    }

    void log(Level level, Object... message);

    enum Level {
        INFO,
        WARNING,
        SUCCESSFULLY;
    }
}
