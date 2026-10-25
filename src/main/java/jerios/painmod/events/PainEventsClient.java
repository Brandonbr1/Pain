package jerios.painmod.events;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import jerios.painmod.config.PainConfig;
import jerios.painmod.registry.ModItemBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.FOVUpdateEvent;
import org.lwjgl.Sys;

public class PainEventsClient {

    Minecraft mc = FMLClientHandler.instance().getClient();

    @SubscribeEvent
    public void onTickClient(TickEvent.ClientTickEvent event) {
        //leftClickCounter
        // rightClickTimer set high when we have the potion effect active

        if (mc.theWorld != null && mc.theWorld.provider.dimensionId == PainConfig.painDimensionID)
        {
            if (mc.gameSettings != null && mc.gameSettings .gammaSetting > PainConfig.maxBrightnessInDim)
            {
                System.out.println("GAMMA       "+mc.gameSettings.gammaSetting);
                mc.gameSettings.gammaSetting = PainConfig.maxBrightnessInDim;
            }
        }

    }



    // WOW.... Vanilla really hardcoded the bow...
    @SubscribeEvent
    public void onFov(FOVUpdateEvent updateEvent) {
        float f = 1.0F;

        EntityPlayerSP playerSP = updateEvent.entity;

        if (playerSP.isUsingItem() && playerSP.getItemInUse().getItem() == ModItemBlocks.cursedBow)
        {
            int i = playerSP.getItemInUseDuration();
            float f1 = (float)i / 20.0F;

            if (f1 > 1.0F)
            {
                f1 = 1.0F;
            }
            else
            {
                f1 *= f1;
            }

            f *= 1.0F - f1 * 0.15F;

            updateEvent.newfov = f;
        }
    }



}
