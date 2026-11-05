package jerios.painmod.core.world.biome;

import jerios.painmod.core.entity.PainCreeper;
import jerios.painmod.core.entity.PainSkeleton;
import jerios.painmod.core.entity.PainSpider;
import jerios.painmod.core.entity.PainZombie;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenDesertWells;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class PainDesert extends PainBiomeGen {
    public PainDesert(int id) {
        super(id);
        this.setBiomeName("Pain Desert");
                this.setDisableRain();
                this.setTemperatureRainfall(2.0F, 0.0F);
                this.setHeight(height_LowPlains);
        spawnableMonsterList.clear();
        this.topBlock = Blocks.sand;
        this.fillerBlock = Blocks.sand;
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 2;
        this.theBiomeDecorator.reedsPerChunk = 10;
      //  this.theBiomeDecorator.cactiPerChunk = 10;
        this.spawnableCreatureList.clear();


        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(PainZombie.class, 100, 4, 4));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(PainSpider.class, 100, 4, 4));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(PainSkeleton.class, 100, 4, 4));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(PainCreeper.class, 100, 4, 4));
    }


       WorldGenerator cactusGen = new WorldGenCactus();


    public void decorate(World world, Random random, int x, int z)
    {
        super.decorate(world, random, x, z);

        if (random.nextInt(1000) == 0)
        {
            int k = x + random.nextInt(16) + 8;
            int l = z + random.nextInt(16) + 8;
            WorldGenDesertWells worldgendesertwells = new WorldGenDesertWells();
            worldgendesertwells.generate(world, random, k, world.getHeightValue(k, l) + 1, l);
        }

        int j;
        for (j = 0; j < 50; ++j)
        {
           int k =  random.nextInt(16) + 8;
           int l =  random.nextInt(16) + 8;
           int i1 = random.nextInt(world.getHeightValue(k, l) * 2);
           this.cactusGen.generate(world, random, k, i1, l);
        }


    }


    @Override
    public void genTerrainBlocks(World world, Random random, Block[] blocks, byte[] metas, int columnX, int columnZ, double noise) {
        super.genTerrainBlocks(world, random, blocks, metas, columnX, columnZ, noise);
    }

}
