package jerios.painmod.core.world.features;

import jerios.painmod.core.items.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenHellLavaPain extends WorldGenerator {

    private final Block block;

    public WorldGenHellLavaPain(Block block)
    {
        this.block = block;
    }


    @Override
    public boolean generate(World world, Random random, int x, int y, int z)
    {

            int l = 0;

            if (world.getBlock(x - 1, y, z) == ModItems.painDirt || world.getBlock(x - 1, y, z) == ModItems.painGrass || world.getBlock(x - 1, y, z) == ModItems.painStone)
            {
                ++l;
            }

            if (world.getBlock(x + 1, y, z) == ModItems.painDirt || world.getBlock(x + 1, y, z) == ModItems.painGrass || world.getBlock(x + 1, y, z) == ModItems.painStone)
            {
                ++l;
            }

            if (world.getBlock(x, y, z - 1) == ModItems.painDirt || world.getBlock(x, y, z - 1) == ModItems.painGrass || world.getBlock(x, y, z - 1) == ModItems.painStone)
            {
                ++l;
            }

            if (world.getBlock(x, y, z + 1) == ModItems.painDirt || world.getBlock(x, y, z + 1) == ModItems.painGrass || world.getBlock(x, y, z + 1) == ModItems.painStone)
            {
                ++l;
            }

            if (world.getBlock(x, y - 1, z) == ModItems.painDirt || world.getBlock(x, y - 1, z) == ModItems.painGrass || world.getBlock(x, y - 1, z) == ModItems.painStone)
            {
                ++l;
            }

            int i1 = 0;

            if (world.isAirBlock(x - 1, y, z))
            {
                ++i1;
            }

            if (world.isAirBlock(x + 1, y, z))
            {
                ++i1;
            }

            if (world.isAirBlock(x, y, z - 1))
            {
                ++i1;
            }

            if (world.isAirBlock(x, y, z + 1))
            {
                ++i1;
            }

            if (world.isAirBlock(x, y - 1, z))
            {
                ++i1;
            }

            if (l == 4 && i1 == 1 || l == 5)
            {
                world.setBlock(x, y, z, this.block, 0, 2);
                world.scheduledUpdatesAreImmediate = true;
                this.block.updateTick(world, x, y, z, random);
                world.scheduledUpdatesAreImmediate = false;
            }

            return true;

    }
}
