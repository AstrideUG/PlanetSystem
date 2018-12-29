package me.devsnox.planetsystem.core.log;

import me.devsnox.planetsystem.api.log.PlayerKeyLogger;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BasePlayerKeyLogger extends BasePlayerLogger implements PlayerKeyLogger {

    private final Map<Object, Object> mapper;

    public BasePlayerKeyLogger(Player player, Map<Object, Object> mapper) {
        super(player);
        this.mapper = mapper;
    }

    @Override
    public Object getValue(Object key) {
        Object o = mapper.get(key);
        return o == null ? key : o;
    }

    @Override
    public void log(Level level, Object... message) {
        List<Object> objects = new ArrayList<>();

        for (Object o : message) objects.add(getValue(o));

        super.log(level, objects.toArray());
    }

}
