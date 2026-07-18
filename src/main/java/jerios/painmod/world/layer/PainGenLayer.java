package jerios.painmod.world.layer;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.WorldTypeEvent;

import java.util.concurrent.Callable;

public abstract class PainGenLayer
{
    /** seed from World#getWorldSeed that is used in the LCG prng */
    private long worldGenSeed;
    /** parent GenLayer that was provided via the constructor */
    protected PainGenLayer parent;
    /**
     * final part of the LCG prng that uses the chunk X, Z coords along with the other two seeds to generate
     * pseudorandom numbers
     */
    private long chunkSeed;
    /** base seed to the LCG prng provided via the constructor */
    protected long baseSeed;
    private static final String __OBFID = "CL_00000559";

    /**
     * the first array item is a linked list of the bioms, the second is the zoom function, the third is the same as the
     * first.
     */
    public static PainGenLayer[] initializeAllBiomeGenerators(long p_75901_0_, WorldType p_75901_2_)
    {
        boolean flag = false;
        PainGenLayerIsland genlayerisland = new PainGenLayerIsland(1L);
        PainGenLayerFuzzyZoom genlayerfuzzyzoom = new PainGenLayerFuzzyZoom(2000L, genlayerisland);
        PainGenLayerAddIsland genlayeraddisland = new PainGenLayerAddIsland(1L, genlayerfuzzyzoom);
        PainGenLayerZoom genlayerzoom = new PainGenLayerZoom(2001L, genlayeraddisland);
        genlayeraddisland = new PainGenLayerAddIsland(2L, genlayerzoom);
        genlayeraddisland = new PainGenLayerAddIsland(50L, genlayeraddisland);
        genlayeraddisland = new PainGenLayerAddIsland(70L, genlayeraddisland);
        PainGenLayerRemoveTooMuchOcean genlayerremovetoomuchocean = new PainGenLayerRemoveTooMuchOcean(2L, genlayeraddisland);
        PainGenLayerAddSnow genlayeraddsnow = new PainGenLayerAddSnow(2L, genlayerremovetoomuchocean);
        genlayeraddisland = new PainGenLayerAddIsland(3L, genlayeraddsnow);
        PainGenLayerEdge genlayeredge = new PainGenLayerEdge(2L, genlayeraddisland, PainGenLayerEdge.Mode.COOL_WARM);
        genlayeredge = new PainGenLayerEdge(2L, genlayeredge, PainGenLayerEdge.Mode.HEAT_ICE);
        genlayeredge = new PainGenLayerEdge(3L, genlayeredge, PainGenLayerEdge.Mode.SPECIAL);
        genlayerzoom = new PainGenLayerZoom(2002L, genlayeredge);
        genlayerzoom = new PainGenLayerZoom(2003L, genlayerzoom);
        genlayeraddisland = new PainGenLayerAddIsland(4L, genlayerzoom);
        PainGenLayerAddMushroomIsland genlayeraddmushroomisland = new PainGenLayerAddMushroomIsland(5L, genlayeraddisland);
        PainGenLayerDeepOcean genlayerdeepocean = new PainGenLayerDeepOcean(4L, genlayeraddmushroomisland);
        PainGenLayer genlayer2 = PainGenLayerZoom.magnify(1000L, genlayerdeepocean, 0);
        byte b0 = 4;

        if (p_75901_2_ == WorldType.LARGE_BIOMES)
        {
            b0 = 6;
        }

        if (flag)
        {
            b0 = 4;
        }
        b0 = getModdedBiomeSize(p_75901_2_, b0);

        PainGenLayer genlayer = PainGenLayerZoom.magnify(1000L, genlayer2, 0);
        PainGenLayerRiverInit genlayerriverinit = new PainGenLayerRiverInit(100L, genlayer);
        Object object = getBiomeLayer(p_75901_0_, genlayer2, p_75901_2_);
        // Object objecttest = p_75901_2_.getBiomeLayer(p_75901_0_, genlayer2);

        PainGenLayer genlayer1 = PainGenLayerZoom.magnify(1000L, genlayerriverinit, 2);
        PainGenLayerHills genlayerhills = new PainGenLayerHills(1000L, (PainGenLayer)object, genlayer1);
        genlayer = PainGenLayerZoom.magnify(1000L, genlayerriverinit, 2);
        genlayer = PainGenLayerZoom.magnify(1000L, genlayer, b0);
        PainGenLayerRiver genlayerriver = new PainGenLayerRiver(1L, genlayer);
        PainGenLayerSmooth genlayersmooth = new PainGenLayerSmooth(1000L, genlayerriver);
        object = new PainGenLayerRareBiome(1001L, genlayerhills);

        for (int j = 0; j < b0; ++j)
        {
            object = new PainGenLayerZoom((long)(1000 + j), (PainGenLayer)object);

            if (j == 0)
            {
                object = new PainGenLayerAddIsland(3L, (PainGenLayer)object);
            }

            if (j == 1)
            {
                object = new PainGenLayerShore(1000L, (PainGenLayer)object);
            }
        }

        PainGenLayerSmooth genlayersmooth1 = new PainGenLayerSmooth(1000L, (PainGenLayer)object);
        PainGenLayerRiverMix genlayerrivermix = new PainGenLayerRiverMix(100L, genlayersmooth1, genlayersmooth);
        PainGenLayerVoronoiZoom genlayervoronoizoom = new PainGenLayerVoronoiZoom(10L, genlayerrivermix);
        genlayerrivermix.initWorldGenSeed(p_75901_0_);
        genlayervoronoizoom.initWorldGenSeed(p_75901_0_);
        return new PainGenLayer[] {genlayerrivermix, genlayervoronoizoom, genlayerrivermix};
    }

    public PainGenLayer(long p_i2125_1_)
    {
        this.baseSeed = p_i2125_1_;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += p_i2125_1_;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += p_i2125_1_;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += p_i2125_1_;
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
                    private static final String __OBFID = "CL_00000560";
                    public String call()
                    {
                        return String.valueOf(BiomeGenBase.getBiome(biomeIDA));
                    }
                });
                crashreportcategory.addCrashSectionCallable("Biome B", new Callable()
                {
                    private static final String __OBFID = "CL_00000561";
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

    // ========== Jerios =================================

    // TODO: UHHH
    public static PainGenLayer getBiomeLayer(long worldSeed, PainGenLayer parentLayer, WorldType type)
    {
        PainGenLayer ret = new PainGenLayerBiome(200L, parentLayer, type);
        ret = PainGenLayerZoom.magnify(1000L, ret, 2);
        ret = new PainGenLayerBiomeEdge(1000L, ret);
        return ret;
    }


    // =========== Jerios ===================================

    /* ======================================== FORGE START =====================================*/
    protected long nextLong(long par1)
    {
        long j = (this.chunkSeed >> 24) % par1;

        if (j < 0)
        {
            j += par1;
        }

        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += this.worldGenSeed;
        return j;
    }

    public static byte getModdedBiomeSize(WorldType worldType, byte original)
    {
        WorldTypeEvent.BiomeSize event = new WorldTypeEvent.BiomeSize(worldType, original);
        MinecraftForge.TERRAIN_GEN_BUS.post(event);
        return event.newSize;
    }
    /* ========================================= FORGE END ======================================*/
}