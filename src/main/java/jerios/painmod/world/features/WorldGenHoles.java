package jerios.painmod.world.features;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenHoles extends WorldGenerator {

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        for (int i = 0; i < 15; i++)
        {

            int flag = random.nextInt(5);

            switch (flag) {
                case 1:
                    x -= random.nextInt(6);
                    z -= random.nextInt(6);
                    y -= random.nextInt(2);
                    break;
                case 2:
                    x += random.nextInt(6);
                    z += random.nextInt(6);
                    y -= random.nextInt(2);
                    break;
                case 3:
                    x -= random.nextInt(6);
                    z += random.nextInt(6);
                    y -= random.nextInt(2);
                    break;
                case 4:
                    x += random.nextInt(6);
                    z -= random.nextInt(6);
                    y -= random.nextInt(2);
                    break;

            }


            if (world.blockExists(x,y,z) && world.getBlock(x,y,z) != Blocks.air)
            {

                world.setBlock(x,y,z, Blocks.air, 0, 3);




                return true;
            }

        }





        return false;
    }
}
