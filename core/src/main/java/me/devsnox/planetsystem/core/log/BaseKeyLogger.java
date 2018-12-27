package me.devsnox.planetsystem.core.log;

import me.devsnox.planetsystem.api.log.KeyLogger;

public class BaseKeyLogger implements KeyLogger {
    @Override
    public Object getValue(Object key) {
        return null;
    }

    @Override
    public void log(Level level, Object... message) {

    }
}
