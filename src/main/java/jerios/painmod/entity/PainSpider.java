package jerios.painmod.entity;

import jerios.painmod.registry.ModPotions;
import jerios.painmod.utils.GRUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class PainSpider extends EntitySpider {
    public PainSpider(World world) {
        super(world);
        this.setSize(0.7F, 0.5F);
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

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        if (super.attackEntityAsMob(entity))
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
