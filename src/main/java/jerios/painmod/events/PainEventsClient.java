package jerios.painmod.events;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import jerios.painmod.registry.ModItemBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.Items;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class PainEventsClient {

    Minecraft mc = FMLClientHandler.instance().getClient();

    @SubscribeEvent
    public void onTickClient(TickEvent.ClientTickEvent event) {
        //leftClickCounter
        // rightClickTimer set high when we have the potion effect active
    }


    // WOW.... Vanilla really hardcoded the bow...
    @SubscribeEvent
    public void onFov(FOVUpdateEvent updateEvent) {


        System.out.println("FIRE");
        float f = 1.0F;

        EntityPlayerSP playerSP = updateEvent.entity;

        if (playerSP.capabilities.isFlying)
        {
            f *= 1.1F;
        }

        IAttributeInstance iattributeinstance = playerSP.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        f = (float)((double)f * ((iattributeinstance.getAttributeValue() / (double)playerSP.capabilities.getWalkSpeed() + 1.0D) / 2.0D));

        if (playerSP.capabilities.getWalkSpeed() == 0.0F || Float.isNaN(f) || Float.isInfinite(f))
        {
            f = 1.0F;
        }

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
        }
    }



}
