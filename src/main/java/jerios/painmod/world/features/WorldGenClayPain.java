package jerios.painmod.world.features;

import jerios.painmod.registry.ModItemBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenClayPain extends WorldGenerator {


    private Block field_150546_a;
    /** The number of blocks to generate. */
    private int numberOfBlocks;
    public WorldGenClayPain(int p_i2011_1_)
    {
        this.field_150546_a = Blocks.air;
        this.numberOfBlocks = p_i2011_1_;
    }

    public boolean generate(World world, Random random, int x, int y, int z)
    {
        if (world.getBlock(x, y, z).getMaterial() == Material.air)
        {
            return false;
        }
        else
        {
            int l = random.nextInt(this.numberOfBlocks - 2) + 2;
            byte b0 = 1;

            for (int i1 = x - l; i1 <= x + l; ++i1)
            {
                for (int j1 = z - l; j1 <= z + l; ++j1)
                {
                    int k1 = i1 - x;
                    int l1 = j1 - z;

                    if (k1 * k1 + l1 * l1 <= l * l)
                    {
                        for (int i2 = y - b0; i2 <= y + b0; ++i2)
                        {
                            Block block = world.getBlock(i1, i2, j1);

                            if (block == ModItemBlocks.painGrass || block == ModItemBlocks.painDirt)
                           {
                                world.setBlock(i1, i2, j1, this.field_150546_a, 0, 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}
