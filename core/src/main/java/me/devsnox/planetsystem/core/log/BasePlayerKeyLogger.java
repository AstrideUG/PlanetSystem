package me.devsnox.planetsystem.core.log;

import me.devsnox.planetsystem.api.log.PlayerKeyLogger;

public class BasePlayerKeyLogger implements PlayerKeyLogger {
    @Override
    public Object getValue(Object key) {
        return null;
    }

    @Override
    public void log(Level level, Object... message) {

    }
}
