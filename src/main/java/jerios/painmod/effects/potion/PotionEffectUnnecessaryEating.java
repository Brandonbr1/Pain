package jerios.painmod.effects.potion;

import com.mojang.authlib.GameProfile;
import jerios.painmod.registry.ModPotions;
import jerios.painmod.utils.PainFakePlayer;
import jerios.painmod.utils.PainFakePlayerFactory;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.WorldServer;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

public class PotionEffectUnnecessaryEating extends Potion {

    public PotionEffectUnnecessaryEating(int id, int color) {
        super(id, true, color);
    }

    public static void applyTheEffect(ItemFood item, ItemStack stack, int index, EntityPlayer player)
    {
            if (stack.getItem() != item) {
                return;
            }

            if (stack.stackSize >= 1) {
                item.onEaten(stack, player.worldObj, player);
                stack.stackSize--;
                System.out.println("I was ran, so why you no work?");
                player.worldObj.playSoundAtEntity(player, "random.burp", 0.5F, player.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }
            if (stack.stackSize <= 0) {
                player.inventory.mainInventory[index] = null;
                player.worldObj.playSoundAtEntity(player, "random.burp", 0.5F, player.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

    }
    boolean applyInstantly = false;
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        if (!applyInstantly) {
            applyInstantly = true;
            return true;
        }
        int amp = (amplifier * amplifier) + amplifier;
        return duration % 180 - amp == 0;
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {

        if (entityLivingBase instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLivingBase;

            if (!player.isPotionActive(Potion.hunger))
            {
                Collection<PotionEffect> activePotions = player.getActivePotionEffects();
                for (PotionEffect pot : activePotions) {
                    if (pot.getPotionID() == ModPotions.gluttony.id) {
                        player.addPotionEffect(new PotionEffect(Potion.hunger.id, pot.getDuration() + 90, pot.getAmplifier()));
                    }
                }
            }

            float eatFactor = 0.80F;
            if (player.isPotionActive(ModPotions.malnourishment)){
                eatFactor += 0.21F;
            }

            if (player.getFoodStats().getSaturationLevel() > 0) {
                player.getFoodStats().setFoodSaturationLevel(0);
            }
            player.addExhaustion(eatFactor * (float)(amplifier + 1));

            ItemStack currentItem = player.inventory.getCurrentItem();

            if (currentItem != null && currentItem.getItem() != null) {
                if (currentItem.getItem() instanceof ItemFood) {
                    ItemFood food = (ItemFood) currentItem.getItem();
                    applyTheEffect(food, currentItem, player.inventory.currentItem, player);
                }
            }

        }


    }
}
