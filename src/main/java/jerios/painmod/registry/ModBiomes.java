package jerios.painmod.registry;

import jerios.painmod.config.PainConfig;
import jerios.painmod.world.biome.PainDesert;
import jerios.painmod.world.biome.PainJungle;
import jerios.painmod.world.biome.PainPlains;
import net.minecraft.world.biome.BiomeGenBase;

public class ModBiomes {

    public static final BiomeGenBase painPlains = new PainPlains(PainConfig.painBiomeID);
    public static final BiomeGenBase painJungles = new PainJungle(PainConfig.painJungleID);
    public static BiomeGenBase painDesert = new PainDesert(PainConfig.painDesertID);

    public static void register() {
    }


}
