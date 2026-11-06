package jerios.painmod.forge.events;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jerios.painmod.core.items.weapons.IBow;
import jerios.painmod.forge.config.PainConfig;
import jerios.painmod.utils.rendering.PGL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;

import java.util.Objects;

@SideOnly(Side.CLIENT)
public class PainEventsClient {

    boolean hasNightVision = false;
    @SubscribeEvent
    public void tickClient(TickEvent.ClientTickEvent tickEvent) {

        if (tickEvent.side == Side.CLIENT) {
            if (mc.thePlayer != null) {
                hasNightVision = mc.thePlayer.isPotionActive(Potion.nightVision);
            }

            if (mc.theWorld != null) {
                if (inPainDim()) {
                    if (mc.gameSettings != null && mc.gameSettings.gammaSetting >= PainConfig.maxBrightnessInDim) {
                        mc.gameSettings.gammaSetting = PainConfig.maxBrightnessInDim;
                    }
                }

            }
        }

    }


    private Minecraft mc = FMLClientHandler.instance().getClient();



    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void lowerRainVolume(PlaySoundEvent17 soundEvent) {
        if (inPainDim()) {
            if (Objects.equals(soundEvent.name, "ambient.weather.rain")) {

                float vol = soundEvent.sound.getVolume();
                float pitch = soundEvent.sound.getPitch();
                if (vol > 0.1F && pitch >= 0.5F) {
                    soundEvent.result = null;
                    mc.thePlayer.playSound("ambient.weather.rain", 0.1f, 0.2f);
                }

            }
        }

    }

    @SubscribeEvent
    public void fogRenderEvent(EntityViewRenderEvent.RenderFogEvent fogEvent)
    {
        if (inPainDim())
        {
            float dist = fogEvent.farPlaneDistance;
            if (fogEvent.fogMode != PGL.GL_LINEAR) {
                PGL.fogLinear();
            }

            if (!hasNightVision) {
                PGL.fogStart(dist * PainConfig.fogDensityStart);
                PGL.fogEnd(dist * PainConfig.fogDensityEnd);
            } else {
                PGL.fogStart(0);
                PGL.fogEnd(0.1f);
            }


            PGL.fogCapabilities();


        }

    }


    @SubscribeEvent
    public void fogDensityEvent(EntityViewRenderEvent.FogDensity density)
    {
        if (inPainDim())
        {
            if (!hasNightVision) {
                density.density = PainConfig.fogDensity;
            } else {
                density.density = 90;
            }
        }
    }


    // THIS CAUSESS NIGHT VISION TO BE VERY VISIBLE if fog color is 0.1, 0.1, 0.1....
    // Why?


    public void fogColorChange(EntityViewRenderEvent.FogColors fogColors) {
        if (inPainDim())
        {
            if (!hasNightVision) {
                fogColors.blue = 0.1f;
                fogColors.green = 0.1f;
                fogColors.red = 0.1f;
            } else {
                fogColors.red = 000.0F;
                fogColors.blue =  000.0F;
                fogColors.green =  000.0F;

            }

        }
    }




    @SubscribeEvent
    public void fovChanged(FOVUpdateEvent updateEvent) {
        float f = 1.0F;

        EntityPlayerSP playerSP = updateEvent.entity;

        if (playerSP.isUsingItem() && playerSP.getItemInUse().getItem() instanceof IBow)
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

    @SubscribeEvent
    public void renderTheBowCorrect(RenderPlayerEvent.Specials.Pre pre) {
        EntityPlayer player = pre.entityPlayer;

        ModelBiped modelBipedMain = pre.renderer.modelBipedMain;

        ItemStack itemstack1 = player.inventory.getCurrentItem();

        float f2;

        if (itemstack1 != null)
        {

            if (itemstack1.getItem() instanceof IBow) {
                pre.renderItem = false;
            } else {
                pre.renderItem = true;
                return;
            }


            PGL.pushMatrix();
            modelBipedMain.bipedRightArm.postRender(0.0625F);
            PGL.translate(-0.0625F, 0.4375F, 0.0625F);


            f2 = 0.625F;
            PGL.translate(0.0F, 0.125F, 0.3125F);
            PGL.rotate(-20.0F, 0.0F, 1.0F, 0.0F);
            PGL.scale(f2, -f2, f2);
            PGL.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
            PGL.rotate(45.0F, 0.0F, 1.0F, 0.0F);


            float f3;
            int k;
            float f12;

            k = itemstack1.getItem().getColorFromItemStack(itemstack1, 0);
            float f11 = (float)(k >> 16 & 255) / 255.0F;
            f12 = (float)(k >> 8 & 255) / 255.0F;
            f3 = (float)(k & 255) / 255.0F;
            PGL.color(f11, f12, f3, 1.0F);
            RenderManager.instance.itemRenderer.renderItem(player, itemstack1, 0);


            PGL.popMatrix();
        }

    }


    private boolean inPainDim() {
        return mc.theWorld != null && mc.theWorld.provider.dimensionId == PainConfig.painDimensionID;
    }



}
