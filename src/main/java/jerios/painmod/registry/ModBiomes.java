package jerios.painmod.registry;

import jerios.painmod.config.PainConfig;
import jerios.painmod.world.biome.PainPlains;
import net.minecraft.world.biome.BiomeGenBase;

public class ModBiomes {

    public static BiomeGenBase painPlains;

    public static void register() {
        painPlains = new PainPlains(PainConfig.painBiomeID);
    }


}
