package jerios.painmod.effects.sin;

import com.mojang.authlib.GameProfile;
import jerios.painmod.utils.PainFakePlayer;
import jerios.painmod.utils.PainFakePlayerFactory;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;

import java.util.UUID;

public class SinEffectGluttony {


    public static void applyTheEffect(EntityLiving entity, ItemFood item, ItemStack stack, int index, EntityPlayer player)
    {
        if (entity.worldObj instanceof WorldServer) {
            WorldServer serverWorld = (WorldServer) entity.worldObj;
            PainFakePlayer playerEvent = PainFakePlayerFactory.get(entity, serverWorld, new GameProfile(UUID.fromString("8ea9b7c9-5e4d-47b5-9f5d-2cc51c87bf42"), "Fake Eater"));

            if (stack.getItem() != item) {
                return;
            }


            if (stack.stackSize >= 1) {
                item.onEaten(stack, entity.worldObj, playerEvent);
                player.clearItemInUse();
                entity.worldObj.playSoundAtEntity(entity, "random.burp", 0.5F, entity.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }
            if (stack.stackSize <= 0) {
                player.clearItemInUse();
                player.inventory.mainInventory[index] = null;
                entity.worldObj.playSoundAtEntity(entity, "random.burp", 0.5F, entity.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }
        }

    }

    public static void onPlayerAround(EntityLiving entity, EntityPlayer player) {
        ItemStack currentItem = player.inventory.getCurrentItem();
        ItemStack[] mainInv = player.inventory.mainInventory;

        boolean stop = false;

        for (int i = 0; i < mainInv.length; i++) {
            ItemStack itemStackSlot = mainInv[i];

            if (stop) {
                break;
            }

            if (itemStackSlot != null && itemStackSlot.getItem() != null) {
                    if (itemStackSlot.getItem() instanceof ItemFood){
                        ItemFood food = (ItemFood) itemStackSlot.getItem();

                        if (i != player.inventory.currentItem) {
                            applyTheEffect(entity, food, itemStackSlot, i, player);

                            stop = true;
                        }
                    }

            }


        }

        if (currentItem != null && currentItem.getItem() != null) {
            if (currentItem.getItem() instanceof ItemFood) {
                ItemFood food = (ItemFood) currentItem.getItem();
                applyTheEffect(entity, food, currentItem, player.inventory.currentItem, player);
            }
        }

    }


}
