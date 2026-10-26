package jerios.painmod.world;

import jerios.painmod.registry.ModBiomes;
import jerios.painmod.world.layer.PainGenLayer;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
public class PainChunkManager extends WorldChunkManager {

    private final GenLayer genBiomes;
    private final GenLayer biomeIndexLayer;
    private final BiomeCache biomeCache;
    private final List<BiomeGenBase> biomesToSpawnIn;

    public PainChunkManager(World world) {
        GenLayer[] layers = PainGenLayer.initializeAllBiomeGenerators(world.getSeed());
        this.genBiomes = layers[0];
        this.biomeIndexLayer = layers[1];
        this.biomeCache = new BiomeCache(this);
        this.biomesToSpawnIn = new ArrayList<BiomeGenBase>(
                Arrays.asList(ModBiomes.painPlains));
    }

    @Override
    public List<BiomeGenBase> getBiomesToSpawnIn() {
        return this.biomesToSpawnIn;
    }

    @Override
    public BiomeGenBase getBiomeGenAt(int x, int z) {
        return this.biomeCache.getBiomeGenAt(x, z);
    }

    @Override
    public float[] getRainfall(float[] listToReuse, int x, int z, int width, int length) {
        IntCache.resetIntCache();

        if (listToReuse == null || listToReuse.length < width * length) {
            listToReuse = new float[width * length];
        }

        int[] ints = this.biomeIndexLayer.getInts(x, z, width, length);

        for (int i = 0; i < width * length; ++i) {
            listToReuse[i] = Math.min(
                    1.0F,
                    (float) BiomeGenBase.getBiome(ints[i])
                            .getIntRainfall() / 65536.0F);
        }

        return listToReuse;
    }

    @Override
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] listToReuse, int x, int z, int width, int length) {
        IntCache.resetIntCache();

        if (listToReuse == null || listToReuse.length < width * length) {
            listToReuse = new BiomeGenBase[width * length];
        }

        int[] ints = this.genBiomes.getInts(x, z, width, length);

        for (int i = 0; i < width * length; ++i) {
            listToReuse[i] = BiomeGenBase.getBiome(ints[i]);
        }

        return listToReuse;
    }

    @Override
    public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] listToReuse, int x, int z, int width, int length) {
        return this.getBiomeGenAt(listToReuse, x, z, width, length, true);
    }

    @Override
    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] listToReuse, int x, int z, int width, int length,
                                        boolean useCache) {
        IntCache.resetIntCache();

        if (listToReuse == null || listToReuse.length < width * length) {
            listToReuse = new BiomeGenBase[width * length];
        }

        if (useCache && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0) {
            BiomeGenBase[] cached = this.biomeCache.getCachedBiomes(x, z);
            System.arraycopy(cached, 0, listToReuse, 0, width * length);
            return listToReuse;
        }

        int[] ints = this.biomeIndexLayer.getInts(x, z, width, length);

        for (int i = 0; i < width * length; ++i) {
            listToReuse[i] = BiomeGenBase.getBiome(ints[i]);
        }

        return listToReuse;
    }

    @Override
    public boolean areBiomesViable(int x, int z, int radius, List<BiomeGenBase> allowed) {
        IntCache.resetIntCache();
        int minX = x - radius >> 2;
        int minZ = z - radius >> 2;
        int maxX = x + radius >> 2;
        int maxZ = z + radius >> 2;
        int width = maxX - minX + 1;
        int length = maxZ - minZ + 1;
        int[] ints = this.genBiomes.getInts(minX, minZ, width, length);

        for (int i = 0; i < width * length; ++i) {
            BiomeGenBase biome = BiomeGenBase.getBiome(ints[i]);

            if (!allowed.contains(biome)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ChunkPosition findBiomePosition(int x, int z, int radius, List<BiomeGenBase> allowed, Random random) {
        IntCache.resetIntCache();
        int minX = x - radius >> 2;
        int minZ = z - radius >> 2;
        int maxX = x + radius >> 2;
        int maxZ = z + radius >> 2;
        int width = maxX - minX + 1;
        int length = maxZ - minZ + 1;
        int[] ints = this.genBiomes.getInts(minX, minZ, width, length);
        ChunkPosition position = null;
        int found = 0;

        for (int i = 0; i < width * length; ++i) {
            int blockX = minX + i % width << 2;
            int blockZ = minZ + i / width << 2;
            BiomeGenBase biome = BiomeGenBase.getBiome(ints[i]);

            if (allowed.contains(biome) && (position == null || random.nextInt(found + 1) == 0)) {
                position = new ChunkPosition(blockX, 0, blockZ);
                ++found;
            }
        }

        return position;
    }

    @Override
    public void cleanupCache() {
        this.biomeCache.cleanupCache();
    }


}