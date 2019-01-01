package me.devsnox.planetsystem.core.world;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class VoidWorldGenerator extends ChunkGenerator {

    @Override
    public List<BlockPopulator> getDefaultPopulators(final World world) {
        return Collections.emptyList();
    }

    @Override
    public boolean canSpawn(final World world, final int x, final int z) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public byte[] generate(final World world, final Random rand, final int chunkx, final int chunkz) {
        return new byte[32768];
    }
}
