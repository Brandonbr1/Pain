package jerios.painmod.world.biome;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public class PainPlains extends BiomeGenBase {
    public PainPlains(int id) {
        super(id);
    }

    public PainPlains(int id, boolean register) {
        super(id, register);
    }


    @Override
    public void decorate(World world, Random random, int x, int z) {
        super.decorate(world, random, x, z);




    }
}
