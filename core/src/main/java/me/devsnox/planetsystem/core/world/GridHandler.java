package me.devsnox.planetsystem.core.world;

import org.bukkit.*;

import java.io.File;

public final class GridHandler implements me.devsnox.planetsystem.api.handler.GridHandler {

    private final World world;
    private final int maxSize;
    private int stage;

    public GridHandler(final String name, final int maxSize) {
        final File file = new File(Bukkit.getWorldContainer(), name);

        if (file.exists() && file.isDirectory()) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }

        this.world = generateVoidWorld(name);
        this.maxSize = maxSize;
        this.stage = 0;
    }

    @Override
    public Location getEmptyLocation() {
        final int x = this.stage++ * (this.maxSize / 2);
        return new Location(world, x + 0.5, 126, 0.5);
    }

    private World generateVoidWorld(final String name) {
        return WorldCreator.name(name).type(WorldType.NORMAL)
                .generateStructures(false)
                .environment(World.Environment.NORMAL)
                .generator(new VoidWorldGenerator())
                .createWorld();
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }

    public World getWorld() {
        return world;
    }
}
