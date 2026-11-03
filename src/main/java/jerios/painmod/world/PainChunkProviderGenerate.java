package jerios.painmod.world;

import cpw.mods.fml.common.eventhandler.Event;
import jerios.painmod.Share;
import jerios.painmod.integerationHandler.acl.ACLUtils;
import jerios.painmod.registry.ModItemBlocks;
import jerios.painmod.world.features.WorldGenClayPain;
import jerios.painmod.world.features.WorldGenHellLavaPain;
import jerios.painmod.world.features.WorldGenHoles;
import jerios.painmod.world.mapGen.PainMapGenCaves;
import jerios.painmod.world.mapGen.PainMapGenRavine;
import jerios.painmod.world.spawners.PainSpawner;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.*;

public class PainChunkProviderGenerate implements IChunkProvider {

    // store a list of corrupted chunks so we know as to not spawn stuff near it.
    private final List<ChunkCoordIntPair> corruptedChunks = new ArrayList<>();

    private Random rand;
    private Random unpredictableRandom; //Unseeded random. World seeds will never be consistent, but will generally stay the same.
    private NoiseGeneratorOctaves noiseGenLow; // lower noise
    private NoiseGeneratorOctaves noiseGenUpper; // upper noise
    private NoiseGeneratorOctaves noiseGenSlide;
    public NoiseGeneratorOctaves noiseGenStone;
    public NoiseGeneratorOctaves noiseGenDepth;

    public NoiseGeneratorOctaves noiseGenErosion;

    public NoiseGeneratorPerlin spawnerMobNoise; // use as actual noise for mob spawning, gets worse the further out

    private World worldObj;
    private final boolean mapFeaturesEnabled;
    private final double[] noiseArray;
    private final float[] parabolicField;
    private double[] stoneNoise = new double[256];
    private MapGenBase caveGenerator = new PainMapGenCaves();
    private MapGenVillage villageGenerator = new MapGenVillage();
    private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
    private MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();
    private MapGenBase ravineGenerator = new PainMapGenRavine();
    private BiomeGenBase[] biomesForGeneration;
    double[] noiseSlide;
    double[] noiseLow;
    double[] noiseUpper;
    double[] depthNoise;
    double[] erosionNoise;
    double[] mobSpawnerNoise;

    public PainChunkProviderGenerate(World world, long seed, boolean mapFeatures)
    {
        this.worldObj = world;
        this.mapFeaturesEnabled = mapFeatures;
        this.rand = new Random(seed);
        this.unpredictableRandom = new Random();


        this.noiseGenLow = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGenUpper = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGenSlide = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseGenStone = new NoiseGeneratorOctaves(rand, 4);
        this.noiseGenDepth = new NoiseGeneratorOctaves(this.rand, 10);

        this.noiseGenErosion = new NoiseGeneratorOctaves(this.rand, 6);

        this.spawnerMobNoise = new NoiseGeneratorPerlin(this.rand, 32);

        this.noiseArray = new double[825];
        this.parabolicField = new float[25];

        for (int j = -2; j <= 2; ++j)
        {
            for (int k = -2; k <= 2; ++k)
            {
                float f = 10.0F / MathHelper.sqrt_float((float)(j * j + k * k) + 0.2F);
                this.parabolicField[j + 2 + (k + 2) * 5] = f;
            }
        }
    }

