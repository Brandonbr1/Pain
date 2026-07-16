package jerios.painmod.utils;

import jerios.painmod.PainConstants;
import jerios.painmod.PainMod;
import net.minecraft.util.ResourceLocation;

public class ResourceLocationMaker {

    public static ResourceLocation makeResourceLocationEntityHostile(String filename) {
        return new ResourceLocation(PainMod.MODID, PainConstants.ENTITY_AGREESIVE + filename + ".png");
    }

    public static ResourceLocation makeResourceLocationEntityPeaceful(String filename) {
        return new ResourceLocation(PainMod.MODID, PainConstants.ENTITY_PEACEFUL + filename + ".png");
    }
}
