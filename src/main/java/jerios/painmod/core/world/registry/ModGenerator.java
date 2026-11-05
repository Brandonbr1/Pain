package jerios.painmod.core.world.registry;

import jerios.painmod.forge.config.PainConfig;
import jerios.painmod.core.world.PainProvider;
import net.minecraftforge.common.DimensionManager;

public class ModGenerator {

    public static void register(){
        int dim = PainConfig.painDimensionID;
        DimensionManager.registerProviderType(dim, PainProvider.class, true);
        DimensionManager.registerDimension(dim,dim);
    }


}