    public void generateTerrain(int x, int z, Block[] blocks)
    {
        byte oceanHeight = 63;
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
        this.generateInitalNoiseXZStack(x * 4, 0, z * 4, x, z);

        for (int noiseLayer1 = 0; noiseLayer1 < 4; ++noiseLayer1)
        {
            int noiseL1P1 = noiseLayer1 * 5;
            int noiseL1P2 = (noiseLayer1 + 1) * 5;

            for (int noiseLayer2 = 0; noiseLayer2 < 4; ++noiseLayer2)
            {
                int noiseL2P1 = (noiseL1P1 + noiseLayer2) * 33;
                int noiseL2P2 = (noiseL1P1 + noiseLayer2 + 1) * 33;
                int noiseL2P3 = (noiseL1P2 + noiseLayer2) * 33;
                int noiseL2P4 = (noiseL1P2 + noiseLayer2 + 1) * 33;

                for (int noiseLayer3 = 0; noiseLayer3 < 32; ++noiseLayer3)
                {
                    double noiseScaleLayer3 = 0.125D;
                    double noiseLayer3P1 = this.noiseArray[noiseL2P1 + noiseLayer3];
                    double noiseLayer3P2 = this.noiseArray[noiseL2P2 + noiseLayer3];
                    double noiseLayer3P3 = this.noiseArray[noiseL2P3 + noiseLayer3];
                    double noiseLayer3P4 = this.noiseArray[noiseL2P4 + noiseLayer3];
                    double noiseLayer3P5 = (this.noiseArray[noiseL2P1 + noiseLayer3 + 1] - noiseLayer3P1) * noiseScaleLayer3;
                    double noiseLayer3P6 = (this.noiseArray[noiseL2P2 + noiseLayer3 + 1] - noiseLayer3P2) * noiseScaleLayer3;
                    double noiseLayer3P7 = (this.noiseArray[noiseL2P3 + noiseLayer3 + 1] - noiseLayer3P3) * noiseScaleLayer3;
                    double noiseLayer3P8 = (this.noiseArray[noiseL2P4 + noiseLayer3 + 1] - noiseLayer3P4) * noiseScaleLayer3;

                    for (int noiseLayer4 = 0; noiseLayer4 < 8; ++noiseLayer4)
                    {
                        double noiseScaleLayer4 = 0.25D;
                        double noiseLayer4P1 = noiseLayer3P1;
                        double noiseLayer4P2 = noiseLayer3P2;
                        double noiseLayer4P3 = (noiseLayer3P3 - noiseLayer3P1) * noiseScaleLayer4;
                        double noiseLayer4P4 = (noiseLayer3P4 - noiseLayer3P2) * noiseScaleLayer4;

                        for (int noiseLayer5 = 0; noiseLayer5 < 4; ++noiseLayer5)
                        {
                            int depth = noiseLayer5 + noiseLayer1 * 4 << 12 | 0 + noiseLayer2 * 4 << 8 | noiseLayer3 * 8 + noiseLayer4;
                            short maxDepth = 256;
                            depth -= maxDepth;
                            double noiseScaleLayer5 = 0.25D;
                            double noiseLayer5P1 = (noiseLayer4P2 - noiseLayer4P1) * noiseScaleLayer5;
                            double noiseLayer5P2 = noiseLayer4P1 - noiseLayer5P1;

                            for (int noiseLayer6 = 0; noiseLayer6 < 4; ++noiseLayer6)
                            {
                                if ((noiseLayer5P2 += noiseLayer5P1) > 0.0D)
                                {
                                    blocks[depth += maxDepth] = ModItemBlocks.painStone;
                                }
                                else if (noiseLayer3 * 8 + noiseLayer4 < oceanHeight)
                                {
                                    blocks[depth += maxDepth] = Blocks.water;
                                }
                                else
                                {
                                    blocks[depth += maxDepth] = null;
                                }
                            }

                            noiseLayer4P1 += noiseLayer4P3;
                            noiseLayer4P2 += noiseLayer4P4;
                        }

                        noiseLayer3P1 += noiseLayer3P5;
                        noiseLayer3P2 += noiseLayer3P6;
                        noiseLayer3P3 += noiseLayer3P7;
                        noiseLayer3P4 += noiseLayer3P8;
                    }
                }
            }
        }
    }

