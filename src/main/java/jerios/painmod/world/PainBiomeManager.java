package jerios.painmod.world;

import jerios.painmod.registry.ModBiomes;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.Random;

public class PainBiomeManager {



    private static final BiomeGenBase[] COLD_BIOMES = new BiomeGenBase[]
    {
    };

    private static final BiomeGenBase[] WARM_BIOMES = new BiomeGenBase[]
    {
            ModBiomes.painPlains,

    };


    private static final BiomeGenBase[] DESERT_BIOMES = new BiomeGenBase[]
    {
        ModBiomes.painDesert
    };

    public static final BiomeGenBase[] ICY_BIOMES = new BiomeGenBase[]
    {

    };

    public static BiomeGenBase getRandomWarmBiome(Random random) {
        return WARM_BIOMES[random.nextInt(WARM_BIOMES.length)];
    }

    public static BiomeGenBase getRandomColdBiome(Random random) {
        return COLD_BIOMES[random.nextInt(COLD_BIOMES.length)];
    }

    public static BiomeGenBase getRandomDesertBiome(Random random) {
        return DESERT_BIOMES[random.nextInt(DESERT_BIOMES.length)];
    }

    public static BiomeGenBase getRandomIcyBiome(Random random) {
        return ICY_BIOMES[random.nextInt(ICY_BIOMES.length)];
    }







}
