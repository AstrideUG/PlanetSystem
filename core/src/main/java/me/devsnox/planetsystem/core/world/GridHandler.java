package me.devsnox.planetsystem.core.world;

import org.bukkit.*;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.TreeSet;

public final class GridHandler implements me.devsnox.planetsystem.api.handler.GridHandler {

    private final World world;
    private final int maxSize;
    private final TreeSet<Integer> used;

    public GridHandler(final String name, final int maxSize) {
        final File file = new File(Bukkit.getWorldContainer(), name);

        if (file.exists() && file.isDirectory()) {
            Bukkit.unloadWorld(name, false);
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }

        this.world = this.generateVoidWorld(name);
        this.maxSize = maxSize;
        this.used = new TreeSet<>();
    }

    @Override
    public Location getEmptyLocation() {
//        System.out.println("St getEmptyLocation");
//        System.out.println(this.findFree());

        final int x = (this.findFreeAndAdd()) * (this.maxSize / 2);
//        System.out.println(this.used);
//        System.out.println("Ensd getEmptyLocation");
        return new Location(this.world, x + 0.5, 126, 0.5);
    }

    @Override
    public int getId(Location location) {
        return location.clone().getBlockX() / (this.maxSize / 2);
    }

    @Override
    public void removeEntry(int i) {
        this.used.remove(i);
    }

    private int findFreeAndAdd() {
        int free = this.findFree();
        System.out.println(this.used);
        System.out.println(this.used.size());
        boolean add = this.used.add(free);
        System.out.println("findFreeAndAdd " + add);
        System.out.println(this.used);
        System.out.println(this.used.size());
        return free;
    }

    private int findFree() {
        try {
            Integer integer = this.used.last();
            for (int i = 0; i < integer; i++) if (this.used.contains(i)) return i;
            return integer + 1;
        } catch (NoSuchElementException ex) {
            return 0;
        }
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
        return this.maxSize;
    }

    public World getWorld() {
        return this.world;
    }
}