    public void replaceBlocksForBiome(int chunkX, int chunkZ, Block[] blocks, byte[] metas, BiomeGenBase[] biomes)
    {
        ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, chunkX, chunkZ, blocks, metas, biomes, this.worldObj);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Event.Result.DENY) return;

        double stoneScale = 0.03125D;

        if (rand.nextInt(80) == 0 || unpredictableRandom.nextInt(96) == 0) {
            stoneScale = 0.06125D;
        }


        this.stoneNoise = this.noiseGenStone.generateNoiseOctaves(
                this.stoneNoise,
                chunkX * 16,
                chunkZ * 16,
                0,
                16,
                16,
                1,
                stoneScale * 2.0D,
                stoneScale * 2.0D,
                stoneScale * 2.0D);



        for (int k = 0; k < 16; ++k)
        {
            for (int l = 0; l < 16; ++l)
            {
                BiomeGenBase biomegenbase = biomes[l + k * 16];
                biomegenbase.genTerrainBlocks(this.worldObj, this.rand, blocks, metas, chunkX * 16 + k, chunkZ * 16 + l, this.stoneNoise[l + k * 16]);
            }
        }
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    public Chunk loadChunk(int chunkX, int chunkZ)
    {
        return this.provideChunk(chunkX, chunkZ);
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    public Chunk provideChunk(int x, int z)
    {
        this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        Block[] blocks = new Block[65536];
        byte[] metas = new byte[65536];
        this.generateTerrain(x, z, blocks);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        this.replaceBlocksForBiome(x, z, blocks, metas, this.biomesForGeneration);
        this.caveGenerator.func_151539_a(this, this.worldObj, x, z, blocks);
        this.ravineGenerator.func_151539_a(this, this.worldObj, x, z, blocks);

        if (this.mapFeaturesEnabled)
        {
            this.mineshaftGenerator.func_151539_a(this, this.worldObj, x, z, blocks);
            this.villageGenerator.func_151539_a(this, this.worldObj, x, z, blocks);
            this.scatteredFeatureGenerator.func_151539_a(this, this.worldObj, x, z, blocks);
        }

        Chunk chunk = new Chunk(this.worldObj, blocks, metas, x, z);

        // in rare circumstances, return a corrupted chunk.
        if (rand.nextInt(500) == 0) {
            chunk = new EmptyChunk(worldObj,x,z);
            corruptedChunks.add(new ChunkCoordIntPair(x,z));
        }


        byte[] biomeArray = chunk.getBiomeArray();

        for (int k = 0; k < biomeArray.length; ++k)
        {
            biomeArray[k] = (byte)this.biomesForGeneration[k].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    private void generateInitalNoiseXZStack(int x, int y, int z, int chunkX, int chunkZ)
    {
        double scale1 = 684.412D;
        double scale2 = 200.0D;
        double scale3 = 0.5D;
        double scale4 = 8.555150000000001D;
        double scale5 = 4.277575000000001D;
        double scale6 = 8.555150000000001D;
        // double d1 = 684.412D;
        // double d2 = 512.0D;
        //  double d3 = 512.0D;


        //perhaps messing wit this would get what I want.. It does... do a random call
        this.depthNoise = this.noiseGenDepth.generateNoiseOctaves(this.depthNoise, x, z, 5, 5, scale2, scale2, scale3);
        this.noiseSlide = this.noiseGenSlide.generateNoiseOctaves(this.noiseSlide, x, y, z, 5, 33, 5, scale6, scale5, scale4);
        this.noiseLow = this.noiseGenLow.generateNoiseOctaves(this.noiseLow, x, y, z, 5, 33, 5, scale1, scale1, scale1);
        this.noiseUpper = this.noiseGenUpper.generateNoiseOctaves(this.noiseUpper, x, y, z, 5, 33, 5, scale1, scale1, scale1);
        //DO STUFF WITH IT noiseGen4
         this.erosionNoise = noiseGenErosion.generateNoiseOctaves(this.erosionNoise,x,y,z,5,1,5,scale1,1,scale1);
        boolean flag1 = false;
        boolean flag = false;
        int index = 0;
        int index2D = 0;
        double d4 = 8.5D;

        for (int sizeX = 0; sizeX < 5; ++sizeX)
        {
            for (int sizeZ = 0; sizeZ < 5; ++sizeZ)
            {
                float maxSum = 0.0F;
                float minSum = 0.0F;
                float weightSum = 0.0F;
                byte radius = 2;
                BiomeGenBase centerBiome = this.biomesForGeneration[sizeX + 2 + (sizeZ + 2) * 10];

                for (int l1 = -radius; l1 <= radius; ++l1)
                {
                    for (int i2 = -radius; i2 <= radius; ++i2)
                    {
                        BiomeGenBase neighbourBiome = this.biomesForGeneration[sizeX + l1 + 2 + (sizeZ + i2 + 2) * 10];
                        float biomeRoot = neighbourBiome.rootHeight;
                        float biomeHeight = neighbourBiome.heightVariation;

                        // make the world gen occasionally look super wonky
                        if (rand.nextInt(52) == 0 || unpredictableRandom.nextInt(65) == 0) {
                            if (biomeRoot > 0.0F) {
                                biomeRoot = 1.0F + biomeRoot * 2.0F;
                                biomeHeight = 1.0F + biomeHeight * 4.0F;
                            }
                        }

                        float noiseBiome = this.parabolicField[l1 + 2 + (i2 + 2) * 5] / (biomeRoot + 2.0F);

                        if (neighbourBiome.rootHeight > centerBiome.rootHeight)
                        {
                            noiseBiome /= 2.0F;
                        }

                        maxSum += biomeHeight * noiseBiome;
                        minSum += biomeRoot * noiseBiome;
                        weightSum += noiseBiome;
                    }
                }

                maxSum /= weightSum;
                minSum /= weightSum;
                maxSum = maxSum * 0.9F + 0.1F;
                minSum = (minSum * 4.0F - 1.0F) / 8.0F;
                double depthNoiseCalculation = this.depthNoise[index2D] / 8000.0D;

                if (depthNoiseCalculation < 0.0D)
                {
                    depthNoiseCalculation = -depthNoiseCalculation * 0.3D;
                }

                depthNoiseCalculation = depthNoiseCalculation * 3.0D - 2.0D;

                if (depthNoiseCalculation < 0.0D)
                {
                    depthNoiseCalculation /= 2.0D;

                    if (depthNoiseCalculation < -1.0D)
                    {
                        depthNoiseCalculation = -1.0D;
                    }

                    depthNoiseCalculation /= 1.4D;
                    depthNoiseCalculation /= 2.0D;
                }
                else
                {
                    if (depthNoiseCalculation > 1.0D)
                    {
                        depthNoiseCalculation = 1.0D;
                    }

                    depthNoiseCalculation /= 8.0D;
                }

                ++index2D;

                double d13 = (double)minSum;
                double aMaxSum = (double)maxSum;
                d13 += depthNoiseCalculation * 0.2D;
                d13 = d13 * 8.5D / 8.0D;
                double d5 = 8.5D + d13 * 4.0D;

                for (int sizeY = 0; sizeY < 33; ++sizeY)
                {

                    double scaleFactor = 12.0D;

                    if (chunkX > 1200 || chunkZ > 1200) {
                        scaleFactor = 2.0D;
                    } else if (chunkX > 1000 || chunkZ > 1000) {
                        scaleFactor = 4.0D;
                    } else if (chunkX > 850 || chunkZ > 850) {
                        scaleFactor = 6.0D;
                    } else if (chunkX > 650 || chunkZ > 650) {
                        scaleFactor = 8.0D;
                    } else if (chunkX > 450 || chunkZ > 450) {
                        scaleFactor =10.0D;
                    }

                    // reduce randomly perhaps?
                    double falloff = ((double)sizeY - d5) * scaleFactor * 128.0D / 256.0D / aMaxSum;

                    if (falloff < 0.0D)
                    {
                        falloff *= 4.0D;
                    }

                    double low = this.noiseLow[index] / 512.0D; // lower noise
                    double high = this.noiseUpper[index] / 512.0D; //upper noise
                    double selector = (this.noiseSlide[index] / 10.0D + 1.0D) / 2.0D; // val that gets passed

                    double density = MathHelper.denormalizeClamp(low, high, selector) - falloff;


                    if (sizeY > 29)
                    {
                        double d11 = (double)((float)(sizeY - 29) / 3.0F);
                        density = density * (1.0D - d11) + -10.0D * d11;
                    }


                    this.noiseArray[index] = density;
                    ++index;
                }
            }
        }
    }

    /**
     * Checks to see if a chunk exists at x, y
     */
    public boolean chunkExists(int x, int z)
    {
        return true;
    }


    @Override
    public void populate(IChunkProvider chunkProvider, int x, int z)
    {
        BlockFalling.fallInstantly = false;
        int xPosFromChunk = x * 16;
        int zPosFromChunk = z * 16;
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(xPosFromChunk + 16, zPosFromChunk + 16);
        this.rand.setSeed(this.worldObj.getSeed());
        long seedHashX = this.rand.nextLong() / 2L * 2L + 1L;
        long seedHashZ = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)x * seedHashX + (long)z * seedHashZ ^ this.worldObj.getSeed());
        boolean villageGen = false;

        if (unpredictableRandom.nextInt(1400) == 0) {
            this.rand.setSeed(seedHashZ >> seedHashX);

        }

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(chunkProvider, worldObj, rand, x, z, villageGen));

        if (this.mapFeaturesEnabled)
        {
            this.mineshaftGenerator.generateStructuresInChunk(this.worldObj, this.rand, x, z);
            villageGen = this.villageGenerator.generateStructuresInChunk(this.worldObj, this.rand, x, z);
            this.scatteredFeatureGenerator.generateStructuresInChunk(this.worldObj, this.rand, x, z);
        }

        int shiftedX = 0;
        int shiftedY = 0;
        int shiftedZ = 0;
        int randTry = 0;

        if (!villageGen && this.rand.nextInt(4) == 0 || unpredictableRandom.nextInt(4) == 0
                && TerrainGen.populate(chunkProvider, worldObj, rand, x, z, villageGen, LAKE))
        {
            shiftedX = xPosFromChunk + this.rand.nextInt(16) + 8;
            shiftedY = this.rand.nextInt(256);
            shiftedZ = zPosFromChunk + this.rand.nextInt(16) + 8;
            (new WorldGenLakes(Blocks.air)).generate(this.worldObj, this.rand, shiftedX, shiftedY, shiftedZ);
        }

        if (TerrainGen.populate(chunkProvider, worldObj, rand, x, z, villageGen, LAVA) && !villageGen && this.rand.nextInt(8) == 0 || unpredictableRandom.nextInt(8) == 0)
        {
            shiftedX = xPosFromChunk + this.rand.nextInt(16) + 8;
            shiftedY = this.rand.nextInt(this.rand.nextInt(248) + 8);
            shiftedZ = zPosFromChunk + this.rand.nextInt(16) + 8;

            (new WorldGenLakes(Blocks.air)).generate(this.worldObj, this.rand, shiftedX, shiftedY, shiftedZ);
        }




        int tryAmmount = 0;

        for (tryAmmount = 0; tryAmmount < 50; ++tryAmmount)
        {
            shiftedX = xPosFromChunk + this.rand.nextInt(16) + 8;
            shiftedY = this.rand.nextInt(this.rand.nextInt(248) + 8);
            shiftedZ = zPosFromChunk + this.rand.nextInt(16) + 8;
            (new WorldGenLiquids(Blocks.air)).generate(worldObj, rand, shiftedX, shiftedY, shiftedZ);
        }

        for (tryAmmount = 0; tryAmmount < 20; ++tryAmmount)
        {
            shiftedX = xPosFromChunk + this.rand.nextInt(16) + 8;
            shiftedY = this.rand.nextInt(this.rand.nextInt(248) + 8);
            shiftedZ = zPosFromChunk + this.rand.nextInt(16) + 8;
            (new WorldGenLiquids(Blocks.air)).generate(worldObj, rand, shiftedX, shiftedY, shiftedZ);
        }

        boolean doGen = TerrainGen.populate(chunkProvider, worldObj, rand, x, z, villageGen, DUNGEON);
        for (shiftedX = 0; doGen && shiftedX < 8; ++shiftedX)
        {
            shiftedY = xPosFromChunk + this.rand.nextInt(16) + 8;
            shiftedZ = this.rand.nextInt(256);
            int j2 = zPosFromChunk + this.rand.nextInt(16) + 8;
            (new WorldGenDungeons()).generate(this.worldObj, this.rand, shiftedY, shiftedZ, j2);
        }

        if (rand.nextInt(2) == 0)
        {
            shiftedY = xPosFromChunk + this.rand.nextInt(16) + 8;

            int j2 = zPosFromChunk + this.rand.nextInt(16) + 8;
            shiftedZ = worldObj.getTopSolidOrLiquidBlock(shiftedY, j2) - 1;
            (new WorldGenHoles()).generate(this.worldObj, this.rand, shiftedY, shiftedZ, j2);
        }

        if (rand.nextInt(8) == 0 || unpredictableRandom.nextInt(8) == 0)
        {
            shiftedY = xPosFromChunk + this.rand.nextInt(16) + 8;

            int j2 = zPosFromChunk + this.rand.nextInt(16) + 8;
            shiftedZ = worldObj.getTopSolidOrLiquidBlock(shiftedY, j2) -1;
            (new WorldGenBlockBlob(Blocks.air, 5)).generate(this.worldObj, this.rand, shiftedY, shiftedZ, j2);
        }

        if (rand.nextInt(16) == 0 || unpredictableRandom.nextInt(16) == 0)
        {
            shiftedY = xPosFromChunk + this.rand.nextInt(16) + 8;

            int j2 = zPosFromChunk + this.rand.nextInt(16) + 8;
            shiftedZ = worldObj.getTopSolidOrLiquidBlock(shiftedY, j2) - 2;
            (new WorldGenHellLavaPain(Blocks.air)).generate(this.worldObj, this.rand, shiftedY, shiftedZ, j2);
        }

        if (rand.nextInt(24) == 0 || unpredictableRandom.nextInt(38) == 0) {
            shiftedY = xPosFromChunk + this.rand.nextInt(16) + 8;

            int j2 = zPosFromChunk + this.rand.nextInt(16) + 8;
            shiftedZ = worldObj.getTopSolidOrLiquidBlock(shiftedY, j2) - 2;

            (new WorldGenClayPain(12 + rand.nextInt(3))).generate(worldObj, rand, shiftedY, shiftedZ, j2);

        }


        biomegenbase.decorate(this.worldObj, this.rand, xPosFromChunk, zPosFromChunk);
        if (TerrainGen.populate(chunkProvider, worldObj, rand, x, z, villageGen, ANIMALS))
        {
            PainSpawner.performWorldGenSpawning(this.worldObj, biomegenbase, xPosFromChunk + 8, zPosFromChunk + 8, 16, 16, this.rand);
            SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomegenbase, xPosFromChunk + 8, zPosFromChunk + 8, 16, 16, this.rand);
        }


        xPosFromChunk += 8;
        zPosFromChunk += 8;






        doGen = TerrainGen.populate(chunkProvider, worldObj, rand, x, z, villageGen, ICE);
        for (shiftedX = 0; doGen && shiftedX < 16; ++shiftedX)
        {
            for (shiftedY = 0; shiftedY < 16; ++shiftedY)
            {
                shiftedZ = this.worldObj.getPrecipitationHeight(xPosFromChunk + shiftedX, zPosFromChunk + shiftedY);

                if (this.worldObj.isBlockFreezable(shiftedX + xPosFromChunk, shiftedZ - 1, shiftedY + zPosFromChunk))
                {
                    this.worldObj.setBlock(shiftedX + xPosFromChunk, shiftedZ - 1, shiftedY + zPosFromChunk, Blocks.ice, 0, 2);
                }

                if (this.worldObj.func_147478_e(shiftedX + xPosFromChunk, shiftedZ, shiftedY + zPosFromChunk, true))
                {
                    this.worldObj.setBlock(shiftedX + xPosFromChunk, shiftedZ, shiftedY + zPosFromChunk, Blocks.snow_layer, 0, 2);
                }
            }
        }


        // x like pattern
        if (rand.nextInt(40) == 0)
        {
            for (int i = 0; i < erosionNoise.length; i++) {



                double er = erosionNoise[i];



                int yp3 = worldObj.getTopSolidOrLiquidBlock(shiftedX,shiftedZ) - 1;

                int erI = (int) er;


                Share.LOG.info(erI);




                int px = shiftedX + erI;
                int pz = shiftedZ + erI;
                int pxm = shiftedX - erI;
                int pzm = shiftedZ - erI;

                //TODO: REPURPOSE WORLDGenLakes Algorithm


                lakeErosion(shiftedX, yp3, shiftedZ);





                // blob
                // perhaps randomly select in this in addition to the X pattern stage?
                if (er > 7 || er < -7) {
                    int yp = worldObj.getTopSolidOrLiquidBlock(shiftedX,shiftedZ) - 1;

                    int k2 = 5; // adjust prob?

                    for (int l = 0; k2 >= 0 && l < 3; ++l)
                    {
                        int i1 = k2 + rand.nextInt(2);
                        int j1 = k2 + rand.nextInt(2);
                        int k1 = k2 + rand.nextInt(2);
                        float f = (float)(i1 + j1 + k1) * 0.333F + 0.5F;

                        for (int l1 = x - i1; l1 <= x + i1; ++l1)
                        {
                            for (int i2 = z - k1; i2 <= z + k1; ++i2)
                            {
                                for (int j2 = yp - j1; j2 <= yp + j1; ++j2)
                                {
                                    float f1 = (float)(l1 - x);
                                    float f2 = (float)(i2 - z);
                                    float f3 = (float)(j2 - yp);

                                    if (f1 * f1 + f2 * f2 + f3 * f3 <= f * f)
                                    {
                                        ACLUtils.ensureBlockExists(worldObj, l1, j2,i2);
                                        worldObj.setBlock(l1, j2, i2, Blocks.air, 0, 4);
                                    }
                                }
                            }
                        }

                        x += -(k2 + 1) + rand.nextInt(2 + k2 * 2);
                        z += -(k2 + 1) + rand.nextInt(2 + k2 * 2);
                        yp += 0 - rand.nextInt(2);
                    }



                }

                int yp2 = worldObj.getTopSolidOrLiquidBlock(px * 2, pz * 2) - 1;
                int yp4 = worldObj.getTopSolidOrLiquidBlock(-pxm * 2, -pzm * 2) - 1;
                int y5 = worldObj.getTopSolidOrLiquidBlock(px * 2, -pzm * 2) - 1;
                int y6 = worldObj.getTopSolidOrLiquidBlock(-pxm * 2, pz * 2) - 1;



                Share.LOG.info("/tp PainModDevAccount" + " " + px * 2 + " " + yp2 + " " + pz * 2);

                ACLUtils.ensureBlockExists(worldObj, px * 2, yp2, pz * 2);

                worldObj.setBlock(px * 2, yp2, pz* 2, Blocks.air, 0 , 2);




                ACLUtils.ensureBlockExists(worldObj, -pxm * 2, yp4, -pzm * 2);

                worldObj.setBlock(-pxm * 2, yp4, -pzm * 2, Blocks.air, 0 , 2);








                ACLUtils.ensureBlockExists(worldObj, px * 2, y5, -pzm * 2);

                worldObj.setBlock(px * 2, y5, -pzm * 2, Blocks.air, 0 , 2);




                ACLUtils.ensureBlockExists(worldObj, -pxm * 2, y6, pz * 2);

                worldObj.setBlock(-pxm * 2, y6, pz * 2, Blocks.air, 0 , 2);


            }





        }


        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(chunkProvider, worldObj, rand, x, z, villageGen));

        BlockFalling.fallInstantly = false;
    }

    private void lakeErosion(int x, int y, int z) {
        int l = rand.nextInt(4) + 4;
        int i1;

        for (i1 = 0; i1 < l; ++i1)
        {
            double d0 = rand.nextDouble() * 6.0D + 3.0D;
            double d1 = rand.nextDouble() * 4.0D + 2.0D;
            double d2 = rand.nextDouble() * 6.0D + 3.0D;
            double d3 = rand.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
            double d4 = rand.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
            double d5 = rand.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

            for (int k1 = 1; k1 < 15; ++k1)
            {
                for (int l1 = 1; l1 < 15; ++l1)
                {
                    for (int i2 = 1; i2 < 7; ++i2)
                    {
                        double d6 = ((double)k1 - d3) / (d0 / 2.0D);
                        double d7 = ((double)i2 - d4) / (d1 / 2.0D);
                        double d8 = ((double)l1 - d5) / (d2 / 2.0D);
                        double d9 = d6 * d6 + d7 * d7 + d8 * d8;


                        if (d9 <= 10.0) {
                            ACLUtils.ensureBlockExists(worldObj, (int) (x + d6), (int) (y + d7), (int) (z + d8));
                            worldObj.setBlock((int) (x + d6), (int) (y + d7), (int) (z + d8), Blocks.quartz_block, 0, 2);

                            ACLUtils.ensureBlockExists(worldObj,(int) (x + d6), (int) (y + d7), (int) (z - d8));
                            worldObj.setBlock((int) (x + d6), (int) (y + d7), (int) (z - d8), Blocks.quartz_block, 0, 2);

                            ACLUtils.ensureBlockExists(worldObj,(int) (x - d6), (int) (y + d7), (int) (z + d8));
                            worldObj.setBlock((int) (x - d6), (int) (y + d7), (int) (z + d8), Blocks.quartz_block, 0, 2);

                            ACLUtils.ensureBlockExists(worldObj, (int) (x + d6), (int) (y - d7), (int) (z + d8));
                            worldObj.setBlock((int) (x + d6), (int) (y - d7), (int) (z + d8), Blocks.quartz_block, 0, 2);

                            ACLUtils.ensureBlockExists(worldObj, (int) (x + d6), (int) (y - d7), (int) (z - d8));
                            worldObj.setBlock((int) (x + d6), (int) (y - d7), (int) (z - d8), Blocks.quartz_block, 0, 2);

                            ACLUtils.ensureBlockExists(worldObj, (int) (x - d6), (int) (y - d7), (int) (z + d8));
                            worldObj.setBlock((int) (x - d6), (int) (y - d7), (int) (z + d8), Blocks.quartz_block, 0, 2);
                        }
                    }
                }
            }
        }

    }



    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    public boolean saveChunks(boolean saveAllChunks, IProgressUpdate progressCallback)
    {
        return true;
    }

    /**
     * Save extra data not associated with any Chunk.  Not saved during autosave, only during world unload.  Currently
     * unimplemented.
     */
    public void saveExtraData() {}

    /**
     * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
     */
    public boolean unloadQueuedChunks()
    {
        return false;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    public boolean canSave()
    {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    @Override
    public String makeString()
    {
        return "PainMod_RandomLevelSource";
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    @Override
    public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_)
    {
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(p_73155_2_, p_73155_4_);
        return p_73155_1_ == EnumCreatureType.monster && this.scatteredFeatureGenerator.func_143030_a(p_73155_2_, p_73155_3_, p_73155_4_) ? this.scatteredFeatureGenerator.getScatteredFeatureSpawnList() : biomegenbase.getSpawnableList(p_73155_1_);
    }

    @Override
    public ChunkPosition func_147416_a(World world, String featureName, int startX, int startY, int startZ)
    {
        return null;
    }

    @Override
    public int getLoadedChunkCount()
    {
        return 0;
    }

    @Override
    public void recreateStructures(int p_82695_1_, int p_82695_2_)
    {
        if (this.mapFeaturesEnabled)
        {
            this.mineshaftGenerator.func_151539_a(this, this.worldObj, p_82695_1_, p_82695_2_, (Block[])null);
            this.villageGenerator.func_151539_a(this, this.worldObj, p_82695_1_, p_82695_2_, (Block[])null);
            this.scatteredFeatureGenerator.func_151539_a(this, this.worldObj, p_82695_1_, p_82695_2_, (Block[])null);
        }
    }



}