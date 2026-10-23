package jerios.painmod.fluid;

import net.minecraftforge.fluids.Fluid;

public class MuddyWaterFluidForge extends Fluid {
    public MuddyWaterFluidForge(String fluidName) {
        super("painmod.muddyFluid");
        setViscosity(500);
    }
}
