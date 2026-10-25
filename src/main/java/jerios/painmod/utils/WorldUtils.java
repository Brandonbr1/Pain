package jerios.painmod.utils;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldUtils {


    /**
     * Returns the Y Position
     * @param x
     * @param z
     * @param world
     * @return
     */
    public int getTopBlockPos(int x, int z, World world)
    {
        int k;

        for (k = 63; !world.isAirBlock(x, k + 1, z); ++k)
        {
            ;

            Block block = world.getBlock(x,k,z);

            if (block != Blocks.air) {
                return k;
            }
        }




        return -1;
    }


}
