package jerios.painmod.world.biome;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenDesertWells;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CACTUS;

public class PainDesert extends BiomeGenBase {
    public PainDesert(int id) {
        super(id);
        this.setBiomeName("Pain Desert");
                this.setDisableRain();
                this.setTemperatureRainfall(2.0F, 0.0F);
                this.setHeight(height_LowPlains);
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.sand;
        this.fillerBlock = Blocks.sand;
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 2;
        this.theBiomeDecorator.reedsPerChunk = 10;
      //  this.theBiomeDecorator.cactiPerChunk = 10;
        this.spawnableCreatureList.clear();
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
