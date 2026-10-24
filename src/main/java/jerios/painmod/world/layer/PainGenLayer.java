package jerios.painmod.world.layer;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.*;
import net.minecraftforge.common.BiomeManager;

import java.util.Random;
import java.util.concurrent.Callable;

public abstract class PainGenLayer extends GenLayer
{
    /** seed from World#getWorldSeed that is used in the LCG prng */
    private long worldGenSeed;
    /** parent GenLayer that was provided via the constructor */
    protected GenLayer parent;
    /**
     * final part of the LCG prng that uses the chunk X, Z coords along with the other two seeds to generate
     * pseudorandom numbers
     */
    private long chunkSeed;
    /** base seed to the LCG prng provided via the constructor */
    protected long baseSeed;

    public Random random = new Random(chunkSeed);


    /**
     * the first array item is a linked list of the bioms, the second is the zoom function, the third is the same as the
     * first.
     */
    public static GenLayer[] initializeAllBiomeGenerators(long seed, WorldType worldType)
    {
        PainGenLayerIsland genlayerisland = new PainGenLayerIsland(1L);
        PainGenLayerFuzzyZoom genlayerfuzzyzoom = new PainGenLayerFuzzyZoom(2000L, genlayerisland);
        GenLayerAddIsland genlayeraddisland = new GenLayerAddIsland(1L, genlayerfuzzyzoom);
        PainGenLayerZoom genlayerzoom = new PainGenLayerZoom(2001L, genlayeraddisland);
        genlayeraddisland = new GenLayerAddIsland(2L, genlayerzoom);

        GenLayerAddSnow genlayeraddsnow = new GenLayerAddSnow(2L, genlayeraddisland);

        genlayerzoom = new PainGenLayerZoom(2002L, genlayeraddsnow);


        genlayeraddisland = new GenLayerAddIsland(3L, genlayerzoom);
        genlayerzoom = new PainGenLayerZoom(2003L, genlayeraddisland);
        genlayeraddisland = new GenLayerAddIsland(4L, genlayerzoom);
        GenLayerRemoveTooMuchOcean removeTooMuchOcean = new GenLayerRemoveTooMuchOcean(2L,genlayeraddisland);
        GenLayerAddMushroomIsland genlayeraddmushroomisland = new GenLayerAddMushroomIsland(5L, removeTooMuchOcean);
        byte b0 = 6;


        GenLayer genlayer = PainGenLayerZoom.magnify(1000L, genlayeraddmushroomisland, 0);
        PainGenLayerRiverInit genlayerriverinit = new PainGenLayerRiverInit(100L, genlayer);
        genlayer = PainGenLayerZoom.magnify(1000L, genlayerriverinit, b0 + 2);
        GenLayerRiver genlayerriver = new GenLayerRiver(1L, genlayer);
        GenLayerSmooth genlayersmooth = new GenLayerSmooth(1000L, genlayerriver);
        GenLayer genlayer1 = PainGenLayerZoom.magnify(1000L, genlayeraddmushroomisland, 0);
        PainGenLayerBiome genlayerbiome = new PainGenLayerBiome(200L, genlayer1, worldType);
        genlayer1 = PainGenLayerZoom.magnify(1000L, genlayerbiome, 2);
        Object object = new GenLayerHills(1000L,genlayerbiome, genlayer1);

        for (int j = 0; j < b0; ++j)
        {
            object = new PainGenLayerZoom((long)(1000 + j), (GenLayer)object);

            if (j == 0)
            {
                object = new GenLayerAddIsland(3L, (GenLayer)object);
            }

            if (j == 1)
            {
                object = new GenLayerShore(1000L, (GenLayer)object);
            }

            if (j == 1)
            {
              //  object = new GenLayerSwampRivers(1000L, (GenLayer)object);
            }
        }

        GenLayerSmooth genlayersmooth1 = new GenLayerSmooth(1000L, (GenLayer)object);
        PainGenLayerMix genlayerrivermix = new PainGenLayerMix(100L, genlayersmooth1, genlayersmooth);
        PainGenLayerVoronoiZoom genlayervoronoizoom = new PainGenLayerVoronoiZoom(10L, genlayerrivermix);
        genlayerrivermix.initWorldGenSeed(seed);
        genlayervoronoizoom.initWorldGenSeed(seed);
        return new GenLayer[] {genlayerrivermix, genlayervoronoizoom, genlayerrivermix};
    }

    public PainGenLayer(long p_i2125_1_)
    {
        super(p_i2125_1_);
        this.baseSeed = p_i2125_1_;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += p_i2125_1_;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += p_i2125_1_;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += p_i2125_1_;
    }

    public void genSeed(long seed) {

        this.baseSeed = seed;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += seed;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += seed;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += seed;
    }

