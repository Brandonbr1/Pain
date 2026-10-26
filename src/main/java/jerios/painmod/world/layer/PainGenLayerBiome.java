package jerios.painmod.world.layer;

import jerios.painmod.registry.ModBiomes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

/**
 *
 Copyright (c) 2026 Sxilverrr

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
public class PainGenLayerBiome extends GenLayer {


    public PainGenLayerBiome(long seed, GenLayer parent) {
        super(seed);
        this.parent = parent;
    }


    private final BiomeGenBase[] allowedBiomes = {ModBiomes.painPlains};

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int[] parentInts = this.parent.getInts(areaX, areaY, areaWidth, areaHeight);
        int[] out = IntCache.getIntCache(areaWidth * areaHeight);

        for (int y = 0; y < areaHeight; ++y) {
            for (int x = 0; x < areaWidth; ++x) {
                this.initChunkSeed(x + areaX, y + areaY);
                int value = parentInts[x + y * areaWidth];

                if (value == 0) {
                    out[x + y * areaWidth] = 0;
                } else if (value == BiomeGenBase.mushroomIsland.biomeID) {
                    out[x + y * areaWidth] = value;
                } else if (value == 1) {
                    out[x + y * areaWidth] = this.allowedBiomes[this.nextInt(this.allowedBiomes.length)].biomeID;
                } else {
                    int biome = this.allowedBiomes[this.nextInt(this.allowedBiomes.length)].biomeID;

                    if (biome == BiomeGenBase.taiga.biomeID) {
                        out[x + y * areaWidth] = biome;
                    } else {
                        out[x + y * areaWidth] = BiomeGenBase.icePlains.biomeID;
                    }
                }
            }
        }

        return out;
    }


}
