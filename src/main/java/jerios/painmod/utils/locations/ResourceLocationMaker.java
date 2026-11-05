package jerios.painmod.utils.locations;

import jerios.painmod.forge.PainShare;
import jerios.painmod.forge.PainMod;
import net.minecraft.util.ResourceLocation;

public class ResourceLocationMaker {

    public static ResourceLocation makeResourceLocationEntityHostile(String filename) {
        return new ResourceLocation(PainMod.MODID, PainShare.ENTITY_AGREESIVE + filename + ".png");
    }

    public static ResourceLocation makeResourceLocationEntityPeaceful(String filename) {
        return new ResourceLocation(PainMod.MODID, PainShare.ENTITY_PEACEFUL + filename + ".png");
    }
}
