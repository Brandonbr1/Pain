package jerios.painmod.world.features;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenHoles extends WorldGenerator {

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        for (int i = 0; i < 6; i++)
        {
            int py = y - i;
            for (int j = 0; j < 4; j++)
            {
                for (int k = 0; k < 4; k++)
                {
                    if (random.nextBoolean() && world.blockExists(x + j, py,z + k)) {
                        world.setBlock(x + j, py,z + k, Blocks.air, 0, 2);
                    }
                    if (random.nextBoolean() && world.blockExists(x - j, py, z - k)) {
                        world.setBlock(x - j, py, z - k, Blocks.air, 0, 2);
                    }
                    if (random.nextBoolean() && world.blockExists(x - j, py, z + k)) {
                        world.setBlock(x - j, py, z + k, Blocks.air, 0, 2);
                    }
                    if (random.nextBoolean() && world.blockExists(x + j, py, z - k)) {
                        world.setBlock(x + j, py, z - k, Blocks.air, 0, 2);
                    }
                }

            }


        }



        return true;
    }
}
