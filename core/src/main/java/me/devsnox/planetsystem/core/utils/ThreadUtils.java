package me.devsnox.planetsystem.core.utils;

import org.bukkit.scheduler.BukkitRunnable;

public class ThreadUtils {

    public static void sync(final SyncContent syncContent) {
        new BukkitRunnable() {

            @Override
            public void run() {
                syncContent.run();
            }
        }.run();
    }
}
