package jerios.painmod.core.block.fluid.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import jerios.painmod.core.block.fluid.MuddyWaterFluid;
import jerios.painmod.core.block.fluid.MuddyWaterFluidForge;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluidRegistry {

    public static Fluid muddyFluid;
    public static Block muddyFluidBlock;

    public static void register() {
        muddyFluid = new MuddyWaterFluidForge("");
        FluidRegistry.registerFluid(muddyFluid);

        muddyFluidBlock = new MuddyWaterFluid(muddyFluid, Material.water);
        GameRegistry.registerBlock(muddyFluidBlock, "muddyFluid");

    }


}
