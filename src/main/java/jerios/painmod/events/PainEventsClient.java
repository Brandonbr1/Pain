package jerios.painmod.events;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import jerios.painmod.client.audio.PainMusic;
import jerios.painmod.config.PainConfig;
import jerios.painmod.registry.ModItemBlocks;
import jerios.painmod.utils.PGL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class PainEventsClient {

    private Minecraft mc = FMLClientHandler.instance().getClient();


    @SubscribeEvent
    public void onTickWorldRenderEvent(TickEvent.ClientTickEvent event) {
        //leftClickCounter
        // rightClickTimer set high when we have the potion effect active

        if (mc.theWorld != null) {
            if (inPainDim()) {
                if (mc.gameSettings != null && mc.gameSettings.gammaSetting >= PainConfig.maxBrightnessInDim) {
                    mc.gameSettings.gammaSetting = PainConfig.maxBrightnessInDim;
                }
            }

        }


    }

    @SubscribeEvent
    public void loadPainDim(WorldEvent.Load load) {
        if (load.world.isRemote) {
            if (load.world.provider.dimensionId == PainConfig.painDimensionID)
            {
                if (!(mc.mcMusicTicker instanceof PainMusic))
                {
                    prevMusicTicker = mc.mcMusicTicker;
                    mc.mcMusicTicker = new PainMusic(prevMusicTicker);
                } else {
                    if (prevMusicTicker == null)
                    {
                        prevMusicTicker = mc.mcMusicTicker;
                    }

                    if (mc.mcMusicTicker != prevMusicTicker)
                    {
                        mc.mcMusicTicker = prevMusicTicker;
                    }
                }


            } else {
                if (prevMusicTicker == null)
                {
                    prevMusicTicker = mc.mcMusicTicker;
                }

                if (mc.mcMusicTicker != prevMusicTicker)
                {
                    mc.mcMusicTicker = prevMusicTicker;
                }

            }
        }
    }

    @SubscribeEvent
    public void disconnect(WorldEvent.Unload unload) {
        if (unload.world.isRemote) {
            if (prevMusicTicker != null) {
                mc.mcMusicTicker = prevMusicTicker;
                prevMusicTicker = null;
            }
        }
    }

    public MusicTicker prevMusicTicker;


    @SubscribeEvent
    public void fogRenderEvent(EntityViewRenderEvent.RenderFogEvent fogEvent)
    {
        if (inPainDim())
        {
            float dist = fogEvent.farPlaneDistance;

            PGL.fog(GL11.GL_FOG_MODE, GL11.GL_LINEAR);

            GL11.glFogf(GL11.GL_FOG_START, dist * PainConfig.fogDensityStart);
            GL11.glFogf(GL11.GL_FOG_END, dist * PainConfig.fogDensityEnd);

            if (GLContext.getCapabilities().GL_NV_fog_distance)
            {
                PGL.fog(34138, 34139);
            }

        }

    }

    @SubscribeEvent
    public void fogDensityEvent(net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity density)
    {
        if (inPainDim()) {
            density.density = PainConfig.fogDensity;
        }
    }

    @SubscribeEvent
    public void fogColorChange(EntityViewRenderEvent.FogColors fogColors) {
        if (inPainDim()) {
            fogColors.blue = 0.0F; //TODO: FOG COLORS AS CONFIG
            fogColors.red = 0.0F;
            fogColors.green = 0.0F;
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


    private boolean inPainDim() {
      return  mc.theWorld != null && mc.theWorld.provider.dimensionId == PainConfig.painDimensionID;
    }



}
