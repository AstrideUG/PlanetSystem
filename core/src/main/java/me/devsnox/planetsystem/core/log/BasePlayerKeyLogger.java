package me.devsnox.planetsystem.core.log;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.devsnox.planetsystem.api.log.PlayerKeyLogger;
import me.devsnox.planetsystem.core.PlanetSystem;
import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData;
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonConfig;
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.SpigotGsonMessages;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public final class BasePlayerKeyLogger extends BasePlayerLogger implements PlayerKeyLogger {

    private final Map<Object, Object> mapper;
    private static Map<String, String> messages;


    public BasePlayerKeyLogger(final Player player, final Map<Object, Object> mapper) {
        super(player);
        this.mapper = mapper;
        if (messages == null)
            messages = new SpigotGsonMessages(new GsonConfig(new ConfigData(JavaPlugin.getPlugin(PlanetSystem.class).getDataFolder(), "messages.json", true), new JsonObject(), true).load()).getAvailableMessages();
        mapper.putAll(messages);
    }

    @Override
    public Object getValue(final Object key) {
        final Object o = this.mapper.get(key);
        return o == null ? key : o;
    }

    @Override
    public void log(final Level level, final Object... message) {
        final List<Object> objects = new ArrayList<>();

        for (final Object o : message) objects.add(this.getValue(o));

        super.log(level, objects.toArray());
    }

}
