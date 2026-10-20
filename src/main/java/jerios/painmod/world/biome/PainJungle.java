package jerios.painmod.world.biome;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenMelon;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenVines;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class PainJungle extends BiomeGenBase {
    public PainJungle(int id) {
        super(id);
    }


    private boolean field_150614_aC;

    public PainJungle(int id, boolean p_i45379_2_)
    {
        super(id);
        this.setColor(5470985);
        this.setBiomeName("Pain Jungle");
                this.func_76733_a(5470985);
                this.setTemperatureRainfall(0.95F, 0.9F);
        this.field_150614_aC = p_i45379_2_;

        this.theBiomeDecorator.treesPerChunk = 50;
        this.theBiomeDecorator.grassPerChunk = 25;
        this.theBiomeDecorator.flowersPerChunk = 4;

        if (!p_i45379_2_)
        {
            this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityOcelot.class, 2, 1, 1));
        }

        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityChicken.class, 10, 4, 4));
    }

    @Override
    public WorldGenAbstractTree func_150567_a(Random rand)
    {
        return (WorldGenAbstractTree)(rand.nextInt(10) == 0 ? this.worldGeneratorBigTree : (rand.nextInt(2) == 0 ? new WorldGenShrub(3, 0) : (!this.field_150614_aC && rand.nextInt(3) == 0 ? new WorldGenMegaJungle(false, 10, 20, 3, 3) : new WorldGenTrees(false, 4 + rand.nextInt(7), 3, 3, true))));
    }


    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random rand)
    {
        return rand.nextInt(4) == 0 ? new WorldGenTallGrass(Blocks.tallgrass, 2) : new WorldGenTallGrass(Blocks.tallgrass, 1);
    }

    @Override
    public void decorate(World world, Random random, int x, int z)
    {
        super.decorate(world, random, x, z);
        int k = x + random.nextInt(16) + 8;
        int l = z + random.nextInt(16) + 8;
        int height = world.getHeightValue(k, l) * 2; //This was the original input for the nextInt below.  But it could == 0, which crashes nextInt
        if (height < 1) height = 1;
        int i1 = random.nextInt(height);
        (new WorldGenMelon()).generate(world, random, k, i1, l);
        WorldGenVines worldgenvines = new WorldGenVines();

        for (l = 0; l < 50; ++l)
        {
            i1 = x + random.nextInt(16) + 8;
            short short1 = 128;
            int j1 = z + random.nextInt(16) + 8;
            worldgenvines.generate(world, random, i1, short1, j1);
        }
    }


}
