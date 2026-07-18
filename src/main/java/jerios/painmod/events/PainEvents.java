package jerios.painmod.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import jerios.painmod.registry.ModPotions;
import jerios.painmod.utils.Translations;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.FoodStats;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

import java.util.List;

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
    public void onItemUse(PlayerUseItemEvent.Finish event) {
        EntityPlayer player= event.entityPlayer;

        if (player.isPotionActive(ModPotions.malnourishment)) {
            ItemStack itemStack = event.item;
            Item item = itemStack.getItem();

            if (item instanceof ItemFood) {
                ItemFood food = (ItemFood) item;
               int foodHeal = food.func_150905_g(itemStack);
               float foodSat = food.func_150906_h(itemStack);

                FoodStats playerStats = player.getFoodStats();

                int currentPlayerHeal = playerStats.getFoodLevel();
                float currentPlayerSat = playerStats.getSaturationLevel();

                int finalHeal = currentPlayerHeal - (foodHeal / 2);
                float finalSat = currentPlayerSat - (foodSat / 1.5f);

                player.getFoodStats().setFoodLevel(finalHeal);
                player.getFoodStats().setFoodSaturationLevel(finalSat);

            }
            if (item instanceof ItemPotion) {
                ItemPotion potion = (ItemPotion) item;
                List<PotionEffect> potionList = potion.getEffects(itemStack);
                for (int i = 0; i < potionList.size(); i++) {
                    PotionEffect currentEffect = potionList.get(i);
                    int potionID = currentEffect.getPotionID();
                    int potionPotency = currentEffect.getAmplifier();
                    int durationTimer = currentEffect.getDuration();
                    boolean isAmb = currentEffect.getIsAmbient();

                    //TODO: REDUCE POTENCY, MAKE IT LAST SHORTER, MAKE IT APPLY FOR CURRENT EXISTING POTION EFFECTS
                    player.removePotionEffect(potionID);
                    if (potionPotency > 1) {

                    }
                    // line 1222 of EntityLivingBase
                    //    i = (this.getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;

                    player.addPotionEffect(new PotionEffect(potionID, potionPotency, durationTimer, isAmb));

                }
            }


        }


    }

    @SubscribeEvent
    public void onItemUse(PlayerUseItemEvent.Tick event) {
     EntityPlayer player= event.entityPlayer;

     if (player.isPotionActive(ModPotions.malnourishment)) {
         if (player.worldObj.rand.nextInt(6) == 0) {
             if (event.duration >= 4) {
                 event.duration -= 3;
             }
         }
     }

    }



}
