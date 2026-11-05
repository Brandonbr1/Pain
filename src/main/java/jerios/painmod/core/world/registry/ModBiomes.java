package jerios.painmod.core.world.registry;

import jerios.painmod.forge.config.PainConfig;
import jerios.painmod.core.world.biome.PainBeach;
import jerios.painmod.core.world.biome.PainColdTiga;
import jerios.painmod.core.world.biome.PainDesert;
import jerios.painmod.core.world.biome.PainJungle;
import jerios.painmod.core.world.biome.PainPlains;
import net.minecraft.world.biome.BiomeGenBase;

public class ModBiomes {

    public static final BiomeGenBase painPlains = new PainPlains(PainConfig.painBiomeID);
    public static final BiomeGenBase painJungles = new PainJungle(PainConfig.painJungleID);
    public static final BiomeGenBase painDesert = new PainDesert(PainConfig.painDesertID);
    public static final BiomeGenBase painBeach = new PainBeach(PainConfig.painBeachID);
    public static final BiomeGenBase painColdTiga = new PainColdTiga(PainConfig.painColdTigaID, 0);

    public static void register() {
    }


}
