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
        boolean flag = true;
        Block top = this.topBlock;
        byte meta = (byte)(this.field_150604_aj & 255);
        Block filler = this.fillerBlock;
        int k = -1;
        int noiseCalc = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int colXChunk = columnX & 15;
        int colZChunk = columnZ & 15;
        int blocksChunk = blocks.length / 256;

        for (int l1 = 255; l1 >= 0; --l1)
        {
            int i2 = (colZChunk * 16 + colXChunk) * blocksChunk + l1;

            if (l1 <= 0 + random.nextInt(5))
            {
                blocks[i2] = Blocks.bedrock;
            }
            else
            {
                Block block2 = blocks[i2];

                if (block2 != null && block2.getMaterial() != Material.air)
                {
                    if (block2 == ModItemBlocks.painStone)
                    {
                        if (k == -1)
                        {
                            if (noiseCalc <= 0)
                            {
                                top = null;
                                meta = 0;
                                filler = ModItemBlocks.painStone;

                                if (random.nextInt(9) == 0) {
                                    filler = Blocks.air;
                                }
                            }
                            else if (l1 >= 59 && l1 <= 64)
                            {
                                top = this.topBlock;
                                meta = (byte)(this.field_150604_aj & 255);
                                filler = this.fillerBlock;

                                if (random.nextInt(9) == 0) {
                                    top = Blocks.air;
                                    filler = Blocks.air;
                                }

                            }

                            if (l1 < 63 && (top == null || top.getMaterial() == Material.air))
                            {
                                if (this.getFloatTemperature(columnX, l1, columnZ) < 0.15F)
                                {
                                    top = Blocks.ice;
                                    meta = 0;

                                    if (random.nextInt(9) == 0) {
                                        top = Blocks.air;
                                    }
                                }
                                else
                                {
                                    top = Blocks.water;
                                    meta = 0;

                                    if (random.nextInt(9) == 0) {
                                        top = Blocks.air;
                                    }
                                }
                            }

                            k = noiseCalc;

                            if (l1 >= 62)
                            {
                                blocks[i2] = top;
                                metas[i2] = meta;
                            }
                            else if (l1 < 56 - noiseCalc)
                            {
                                top = null;
                                filler = ModItemBlocks.painStone;
                                blocks[i2] = Blocks.gravel;
                            }
                            else
                            {
                                blocks[i2] = filler;
                            }
                        }
                        else if (k > 0)
                        {
                            --k;
                            blocks[i2] = filler;

                            if (k == 0 && filler == Blocks.sand)
                            {
                                k = random.nextInt(4) + Math.max(0, l1 - 63);
                                filler = Blocks.sandstone;


                                if (random.nextInt(9) == 0) {
                                    filler = Blocks.air;
                                }

                            }
                        }
                    }
                }
                else
                {
                    k = -1;
                }
            }
        }
    }


}
