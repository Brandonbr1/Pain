package jerios.painmod.world.repackage.layer;

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
public class GenLayerHills164 extends GenLayer {

    public GenLayerHills164(long seed, GenLayer parent) {
        super(seed);
        this.parent = parent;
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int[] parentInts = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
        int[] out = IntCache.getIntCache(areaWidth * areaHeight);
        int parentWidth = areaWidth + 2;

        for (int y = 0; y < areaHeight; ++y) {
            for (int x = 0; x < areaWidth; ++x) {
                this.initChunkSeed(x + areaX, y + areaY);
                int center = parentInts[x + 1 + (y + 1) * parentWidth];

                if (this.nextInt(3) == 0) {
                    int hills = center;

                    if (center == BiomeGenBase.desert.biomeID) {
                        hills = BiomeGenBase.desertHills.biomeID;
                    } else if (center == BiomeGenBase.forest.biomeID) {
                        hills = BiomeGenBase.forestHills.biomeID;
                    } else if (center == BiomeGenBase.taiga.biomeID) {
                        hills = BiomeGenBase.taigaHills.biomeID;
                    } else if (center == BiomeGenBase.plains.biomeID) {
                        hills = BiomeGenBase.forest.biomeID;
                    } else if (center == BiomeGenBase.icePlains.biomeID) {
                        hills = BiomeGenBase.iceMountains.biomeID;
                    } else if (center == BiomeGenBase.jungle.biomeID) {
                        hills = BiomeGenBase.jungleHills.biomeID;
                    }

                    if (hills == center) {
                        out[x + y * areaWidth] = center;
                    } else {
                        int north = parentInts[x + 1 + (y + 1 - 1) * parentWidth];
                        int east = parentInts[x + 1 + 1 + (y + 1) * parentWidth];
                        int west = parentInts[x + 1 - 1 + (y + 1) * parentWidth];
                        int south = parentInts[x + 1 + (y + 1 + 1) * parentWidth];

                        if (north == center && east == center && west == center && south == center) {
                            out[x + y * areaWidth] = hills;
                        } else {
                            out[x + y * areaWidth] = center;
                        }
                    }
                } else {
                    out[x + y * areaWidth] = center;
                }
            }
        }

        return out;
    }
}
