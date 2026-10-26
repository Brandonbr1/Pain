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
public class GenLayerAddIsland164 extends GenLayer {

    public GenLayerAddIsland164(long seed, GenLayer parent) {
        super(seed);
        this.parent = parent;
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int parentX = areaX - 1;
        int parentY = areaY - 1;
        int parentWidth = areaWidth + 2;
        int parentHeight = areaHeight + 2;
        int[] parentInts = this.parent.getInts(parentX, parentY, parentWidth, parentHeight);
        int[] out = IntCache.getIntCache(areaWidth * areaHeight);

        for (int y = 0; y < areaHeight; ++y) {
            for (int x = 0; x < areaWidth; ++x) {
                int topLeft = parentInts[x + 0 + (y + 0) * parentWidth];
                int topRight = parentInts[x + 2 + (y + 0) * parentWidth];
                int bottomLeft = parentInts[x + 0 + (y + 2) * parentWidth];
                int bottomRight = parentInts[x + 2 + (y + 2) * parentWidth];
                int center = parentInts[x + 1 + (y + 1) * parentWidth];
                this.initChunkSeed(x + areaX, y + areaY);

                if (center == 0 && (topLeft != 0 || topRight != 0 || bottomLeft != 0 || bottomRight != 0)) {
                    int chance = 1;
                    int picked = 1;

                    if (topLeft != 0 && this.nextInt(chance++) == 0) {
                        picked = topLeft;
                    }

                    if (topRight != 0 && this.nextInt(chance++) == 0) {
                        picked = topRight;
                    }

                    if (bottomLeft != 0 && this.nextInt(chance++) == 0) {
                        picked = bottomLeft;
                    }

                    if (bottomRight != 0 && this.nextInt(chance++) == 0) {
                        picked = bottomRight;
                    }

                    if (this.nextInt(3) == 0) {
                        out[x + y * areaWidth] = picked;
                    } else if (picked == BiomeGenBase.icePlains.biomeID) {
                        out[x + y * areaWidth] = BiomeGenBase.frozenOcean.biomeID;
                    } else {
                        out[x + y * areaWidth] = 0;
                    }
                } else if (center > 0 && (topLeft == 0 || topRight == 0 || bottomLeft == 0 || bottomRight == 0)) {
                    if (this.nextInt(5) == 0) {
                        if (center == BiomeGenBase.icePlains.biomeID) {
                            out[x + y * areaWidth] = BiomeGenBase.frozenOcean.biomeID;
                        } else {
                            out[x + y * areaWidth] = 0;
                        }
                    } else {
                        out[x + y * areaWidth] = center;
                    }
                } else {
                    out[x + y * areaWidth] = center;
                }
            }
        }

        return out;
    }
}
