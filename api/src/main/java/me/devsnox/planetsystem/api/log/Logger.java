package me.devsnox.planetsystem.api.log;

public interface Logger {

    default void info(Object... object) {
        this.log(Level.INFO, object);
    }

    default void warn(Object... object) {
        this.log(Level.WARNING, object);
    }

    default void success(Object... object) {
        this.log(Level.SUCCESSFULLY, object);
    }

    void log(Level level, Object... message);

    enum Level {
        INFO,
        WARNING,
        SUCCESSFULLY
    }
}
