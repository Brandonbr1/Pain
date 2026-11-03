package jerios.painmod.world.biome;

import jerios.painmod.Share;
import jerios.painmod.registry.ModItemBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public class PainBiomeGen extends BiomeGenBase {


    public PainBiomeGen(int id) {
        super(id);
    }

    public PainBiomeGen(int id, boolean register) {
        super(id, register);
    }

    public void genTerrainBlocks(World world, Random random, Block[] blocks, byte[] metas, int columnX, int columnZ, double noise)
    {
        this.genBiomeTerrain2(world, random, blocks, metas, columnX, columnZ, noise);
    }

    public final void genBiomeTerrain2(World world, Random random, Block[] blocks, byte[] metas, int columnX, int columnZ, double noise)
    {
        Block topBlock = this.topBlock;
        byte metadata = (byte)(this.field_150604_aj & 255);
        Block fillerBlock = this.fillerBlock;
        int noiseIndex = -1;
        int noiseCalculations = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int chunkColumnX = columnX & 15;
        int chunkColumnZ = columnZ & 15;
        int blockListChunk = blocks.length / 256;

        for (int height = 255; height >= 0; --height)
        {
            int blockInChunk = (chunkColumnZ * 16 + chunkColumnX) * blockListChunk + height;

            if (height <= 0 + random.nextInt(5))
            {
                blocks[blockInChunk] = Blocks.bedrock;
            }
            else
            {
                Block blockAboveBedrockSpawning = blocks[blockInChunk];

                if (blockAboveBedrockSpawning != null && blockAboveBedrockSpawning.getMaterial() != Material.air)
                {
                    if (blockAboveBedrockSpawning == ModItemBlocks.painStone)
                    {
                        if (noiseIndex == -1)
                        {
                            if (noiseCalculations <= 0)
                            {
                                topBlock = null;
                                metadata = 0;
                                fillerBlock = ModItemBlocks.painStone;

                                if (random.nextInt(9) == 0) {
                                    fillerBlock = Blocks.air;
                                }

                            }
                            else if (height >= 59 && height <= 64)
                            {
                                topBlock = this.topBlock;
                                metadata = (byte)(this.field_150604_aj & 255);
                                fillerBlock = this.fillerBlock;

                                if (random.nextInt(9) == 0) {
                                    topBlock = Blocks.air;
                                    fillerBlock = Blocks.air;
                                    metadata = 0;
                                }
                            }

                            if (height < 63 && (topBlock == null || topBlock.getMaterial() == Material.air))
                            {
                                if (this.getFloatTemperature(columnX, height, columnZ) < 0.15F)
                                {
                                    topBlock = Blocks.ice;
                                    if (random.nextInt(9) == 0) {
                                        topBlock = Blocks.air;
                                    }
                                    metadata = 0;
                                }
                                else
                                {
                                    topBlock = Blocks.water;
                                    if (random.nextInt(9) == 0) {
                                        topBlock = Blocks.air;
                                    }
                                    metadata = 0;
                                }
                            }

                            noiseIndex = noiseCalculations;

                            if (height >= 62)
                            {
                                blocks[blockInChunk] = topBlock;
                                metas[blockInChunk] = metadata;

                                if (random.nextInt(9) == 0) {
                                    blocks[blockInChunk] = Blocks.air;
                                    metas[blockInChunk] = 0;
                                }
                            }
                            else if (height < 56 - noiseCalculations)
                            {
                                topBlock = null;
                                fillerBlock = ModItemBlocks.painStone;
                                blocks[blockInChunk] = Blocks.gravel;

                                if (random.nextInt(9) == 0) {
                                    fillerBlock = Blocks.air;
                                    blocks[blockInChunk] = Blocks.air;
                                }
                            }
                            else
                            {
                                blocks[blockInChunk] = fillerBlock;
                            }
                        }
                        else if (noiseIndex > 0)
                        {
                            --noiseIndex;
                            blocks[blockInChunk] = fillerBlock;

                            if (noiseIndex == 0 && fillerBlock == Blocks.sand)
                            {
                                noiseIndex = random.nextInt(4) + Math.max(0, height - 63);
                                fillerBlock = Blocks.sandstone;


                                if (random.nextInt(9) == 0) {
                                    fillerBlock = Blocks.air;
                                }


                            }
                        }
                    }
                }
                else
                {
                    noiseIndex = -1;
                }
            }
        }
    }


}
