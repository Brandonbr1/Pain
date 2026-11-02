package jerios.painmod.world.features;

import jerios.painmod.registry.ModItemBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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

            if (world.getBlock(x - 1, y, z) == ModItemBlocks.painDirt || world.getBlock(x - 1, y, z) == ModItemBlocks.painGrass || world.getBlock(x - 1, y, z) == ModItemBlocks.painStone)
            {
                ++l;
            }

            if (world.getBlock(x + 1, y, z) == ModItemBlocks.painDirt || world.getBlock(x + 1, y, z) == ModItemBlocks.painGrass || world.getBlock(x + 1, y, z) == ModItemBlocks.painStone)
            {
                ++l;
            }

            if (world.getBlock(x, y, z - 1) == ModItemBlocks.painDirt || world.getBlock(x, y, z - 1) == ModItemBlocks.painGrass || world.getBlock(x, y, z - 1) == ModItemBlocks.painStone)
            {
                ++l;
            }

            if (world.getBlock(x, y, z + 1) == ModItemBlocks.painDirt || world.getBlock(x, y, z + 1) == ModItemBlocks.painGrass || world.getBlock(x, y, z + 1) == ModItemBlocks.painStone)
            {
                ++l;
            }

            if (world.getBlock(x, y - 1, z) == ModItemBlocks.painDirt || world.getBlock(x, y - 1, z) == ModItemBlocks.painGrass || world.getBlock(x, y - 1, z) == ModItemBlocks.painStone)
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
