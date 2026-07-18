package jerios.painmod.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import jerios.painmod.utils.Translations;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

public class PainEvents {
    
    @SubscribeEvent
    public void actuallySayText(PlayerEvent.PlayerChangedDimensionEvent event) {
        EntityPlayer player = event.player;
        if (event.fromDim != 5 && event.toDim == 5) {
            player.addChatMessage(new ChatComponentTranslation("painmod.enterDim"));
        } else if (event.fromDim == 5 && event.toDim != 5) {
            player.addChatMessage(new ChatComponentTranslation("painmod.ahhFinallyFree"));
        }
    }

    // PlayerUseItemEvent.Finish // PlayerUseItemEvent.Stop // PlayerUseItemEvent.Tick


    @SubscribeEvent
    public void onItemUse(PlayerUseItemEvent.Tick event) {
     EntityPlayer player=   event.entityPlayer;

     if (player.isPotionActive(Potion.blindness)) {
         if (player.worldObj.rand.nextInt(4) == 0) {
             if (event.duration >= 4) {
                 event.duration -= 2;
             }
         }
     }

    }



}
