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
public class GenLayerShore164 extends GenLayer {

    public GenLayerShore164(long seed, GenLayer parent) {
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
                int north;
                int east;
                int west;
                int south;

                if (center == BiomeGenBase.mushroomIsland.biomeID) {
                    north = parentInts[x + 1 + (y + 1 - 1) * parentWidth];
                    east = parentInts[x + 1 + 1 + (y + 1) * parentWidth];
                    west = parentInts[x + 1 - 1 + (y + 1) * parentWidth];
                    south = parentInts[x + 1 + (y + 1 + 1) * parentWidth];

                    if (north != BiomeGenBase.ocean.biomeID && east != BiomeGenBase.ocean.biomeID
                        && west != BiomeGenBase.ocean.biomeID
                        && south != BiomeGenBase.ocean.biomeID) {
                        out[x + y * areaWidth] = center;
                    } else {
                        out[x + y * areaWidth] = BiomeGenBase.mushroomIslandShore.biomeID;
                    }
                } else if (center != BiomeGenBase.ocean.biomeID && center != BiomeGenBase.river.biomeID
                    && center != BiomeGenBase.swampland.biomeID
                    && center != BiomeGenBase.extremeHills.biomeID) {
                        north = parentInts[x + 1 + (y + 1 - 1) * parentWidth];
                        east = parentInts[x + 1 + 1 + (y + 1) * parentWidth];
                        west = parentInts[x + 1 - 1 + (y + 1) * parentWidth];
                        south = parentInts[x + 1 + (y + 1 + 1) * parentWidth];

                        if (north != BiomeGenBase.ocean.biomeID && east != BiomeGenBase.ocean.biomeID
                            && west != BiomeGenBase.ocean.biomeID
                            && south != BiomeGenBase.ocean.biomeID) {
                            out[x + y * areaWidth] = center;
                        } else {
                            out[x + y * areaWidth] = BiomeGenBase.beach.biomeID;
                        }
                    } else if (center == BiomeGenBase.extremeHills.biomeID) {
                        north = parentInts[x + 1 + (y + 1 - 1) * parentWidth];
                        east = parentInts[x + 1 + 1 + (y + 1) * parentWidth];
                        west = parentInts[x + 1 - 1 + (y + 1) * parentWidth];
                        south = parentInts[x + 1 + (y + 1 + 1) * parentWidth];

                        if (north == BiomeGenBase.extremeHills.biomeID && east == BiomeGenBase.extremeHills.biomeID
                            && west == BiomeGenBase.extremeHills.biomeID
                            && south == BiomeGenBase.extremeHills.biomeID) {
                            out[x + y * areaWidth] = center;
                        } else {
                            out[x + y * areaWidth] = BiomeGenBase.extremeHillsEdge.biomeID;
                        }
                    } else {
                        out[x + y * areaWidth] = center;
                    }
            }
        }

        return out;
    }
}
