package jerios.painmod.world.features;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenReplaceGrassWithDirt extends WorldGenerator {
    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        return false;
    }
}
