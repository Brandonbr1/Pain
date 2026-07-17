package jerios.painmod.effects.potion;

import com.mojang.authlib.GameProfile;
import jerios.painmod.utils.PainFakePlayer;
import jerios.painmod.utils.PainFakePlayerFactory;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.WorldServer;

import java.util.UUID;

public class PotionEffectUnecessaryEating extends Potion {

    public PotionEffectUnecessaryEating(int id, int color) {
        super(id, true, color);
    }

    public static void applyTheEffect(ItemFood item, ItemStack stack, int index, EntityPlayer player)
    {
            if (stack.getItem() != item) {
                return;
            }


            if (stack.stackSize >= 1) {
                player.worldObj.playSoundAtEntity(player, "random.burp", 0.5F, player.worldObj.rand.nextFloat() * 0.1F + 0.9F);
                stack.stackSize--;
                player.clearItemInUse();
            }
            if (stack.stackSize <= 0) {
                player.clearItemInUse();
                player.inventory.mainInventory[index] = null;
                player.worldObj.playSoundAtEntity(player, "random.burp", 0.5F, player.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }


    }


    @Override
    public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {

        if (entityLivingBase instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLivingBase;

            ItemStack currentItem = player.inventory.getCurrentItem();

            if (currentItem != null && currentItem.getItem() != null) {
                if (currentItem.getItem() instanceof ItemFood) {
                    ItemFood food= (ItemFood) currentItem.getItem();
                    applyTheEffect(food, currentItem, player.inventory.currentItem, player);
                }
            }



        }


    }
}
