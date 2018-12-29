package me.devsnox.planetsystem.core.utils;

import org.bukkit.scheduler.BukkitRunnable;

public class ThreadUtils {

    public static void sync(final Runnable runnable) {
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.run();
    }
}
