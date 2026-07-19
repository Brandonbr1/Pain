package jerios.painmod.effects.potion;

import jerios.painmod.registry.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class UnnecessaryEatingEffect extends Potion {

    public UnnecessaryEatingEffect(int id, int color) {
        super(id, true, color);
    }

    public static void applyTheEffect(ItemFood item, ItemStack stack, int index, EntityPlayer player)
    {
            if (stack.getItem() != item) {
                return;
            }

        ItemStack actualStack = player.inventory.mainInventory[index];
        Item actualItem = actualStack.getItem();

        if (actualStack != stack) {
            return;
        }

        if (actualItem != item) {
            return;
        }

            if (stack.stackSize >= 1) {
                stack.stackSize--;
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
               PotionEffect pot = player.getActivePotionEffect(ModPotions.gluttony);
               player.addPotionEffect(new PotionEffect(Potion.hunger.id, pot.getDuration() + 90, pot.getAmplifier()));
            }

            if (player.getFoodStats().getSaturationLevel() > 1) {
                player.getFoodStats().setFoodSaturationLevel(0);
            }

            float eatFactor = 3.0F;
            if (player.isPotionActive(ModPotions.malnourishment)){
                eatFactor += 0.8F;
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
