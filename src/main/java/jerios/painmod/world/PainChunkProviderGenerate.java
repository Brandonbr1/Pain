package jerios.painmod.world;

import cpw.mods.fml.common.eventhandler.Event;
import jerios.painmod.world.features.WorldGenHoles;
import jerios.painmod.world.mapGen.PainMapGenCaves;
import jerios.painmod.world.mapGen.PainMapGenRavine;
import jerios.painmod.world.spawners.PainSpawner;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
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

import java.util.List;
import java.util.Random;

import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.*;

public class PainChunkProviderGenerate implements IChunkProvider {

    private Random rand;
    private NoiseGeneratorOctaves noiseGenLow; // lower noise
    private NoiseGeneratorOctaves noiseGenUpper; // upper noise
    private NoiseGeneratorOctaves noiseGenSlide;
    public NoiseGeneratorOctaves noiseGenStone;
    public NoiseGeneratorOctaves noiseGenDepth;

    public NoiseGeneratorOctaves noiseGenErosion;

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

    public PainChunkProviderGenerate(World world, long seed, boolean mapFeatures)
    {
        this.worldObj = world;
        this.mapFeaturesEnabled = mapFeatures;
        this.rand = new Random(seed);


        this.noiseGenLow = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGenUpper = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGenSlide = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseGenStone = new NoiseGeneratorOctaves(rand, 4);
        this.noiseGenDepth = new NoiseGeneratorOctaves(this.rand, 10);



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
                                    blocks[depth += maxDepth] = Blocks.stone;
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

        if (rand.nextInt(80) == 0) {
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
        this.erosionNoise = noiseGenErosion.generateNoiseOctaves(this.erosionNoise,x,y,z,8,5,8,scale3,scale3,scale3);


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
                        if (rand.nextInt(52) == 0 && biomeRoot > 0.0F)
                        {
                            biomeRoot = 1.0F + biomeRoot * 2.0F;
                            biomeHeight = 1.0F + biomeHeight * 4.0F;
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
                double d14 = (double)maxSum;
                d13 += depthNoiseCalculation * 0.2D;
                d13 = d13 * 8.5D / 8.0D;
                double d5 = 8.5D + d13 * 4.0D;

                for (int sizeY = 0; sizeY < 33; ++sizeY)
                {

                    double scaleFactor = 12.0D;

                    if (chunkX > 200 || chunkZ > 200) {
                        scaleFactor = 2.0D;
                    } else if (chunkX > 100 || chunkZ > 100) {
                        scaleFactor = 4.0D;
                    } else if (chunkX > 80 || chunkZ > 80) {
                        scaleFactor = 6.0D;
                    } else if (chunkX > 40 || chunkZ > 40) {
                        scaleFactor = 8.0D;
                    } else if (chunkX > 20 || chunkZ > 20) {
                        scaleFactor =10.0D;
                    }

                    double falloff = ((double)sizeY - d5) * scaleFactor * 128.0D / 256.0D / d14;

                    if (falloff < 0.0D)
                    {
                        falloff *= 4.0D;
                    }

                    double low = this.noiseLow[index] / 512.0D; // lower noise
                    double high = this.noiseUpper[index] / 512.0D; //upper noise
                    double selector = (this.noiseSlide[index] / 10.0D + 1.0D) / 2.0D; // val that gets passed

                    // reduce clamp randomly?
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

        if (!villageGen && this.rand.nextInt(4) == 0
                && TerrainGen.populate(chunkProvider, worldObj, rand, x, z, villageGen, LAKE))
        {
            shiftedX = xPosFromChunk + this.rand.nextInt(16) + 8;
            shiftedY = this.rand.nextInt(256);
            shiftedZ = zPosFromChunk + this.rand.nextInt(16) + 8;
            (new WorldGenLakes(Blocks.air)).generate(this.worldObj, this.rand, shiftedX, shiftedY, shiftedZ);
        }

        if (TerrainGen.populate(chunkProvider, worldObj, rand, x, z, villageGen, LAVA) && !villageGen && this.rand.nextInt(8) == 0)
        {
            shiftedX = xPosFromChunk + this.rand.nextInt(16) + 8;
            shiftedY = this.rand.nextInt(this.rand.nextInt(248) + 8);
            shiftedZ = zPosFromChunk + this.rand.nextInt(16) + 8;

            (new WorldGenLakes(Blocks.air)).generate(this.worldObj, this.rand, shiftedX, shiftedY, shiftedZ);

        }


        int tryAmmount;

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
            shiftedZ = worldObj.getTopSolidOrLiquidBlock(shiftedY, j2);
            (new WorldGenHoles()).generate(this.worldObj, this.rand, shiftedY, shiftedZ, j2);

        }

        biomegenbase.decorate(this.worldObj, this.rand, xPosFromChunk, zPosFromChunk);
        if (TerrainGen.populate(chunkProvider, worldObj, rand, x, z, villageGen, ANIMALS))
        {
            PainSpawner.performWorldGenSpawning(this.worldObj, biomegenbase, xPosFromChunk + 8, zPosFromChunk + 8, 16, 16, this.rand);
            SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomegenbase, xPosFromChunk + 8, zPosFromChunk + 8, 16, 16, this.rand);
        }


        int var25 = tryAmmount;
        int var4 = xPosFromChunk;
        int var5 = zPosFromChunk;
        int var19 = shiftedX;
        int var20 = shiftedY;
        int var21 = shiftedZ;


        for(var25 = var4 + 8; var25 < var4 + 8 + 16; ++var25) {
            for(var19 = var5 + 8; var19 < var5 + 8 + 16; ++var19) {
                var20 = var25 - (var4 + 8);
                var21 = var19 - (var5 + 8);
                int var22 = this.worldObj.getTopSolidOrLiquidBlock(var25, var19);
                double var23 = this.erosionNoise[var20 * 16 + var21] - (double)(var22 - 64) / 64.0D * 0.3D;
                System.out.println("NOISE   "+var23);
                if (var23 < 0.5D && var22 > 0 && var22 < 128 && this.worldObj.isAirBlock(var25, var22, var19) && this.worldObj.getBlock(var25, var22 - 1, var19).isOpaqueCube() && this.worldObj.getBlock(var25, var22 - 1, var19).getMaterial() != Material.ice) {
                    this.worldObj.setBlock(var25, var22, var19, Blocks.air, 0, 2);
                }
            }
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


        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(chunkProvider, worldObj, rand, x, z, villageGen));

        BlockFalling.fallInstantly = false;
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

    public int getLoadedChunkCount()
    {
        return 0;
    }

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