    /**
     * Initialize layer's local worldGenSeed based on its own baseSeed and the world's global seed (passed in as an
     * argument).
     */
    public void initWorldGenSeed(long seed)
    {
        this.worldGenSeed = seed;

        if (this.parent != null)
        {
            this.parent.initWorldGenSeed(seed);
        }

        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
    }

    /**
     * Initialize layer's current chunkSeed based on the local worldGenSeed and the (x,z) chunk coordinates.
     */
    public void initChunkSeed(long p_75903_1_, long p_75903_3_)
    {
        this.chunkSeed = this.worldGenSeed;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += p_75903_1_;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += p_75903_3_;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += p_75903_1_;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += p_75903_3_;
    }

    /**
     * returns a LCG pseudo random number from [0, x). Args: int x
     */
    protected int nextInt(int intX)
    {
        int j = (int)((this.chunkSeed >> 24) % (long)intX);

        if (j < 0)
        {
            j += intX;
        }

        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += this.worldGenSeed;
        return j;
    }

    /**
     * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
     * amounts, or biomeList[] indices based on the particular GenLayer subclass.
     */
    public abstract int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight);

    /**
     * returns true if the biomeIDs are equal, or returns the result of the comparison as per BiomeGenBase.isEqualTo
     */
    protected static boolean compareBiomesById(final int biomeIDA, final int biomeIDB)
    {
        if (biomeIDA == biomeIDB)
        {
            return true;
        }
        else if (biomeIDA != BiomeGenBase.mesaPlateau_F.biomeID && biomeIDA != BiomeGenBase.mesaPlateau.biomeID)
        {
            try
            {
                return BiomeGenBase.getBiome(biomeIDA) != null && BiomeGenBase.getBiome(biomeIDB) != null ? BiomeGenBase.getBiome(biomeIDA).isEqualTo(BiomeGenBase.getBiome(biomeIDB)) : false;
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Comparing biomes");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Biomes being compared");
                crashreportcategory.addCrashSection("Biome A ID", Integer.valueOf(biomeIDA));
                crashreportcategory.addCrashSection("Biome B ID", Integer.valueOf(biomeIDB));
                crashreportcategory.addCrashSectionCallable("Biome A", new Callable()
                {

                    public String call()
                    {
                        return String.valueOf(BiomeGenBase.getBiome(biomeIDA));
                    }
                });
                crashreportcategory.addCrashSectionCallable("Biome B", new Callable()
                {

                    public String call()
                    {
                        return String.valueOf(BiomeGenBase.getBiome(biomeIDB));
                    }
                });
                throw new ReportedException(crashreport);
            }
        }
        else
        {
            return biomeIDB == BiomeGenBase.mesaPlateau_F.biomeID || biomeIDB == BiomeGenBase.mesaPlateau.biomeID;
        }
    }

    /**
     * returns true if the biomeId is one of the various ocean biomes.
     */
    protected static boolean isBiomeOceanic(int biomeId)
    {
        return BiomeManager.oceanBiomes.contains(BiomeGenBase.getBiome(biomeId));
    }

    /**
     * selects a random integer from a set of provided integers
     */
    protected int selectRandom(int ... args)
    {
        return args[this.nextInt(args.length)];
    }

    /**
     * returns the most frequently occurring number of the set, or a random number from those provided
     */
    protected int selectModeOrRandom(int p_151617_1_, int p_151617_2_, int p_151617_3_, int p_151617_4_)
    {
        return p_151617_2_ == p_151617_3_ && p_151617_3_ == p_151617_4_ ? p_151617_2_ : (p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_3_ ? p_151617_1_ : (p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_3_ && p_151617_1_ == p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_2_ && p_151617_3_ != p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_3_ && p_151617_2_ != p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_4_ && p_151617_2_ != p_151617_3_ ? p_151617_1_ : (p_151617_2_ == p_151617_3_ && p_151617_1_ != p_151617_4_ ? p_151617_2_ : (p_151617_2_ == p_151617_4_ && p_151617_1_ != p_151617_3_ ? p_151617_2_ : (p_151617_3_ == p_151617_4_ && p_151617_1_ != p_151617_2_ ? p_151617_3_ : this.selectRandom(new int[] {p_151617_1_, p_151617_2_, p_151617_3_, p_151617_4_}))))))))));
    }

    public static GenLayer getBiomeLayer(long worldSeed, GenLayer parentLayer, WorldType type)
    {
        GenLayer ret = new PainGenLayerBiome(200L, parentLayer, type);
        ret = GenLayerZoom.magnify(1000L, ret, 2);
      //  ret = new PainGenLayerBiomeEdge(1000L, ret);

        return ret;
    }



}