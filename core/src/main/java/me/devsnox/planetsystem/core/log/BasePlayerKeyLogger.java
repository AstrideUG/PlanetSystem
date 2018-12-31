package me.devsnox.planetsystem.core.log;

import lombok.Data;
import me.devsnox.planetsystem.api.log.PlayerKeyLogger;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class BasePlayerKeyLogger extends BasePlayerLogger implements PlayerKeyLogger {

    private final Map<Object, Object> mapper;

    public BasePlayerKeyLogger(final Player player, final Map<Object, Object> mapper) {
        super(player);
        this.mapper = mapper;
    }

    @Override
    public Object getValue(final Object key) {
        final Object o = mapper.get(key);
        return o == null ? key : o;
    }

    @Override
    public void log(final Level level, final Object... message) {
        final List<Object> objects = new ArrayList<>();

        for (final Object o : message) objects.add(getValue(o));

        super.log(level, objects.toArray());
    }

}
