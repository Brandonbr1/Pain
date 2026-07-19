package jerios.painmod.events;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import jerios.painmod.PainMod;
import jerios.painmod.config.PainConfig;
import jerios.painmod.registry.ModPotions;
import jerios.painmod.utils.Translations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class PainEvents {
    
    @SubscribeEvent
    public void actuallySayText(PlayerEvent.PlayerChangedDimensionEvent event) {
        EntityPlayer player = event.player;
        if (event.fromDim != PainConfig.painDimensionID && event.toDim == PainConfig.painDimensionID) {
            player.addChatMessage(new ChatComponentTranslation("painmod.enterDim"));
        } else if (event.fromDim == PainConfig.painDimensionID && event.toDim != PainConfig.painDimensionID) {
            player.addChatMessage(new ChatComponentTranslation("painmod.ahhFinallyFree"));
        }
    }

    @SubscribeEvent
    public void onJump(LivingEvent.LivingJumpEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            if (player.isPotionActive(ModPotions.sloth)) {
            player.motionY -= 0.8D;
            }
        }

    }


    @SubscribeEvent
    public void onFall(LivingFallEvent event) {
        if (!event.entityLiving.handleWaterMovement() && event.entityLiving.onGround) {
            if (event.entityLiving instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.entityLiving;
                if (player.isPotionActive(ModPotions.sloth)) {
                    player.motionY += 0.8D;
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.entityPlayer.isPotionActive(ModPotions.sloth)) {
            event.setCanceled(true);
        }

    }

   /** @SubscribeEvent
    public void onAttackSloth(AttackEntityEvent event) {
        if (event.entityPlayer.isPotionActive(ModPotions.sloth))
        {
            event.setCanceled(true);
        }
    }
    **/

    @SubscribeEvent
    public void onHurt(LivingHurtEvent event) {
        DamageSource source = event.source;
        Entity attacker = source.getEntity();

        if (attacker instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) attacker;

            if (player.isPotionActive(ModPotions.rageEffect)) {
                player.attackEntityFrom(PainMod.RAGE, event.ammount * 0.5f);
                player.addExhaustion(event.ammount * 0.8f);
                ItemStack stack = player.inventory.getCurrentItem();

               if (stack != null) {
                   Item item = stack.getItem();
                   if (item != null) {
                       if (item.isDamageable()) {
                           stack.damageItem((int) (event.ammount * Math.max(event.ammount, 3)), player);
                       }
                    }
                   }

               }

        }


        // if player gets hurt.
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            float damage = 1.0f;
            if (player.isPotionActive(ModPotions.malnourishment)) {
                damage += 0.6F;
            }
            if (player.isPotionActive(ModPotions.deadlyPoison)) {
                damage += 0.2F;
            }
            if (player.isPotionActive(ModPotions.plaugeEffect)) {
                damage += 0.4F;
            }
            event.ammount = damage;
        }
    }

    @SubscribeEvent
    public void onItemUse(PlayerUseItemEvent.Finish event) {
        EntityPlayer player= event.entityPlayer;
        ItemStack itemStack = event.item;
        Item item = itemStack.getItem();

        if (player.isPotionActive(ModPotions.malnourishment)) {
            if (item instanceof ItemFood) {
                ItemFood food = (ItemFood) item;
                int foodHeal = food.func_150905_g(itemStack);
                float foodSat = food.func_150906_h(itemStack);

                FoodStats playerStats = player.getFoodStats();

                int currentPlayerHeal = playerStats.getFoodLevel();
                float currentPlayerSat = playerStats.getSaturationLevel();

                int finalHeal = (int) (currentPlayerHeal - (foodHeal * 0.5));
                float finalSat = currentPlayerSat - (foodSat * 0.5f);

                player.getFoodStats().setFoodLevel(finalHeal);
                player.getFoodStats().setFoodSaturationLevel(finalSat);
            }


            if (item instanceof ItemPotion) {
                ItemPotion potion = (ItemPotion) item;
                List<PotionEffect> potionList = potion.getEffects(itemStack);
                Iterator<PotionEffect> iter = potionList.iterator();

                while (iter.hasNext()) {
                    PotionEffect currentEffect = iter.next();
                    Potion currentPotion = Potion.potionTypes[currentEffect.getPotionID()];

                    if (!currentPotion.isBadEffect()) {
                        System.out.println("I am NOT bad effect lol");
                    }

                    int potionID = currentEffect.getPotionID();
                    int potionPotency = currentEffect.getAmplifier();
                    int durationTimer = currentEffect.getDuration();
                    boolean isAmb = currentEffect.getIsAmbient();

                    player.removePotionEffect(potionID);
                    if (potionPotency > 1) {
                        potionPotency -= 1;
                    }
                    durationTimer = (int) (durationTimer * 0.5D);
                    player.addPotionEffect(new PotionEffect(potionID, potionPotency, durationTimer, isAmb));
                }
            }


        }

        if (item instanceof ItemBucketMilk) {
            Collection<PotionEffect> eff = player.getActivePotionEffects();
            Iterator<PotionEffect> iter = eff.iterator();

            while (iter.hasNext()) {
                PotionEffect currentPotion = iter.next();

                int potionID = currentPotion.getPotionID();
                int potionPotency = currentPotion.getAmplifier();
                int durationTimer = currentPotion.getDuration();
                boolean isAmb = currentPotion.getIsAmbient();

                player.removePotionEffect(currentPotion.getPotionID());
            }

        }


    }

    @SubscribeEvent
    public void longerBowDrawTime(PlayerUseItemEvent.Start event) {
        EntityPlayer player = event.entityPlayer;
        ItemStack itemStack = event.item;
        Item item = itemStack.getItem();

        if (player.isPotionActive(ModPotions.malnourishment))
        {
            if (item instanceof ItemBow) {
                    event.duration += 5;
            }

        }
    }

    @SubscribeEvent
    public void onItemUse(PlayerUseItemEvent.Tick event) {
     EntityPlayer player= event.entityPlayer;

     if (player.isPotionActive(ModPotions.malnourishment)) {
         if (player.ticksExisted % 15 == 0) {
             event.duration += 13;
         }
     }

    }



}
