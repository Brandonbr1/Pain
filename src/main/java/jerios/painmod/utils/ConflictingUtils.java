package jerios.painmod.utils;

import jerios.painmod.Share;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DimensionManager;

public class ConflictingUtils {

    public static void isConflictingBiome(int biome, String thebiomename) {
        if (BiomeGenBase.getBiome(biome) != null) {
            Share.LOG.error("Pain Mod has detected a likely conflicting biome id");
            Share.LOG.error("The Conflicting biome name    "+BiomeGenBase.getBiome(biome).biomeName);

        }

    }



}
