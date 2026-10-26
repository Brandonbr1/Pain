package jerios.painmod.world.repackage.layer;

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
public class GenLayerFuzzyZoom164 extends GenLayer {

    public GenLayerFuzzyZoom164(long seed, GenLayer parent) {
        super(seed);
        this.parent = parent;
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int parentX = areaX >> 1;
        int parentY = areaY >> 1;
        int parentWidth = (areaWidth >> 1) + 3;
        int parentHeight = (areaHeight >> 1) + 3;
        int[] parentInts = this.parent.getInts(parentX, parentY, parentWidth, parentHeight);
        int[] zoomed = IntCache.getIntCache(parentWidth * 2 * parentHeight * 2);
        int zoomedWidth = parentWidth << 1;

        for (int py = 0; py < parentHeight - 1; ++py) {
            int index = (py << 1) * zoomedWidth;
            int topLeft = parentInts[0 + (py + 0) * parentWidth];
            int bottomLeft = parentInts[0 + (py + 1) * parentWidth];

            for (int px = 0; px < parentWidth - 1; ++px) {
                this.initChunkSeed((px + parentX) << 1, (py + parentY) << 1);
                int topRight = parentInts[px + 1 + (py + 0) * parentWidth];
                int bottomRight = parentInts[px + 1 + (py + 1) * parentWidth];
                zoomed[index] = topLeft;
                zoomed[index++ + zoomedWidth] = this.choose(topLeft, bottomLeft);
                zoomed[index] = this.choose(topLeft, topRight);
                zoomed[index++ + zoomedWidth] = this.choose(topLeft, topRight, bottomLeft, bottomRight);
                topLeft = topRight;
                bottomLeft = bottomRight;
            }
        }

        int[] out = IntCache.getIntCache(areaWidth * areaHeight);

        for (int y = 0; y < areaHeight; ++y) {
            System.arraycopy(zoomed, (y + (areaY & 1)) * zoomedWidth + (areaX & 1), out, y * areaWidth, areaWidth);
        }

        return out;
    }

    protected int choose(int a, int b) {
        return this.nextInt(2) == 0 ? a : b;
    }

    protected int choose(int a, int b, int c, int d) {
        int r = this.nextInt(4);
        return r == 0 ? a : (r == 1 ? b : (r == 2 ? c : d));
    }
}
