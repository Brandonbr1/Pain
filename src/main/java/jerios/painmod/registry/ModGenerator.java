package jerios.painmod.registry;

import jerios.painmod.config.PainConfig;
import jerios.painmod.world.PainProvider;
import net.minecraftforge.common.DimensionManager;

public class ModGenerator {

    public static void register(){
        int dim = PainConfig.painDimensionID;
        DimensionManager.registerProviderType(dim, PainProvider.class, true);
        DimensionManager.registerDimension(dim,dim);
    }


}
