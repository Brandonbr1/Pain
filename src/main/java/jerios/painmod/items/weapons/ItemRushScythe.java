package jerios.painmod.items.weapons;

import com.google.common.collect.Multimap;
import jerios.painmod.registry.TabsRegistry;
import jerios.painmod.utils.SidedCheckers;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class ItemRushScythe extends ItemSword {
    public ItemRushScythe(ToolMaterial material) {
        super(material);
        setCreativeTab(TabsRegistry.WEAPONS_TAB);
    }




    @Override
    public void addInformation(ItemStack stack, EntityPlayer world, List<String> tooltip, boolean flag) {
        super.addInformation(stack, world, tooltip, flag);


        tooltip.add("Extra Damage" + getDamageMul(stack));

    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
       int cl = getClick(stack);

        setClick(stack, cl - 1);

        if (cl > 0) {
            setClick(stack, 1);
        }


        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {

            if (SidedCheckers.isServer(player.worldObj)) {

                float playerHP = player.getHealth();
                float playerMaxHp = player.getMaxHealth();
                setHealthForLifesteal( stack,playerMaxHp * 0.3f);
                setDamageMulti( stack,playerHP - playerMaxHp);

             //   float playerMotion = (float) Math.abs((player.motionX * player.motionX * player.motionZ * player.motionZ)  + player.motionY + player.motionY );

              //  if (entity instanceof EntityLivingBase) {
              /**      EntityLivingBase base = (EntityLivingBase) entity;
                    double d1 = base.posX - player.posX;
                    double d0;

                    for (d0 = base.posZ - player.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
                        d1 = (Math.random() - Math.random()) * 0.01D;
                    }

                    base.attackedAtYaw = (float) (Math.atan2(d0, d1) * 180.0D / Math.PI) - this.rotationYaw;
                    base.knockBack(base, 2, d1, d0);
               **/

                    entity.addVelocity((double)(-MathHelper.sin(entity.rotationYaw * (float)Math.PI / 180.0F) * (float)2 * 0.5F), 0.1D, (double)(MathHelper.cos(entity.rotationYaw * (float)Math.PI / 180.0F) * (float)2 * 0.5F));

           //     }


                if (playerHP < getDamageLifesteal(stack))
                {
                    float hunger = player.getFoodStats().getFoodLevel();
                    player.heal(hunger);
                    player.getFoodStats().addExhaustion(hunger);
                }
                int cl = getClick(stack);

                setClick(stack, cl + 3);

                if (cl > 500)
                {
                    stack.damageItem(1, player);
                }


            }



        return super.onLeftClickEntity(stack, player, entity);



    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
    {
        return true;
    }


    public float getDamageLifesteal(ItemStack stack) {

        if (!getNBTData(stack).hasKey("healthForLifesteal")) {
            getNBTData(stack).setFloat("healthForLifesteal", 0);
        }

        return getNBTData(stack).getFloat("healthForLifesteal");
    }

    public void setHealthForLifesteal(ItemStack stack, float healthForLifesteal){
        NBTTagCompound n = getNBTData(stack);
        n.setFloat("healthForLifesteal", healthForLifesteal);

        stack.setTagCompound(n);
    }

    public float getDamageMul(ItemStack stack) {

        if (!getNBTData(stack).hasKey("DamageMulti")) {
            getNBTData(stack).setFloat("DamageMulti", 0);
        }

        return getNBTData(stack).getFloat("DamageMulti");
    }

    public void setDamageMulti(ItemStack stack, float damageMulti){
       NBTTagCompound n = getNBTData(stack);
       n.setFloat("DamageMulti", damageMulti);

        stack.setTagCompound(n);
    }

    public int getClick(ItemStack stack) {

        if (!getNBTData(stack).hasKey("ClickTime"))
        {
            getNBTData(stack).setInteger("ClickTime", 0);
        }

        return getNBTData(stack).getInteger("ClickTime");
    }

    public void setClick(ItemStack stack, int spamClickPoints){
        NBTTagCompound n = getNBTData(stack);
        n.setInteger("ClickTime", spamClickPoints);

        stack.setTagCompound(n);
    }

    public NBTTagCompound getNBTData(ItemStack stack) {
       NBTTagCompound nbt = stack.getTagCompound();

       if (nbt == null) {
           nbt = new NBTTagCompound();
       }

       return nbt;

    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }






}
