package jerios.painmod.core.items.buckets;

import jerios.painmod.core.block.fluid.registry.ModFluidRegistry;
import net.minecraft.item.ItemBucket;

public class MuddyPainBucket extends ItemBucket {

    public MuddyPainBucket() {
        super(ModFluidRegistry.muddyFluidBlock);
    }
}
