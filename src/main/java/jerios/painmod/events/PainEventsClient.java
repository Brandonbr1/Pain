package jerios.painmod.events;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import jerios.painmod.config.PainConfig;
import jerios.painmod.registry.ModItemBlocks;
import jerios.painmod.utils.PGL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class PainEventsClient {

    Minecraft mc = FMLClientHandler.instance().getClient();

    @SubscribeEvent
    public void onTickClient(TickEvent.ClientTickEvent event) {
        //leftClickCounter
        // rightClickTimer set high when we have the potion effect active

        if (mc.theWorld != null && mc.theWorld.provider.dimensionId == PainConfig.painDimensionID)
        {
            if (mc.gameSettings != null && mc.gameSettings.gammaSetting >= PainConfig.maxBrightnessInDim)
            {
                mc.gameSettings.gammaSetting = PainConfig.maxBrightnessInDim;
            }
        }

    }

    @SubscribeEvent
    public void fogRenderEvent(EntityViewRenderEvent.RenderFogEvent fogEvent) {
        float f1 = fogEvent.farPlaneDistance;
        

        GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);

        GL11.glFogf(GL11.GL_FOG_START, f1 * 0.25F);
        GL11.glFogf(GL11.GL_FOG_END, f1);

        if (GLContext.getCapabilities().GL_NV_fog_distance)
        {
            GL11.glFogi(34138, 34139);
        }

    }

    @SubscribeEvent
    public void fogDensityEvent(net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity density)
    {
        density.density = 1.0f;
    }

    @SubscribeEvent
    public void fogColorChange(EntityViewRenderEvent.FogColors fogColors) {
        fogColors.blue = 1.0F;
        fogColors.red = 1.0F;
        fogColors.green = 1.0F;
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
