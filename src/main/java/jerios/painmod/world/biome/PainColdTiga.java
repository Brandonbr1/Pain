package jerios.painmod.world.biome;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenTaiga;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class PainColdTiga extends PainBiomeGen{

    private static final WorldGenTaiga1 field_150639_aC = new WorldGenTaiga1();
    private static final WorldGenTaiga2 field_150640_aD = new WorldGenTaiga2(false);
    private static final WorldGenMegaPineTree field_150641_aE = new WorldGenMegaPineTree(false, false);
    private static final WorldGenMegaPineTree field_150642_aF = new WorldGenMegaPineTree(false, true);
    private static final WorldGenBlockBlob field_150643_aG = new WorldGenBlockBlob(Blocks.mossy_cobblestone, 0);
    private int field_150644_aH;




    public PainColdTiga(int id, int p2) {
        super(id);
        this.setColor(3233098);
        this.setBiomeName("Pain Cold Taiga");
        this.func_76733_a(5159473);
        this.setEnableSnow();
        this.setTemperatureRainfall(-0.5F, 0.4F);
        this.setHeight(height_MidPlains).func_150563_c(16777215);

        this.field_150644_aH = p2;
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 8, 4, 4));
        this.theBiomeDecorator.treesPerChunk = 10;

        if (p2 != 1 && p2 != 2)
        {
            this.theBiomeDecorator.grassPerChunk = 1;
            this.theBiomeDecorator.mushroomsPerChunk = 1;
        }
        else
        {
            this.theBiomeDecorator.grassPerChunk = 7;
            this.theBiomeDecorator.deadBushPerChunk = 1;
            this.theBiomeDecorator.mushroomsPerChunk = 3;
        }
    }

    public WorldGenAbstractTree func_150567_a(Random rand)
    {
        return (WorldGenAbstractTree)((this.field_150644_aH == 1 || this.field_150644_aH == 2) && rand.nextInt(3) == 0 ? (this.field_150644_aH != 2 && rand.nextInt(13) != 0 ? field_150641_aE : field_150642_aF) : (rand.nextInt(3) == 0 ? field_150639_aC : field_150640_aD));
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    public WorldGenerator getRandomWorldGenForGrass(Random rand)
    {
        return rand.nextInt(5) > 0 ? new WorldGenTallGrass(Blocks.tallgrass, 2) : new WorldGenTallGrass(Blocks.tallgrass, 1);
    }

    public void decorate(World world, Random random, int x, int z)
    {
        int k;
        int l;
        int i1;
        int j1;

        if (this.field_150644_aH == 1 || this.field_150644_aH == 2)
        {
            k = random.nextInt(3);

            for (l = 0; l < k; ++l)
            {
                i1 = x + random.nextInt(16) + 8;
                j1 = z + random.nextInt(16) + 8;
                int k1 = world.getHeightValue(i1, j1);
                field_150643_aG.generate(world, random, i1, k1, j1);
            }
        }

        genTallFlowers.func_150548_a(3);

        for (k = 0; k < 7; ++k)
        {
            l = x + random.nextInt(16) + 8;
            i1 = z + random.nextInt(16) + 8;
            j1 = random.nextInt(world.getHeightValue(l, i1) + 32);
            genTallFlowers.generate(world, random, l, j1, i1);
        }

        super.decorate(world, random, x, z);
    }

    public void genTerrainBlocks(World world, Random random, Block[] blocks, byte[] metas, int columnX, int columnZ, double noise)
    {
        if (this.field_150644_aH == 1 || this.field_150644_aH == 2)
        {
            this.topBlock = Blocks.grass;
            this.field_150604_aj = 0;
            this.fillerBlock = Blocks.dirt;

            if (noise > 1.75D)
            {
                this.topBlock = Blocks.dirt;
                this.field_150604_aj = 1;
            }
            else if (noise > -0.95D)
            {
                this.topBlock = Blocks.dirt;
                this.field_150604_aj = 2;
            }
        }

        this.genBiomeTerrain(world, random, blocks, metas, columnX, columnZ, noise);
    }

    /**
     * Creates a mutated version of the biome and places it into the biomeList with an index equal to the original plus
     * 128
     */
    public BiomeGenBase createMutation()
    {
        return this.biomeID == BiomeGenBase.megaTaiga.biomeID ? (new BiomeGenTaiga(this.biomeID + 128, 2)).func_150557_a(5858897, true).setBiomeName("Mega Spruce Taiga").func_76733_a(5159473).setTemperatureRainfall(0.25F, 0.8F).setHeight(new BiomeGenBase.Height(this.rootHeight, this.heightVariation)) : super.createMutation();
    }



}
