package jerios.painmod.world;

import jerios.painmod.registry.ModBiomes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;
import java.util.Random;

public class PainBiomeManager {



    public static final BiomeGenBase[] COLD_BIOMES = new BiomeGenBase[]
    {
            ModBiomes.painColdTiga,
            ModBiomes.painColdTiga
    };

    public static final BiomeGenBase[] WARM_BIOMES = new BiomeGenBase[]
    {
            ModBiomes.painPlains,
            ModBiomes.painBeach

    };


    public static final BiomeGenBase[] DESERT_BIOMES = new BiomeGenBase[]
    {
        ModBiomes.painDesert,
            ModBiomes.painDesert
    };

    public static final BiomeGenBase[] ICY_BIOMES = new BiomeGenBase[]
    {

    };

    public static BiomeGenBase getRandomWarmBiome(int index) {
        return WARM_BIOMES[index];
    }

    public static BiomeGenBase getRandomColdBiome(int index) {
        return COLD_BIOMES[index];
    }

    public static BiomeGenBase getRandomDesertBiome(int index) {
        return DESERT_BIOMES[index];
    }

    public static BiomeGenBase getRandomIcyBiome(int index) {
        return ICY_BIOMES[index];
    }







}
