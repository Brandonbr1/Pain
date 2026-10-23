package jerios.painmod.items.buckets;

import jerios.painmod.registry.ModFluidRegistry;
import net.minecraft.item.ItemBucket;

public class MuddyPainBucket extends ItemBucket {

    public MuddyPainBucket() {
        super(ModFluidRegistry.muddyFluidBlock);
    }
}
