package jerios.painmod.world.biome;

import net.minecraft.block.BlockFlower;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public class PainPlains extends BiomeGenBase {
    boolean sunflower = false;

    public PainPlains(int id) {
        super(id);
        this.setColor(9286496);
        this.setBiomeName("Pain Plains");
        this.setTemperatureRainfall(0.8F, 0.4F);
        this.setHeight(height_LowPlains);
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.flowersPerChunk = 4;
        this.theBiomeDecorator.grassPerChunk = 10;
        this.flowers.clear();
        this.addFlower(Blocks.red_flower,    4,  3);
        this.addFlower(Blocks.red_flower,    5,  3);
        this.addFlower(Blocks.red_flower,    6,  3);
        this.addFlower(Blocks.red_flower,    7,  3);
        this.addFlower(Blocks.red_flower,    0, 20);
        this.addFlower(Blocks.red_flower,    3, 20);
        this.addFlower(Blocks.red_flower,    8, 20);
        this.addFlower(Blocks.yellow_flower, 0, 30);
    }

    public String func_150572_a(Random random, int x, int y, int z)
    {
        double d0 = plantNoise.func_151601_a((double)x / 200.0D, (double)z / 200.0D);
        int l;

        if (d0 < -0.8D)
        {
            l = random.nextInt(4);
            return BlockFlower.field_149859_a[4 + l];
        }
        else if (random.nextInt(3) > 0)
        {
            l = random.nextInt(3);
            return l == 0 ? BlockFlower.field_149859_a[0] : (l == 1 ? BlockFlower.field_149859_a[3] : BlockFlower.field_149859_a[8]);
        }
        else
        {
            return BlockFlower.field_149858_b[0];
        }
    }


    @Override
    public void decorate(World world, Random random, int x, int z)
    {
        double d0 = plantNoise.func_151601_a((double)(x + 8) / 200.0D, (double)(z + 8) / 200.0D);
        int k;
        int l;
        int i1;
        int j1;

        if (d0 < -0.8D)
        {
            this.theBiomeDecorator.flowersPerChunk = 15;
            this.theBiomeDecorator.grassPerChunk = 5;
        }
        else
        {
            this.theBiomeDecorator.flowersPerChunk = 4;
            this.theBiomeDecorator.grassPerChunk = 10;
            genTallFlowers.func_150548_a(2);

            for (k = 0; k < 7; ++k)
            {
                l = x + random.nextInt(16) + 8;
                i1 = z + random.nextInt(16) + 8;
                j1 = random.nextInt(world.getHeightValue(l, i1) + 32);
                genTallFlowers.generate(world, random, l, j1, i1);
            }
        }

        if (sunflower)
        {
            genTallFlowers.func_150548_a(0);

            for (k = 0; k < 10; ++k)
            {
                l = x + random.nextInt(16) + 8;
                i1 = z + random.nextInt(16) + 8;
                j1 = random.nextInt(world.getHeightValue(l, i1) + 32);
                genTallFlowers.generate(world, random, l, j1, i1);
            }
        }

        super.decorate(world, random, x, z);
    }
}
