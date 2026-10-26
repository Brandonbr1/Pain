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
public class GenLayerVoronoiZoom164 extends GenLayer {

    public GenLayerVoronoiZoom164(long seed, GenLayer parent) {
        super(seed);
        this.parent = parent;
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        areaX -= 2;
        areaY -= 2;
        int shift = 2;
        int cell = 1 << shift;
        int parentX = areaX >> shift;
        int parentY = areaY >> shift;
        int parentWidth = (areaWidth >> shift) + 3;
        int parentHeight = (areaHeight >> shift) + 3;
        int[] parentInts = this.parent.getInts(parentX, parentY, parentWidth, parentHeight);
        int zoomedWidth = parentWidth << shift;
        int zoomedHeight = parentHeight << shift;
        int[] zoomed = IntCache.getIntCache(zoomedWidth * zoomedHeight);

        for (int py = 0; py < parentHeight - 1; ++py) {
            int topLeft = parentInts[0 + (py + 0) * parentWidth];
            int bottomLeft = parentInts[0 + (py + 1) * parentWidth];

            for (int px = 0; px < parentWidth - 1; ++px) {
                double scale = (double) cell * 0.9D;
                this.initChunkSeed((px + parentX) << shift, (py + parentY) << shift);
                double topLeftX = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * scale;
                double topLeftZ = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * scale;
                this.initChunkSeed((px + parentX + 1) << shift, (py + parentY) << shift);
                double topRightX = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * scale + (double) cell;
                double topRightZ = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * scale;
                this.initChunkSeed((px + parentX) << shift, (py + parentY + 1) << shift);
                double bottomLeftX = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * scale;
                double bottomLeftZ = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * scale + (double) cell;
                this.initChunkSeed((px + parentX + 1) << shift, (py + parentY + 1) << shift);
                double bottomRightX = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * scale + (double) cell;
                double bottomRightZ = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * scale + (double) cell;
                int topRight = parentInts[px + 1 + (py + 0) * parentWidth];
                int bottomRight = parentInts[px + 1 + (py + 1) * parentWidth];

                for (int cz = 0; cz < cell; ++cz) {
                    int index = ((py << shift) + cz) * zoomedWidth + (px << shift);

                    for (int cx = 0; cx < cell; ++cx) {
                        double distTopLeft = ((double) cz - topLeftZ) * ((double) cz - topLeftZ)
                            + ((double) cx - topLeftX) * ((double) cx - topLeftX);
                        double distTopRight = ((double) cz - topRightZ) * ((double) cz - topRightZ)
                            + ((double) cx - topRightX) * ((double) cx - topRightX);
                        double distBottomLeft = ((double) cz - bottomLeftZ) * ((double) cz - bottomLeftZ)
                            + ((double) cx - bottomLeftX) * ((double) cx - bottomLeftX);
                        double distBottomRight = ((double) cz - bottomRightZ) * ((double) cz - bottomRightZ)
                            + ((double) cx - bottomRightX) * ((double) cx - bottomRightX);

                        if (distTopLeft < distTopRight && distTopLeft < distBottomLeft
                            && distTopLeft < distBottomRight) {
                            zoomed[index++] = topLeft;
                        } else if (distTopRight < distTopLeft && distTopRight < distBottomLeft
                            && distTopRight < distBottomRight) {
                                zoomed[index++] = topRight;
                            } else if (distBottomLeft < distTopLeft && distBottomLeft < distTopRight
                                && distBottomLeft < distBottomRight) {
                                    zoomed[index++] = bottomLeft;
                                } else {
                                    zoomed[index++] = bottomRight;
                                }
                    }
                }

                topLeft = topRight;
                bottomLeft = bottomRight;
            }
        }

        int[] out = IntCache.getIntCache(areaWidth * areaHeight);

        for (int y = 0; y < areaHeight; ++y) {
            System.arraycopy(
                zoomed,
                (y + (areaY & cell - 1)) * zoomedWidth + (areaX & cell - 1),
                out,
                y * areaWidth,
                areaWidth);
        }

        return out;
    }
}
