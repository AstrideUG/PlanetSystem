package me.devsnox.planetsystem.core.world;

import org.bukkit.*;

import java.io.File;

public final class GridSystem {

    private final World world;

    private final int maxSize;

    private int stage;

    public GridSystem(String name, int maxSize) {
        File file = new File(Bukkit.getWorldContainer(), name);

        if (file.exists() && file.isDirectory()) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }

        this.world = generateVoidWorld(name);
        this.maxSize = maxSize;
        this.stage = 1;
    }

    public Location getEmptyLocation() {
        int x = this.stage * this.maxSize;
        return new Location(world, x, 126, 0);
    }

    private World generateVoidWorld(String name) {
        return WorldCreator.name(name).type(WorldType.NORMAL)
                .generateStructures(false)
                .environment(World.Environment.NORMAL)
                .generator(new VoidWorldGenerator())
                .createWorld();
    }
}
