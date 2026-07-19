package jerios.painmod.entity;

import jerios.painmod.registry.ModPotions;
import jerios.painmod.utils.GRUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class PainSpider extends EntityCaveSpider {
    public PainSpider(World world) {
        super(world);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        float f = this.getBrightness(1.0F);

        if (f < 0.5) {
            double scaledSpeed = (double) GRUtils.getScaledDifficulty(worldObj, worldObj.getGameRules()) / 2;
            double currentMovementSpeed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + scaledSpeed;
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(currentMovementSpeed + 0.2F);
        }

    }

    public boolean attackEntityAsMob2(Entity entity)
    {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        int i = 0;

        if (entity instanceof EntityLivingBase)
        {
            f += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase)entity);
            i += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)entity);
        }

        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag)
        {
            if (i > 0)
            {
                entity.addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0)
            {
                entity.setFire(j * 4);
            }

            if (entity instanceof EntityLivingBase)
            {
                EnchantmentHelper.func_151384_a((EntityLivingBase)entity, this);
            }

            EnchantmentHelper.func_151385_b(this, entity);
        }

        return flag;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        if (attackEntityAsMob2(entity) /**super.attackEntityAsMob(entity)**/)
        {
            if (entity instanceof EntityLivingBase)
            {
                int duration = 5;

                float f = this.getBrightness(1.0F);

                if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL)
                {
                    duration += 7;
                }
                else if (this.worldObj.difficultySetting == EnumDifficulty.HARD)
                {
                    duration += 15;
                }

                if (f < 0.5F)
                {
                    duration += 8;
                }

                duration += GRUtils.getScaledDifficulty(worldObj, worldObj.getGameRules());


                if (duration > 0)
                {
                    ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(ModPotions.deadlyPoison.id, duration * 20, 0));
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    protected Entity findPlayerToAttack()
    {
        float f = this.getBrightness(1.0F);

        double d0;
        if (f < 0.5F)
        {
            d0 = 32.0D;
        }
        else
        {
            d0 = 16.0D;
        }
        return this.worldObj.getClosestVulnerablePlayerToEntity(this, d0);
    }
}
