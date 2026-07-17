package jerios.painmod.entity;

import jerios.painmod.PainConstants;
import jerios.painmod.effects.sin.SinEffectGluttony;
import jerios.painmod.utils.GRUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;


public class PainCreeper extends EntityCreeper implements IPainMobs{

    public PainCreeper(World world) {
        super(world);
    }

    int explosionCount;
    int explosionAmm;
    private int explosionRadius = 3;
    boolean effectType;
    int effectIndex;

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        List<Entity> mobs = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(16,16,16));

        for (int i = 0; i < mobs.size(); i++) {
            Entity e = mobs.get(i);
            if (e instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) e;
                SinEffectGluttony.onPlayerAround(this, player);
            }
        }


    }


    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
        effectType = this.worldObj.rand.nextBoolean();

        if (!effectType)
        {
            double currentDamage = this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getBaseValue();
            double currentKnockBack = this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getBaseValue();
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.getMaxHealth() + (this.getMaxHealth() / 2) + this.rand.nextInt(12));
            this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(currentDamage + currentDamage / 2);
            this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(currentKnockBack + Math.min(this.rand.nextDouble(), 0.5));
        } else {
        }

        return super.onSpawnWithEgg(p_110161_1_);
    }

    @Override
    public void func_146077_cc()
    {
        if (!this.worldObj.isRemote)
        {
            boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
            int increased = GRUtils.getScaledDifficulty(this.worldObj, this.worldObj.getGameRules());
            explosionRadius += increased;
            explosionAmm += increased;


            if (this.getPowered())
            {
                explosionCount++;
                this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, (float)(this.explosionRadius * 2), flag, flag);
            }
            else
            {
                explosionCount++;
                this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius, flag, flag);
            }

            if (explosionCount > 5 + explosionAmm) {
                this.setDead();
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setInteger(PainConstants.MOD_ID_DOT + "ExplosionTimes", explosionCount);
        tagCompound.setBoolean(PainConstants.MOD_ID_DOT + "EffectType", effectType);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        tagCompund.getInteger(PainConstants.MOD_ID_DOT + "ExplosionTimes");
        tagCompund.getBoolean(PainConstants.MOD_ID_DOT + "EffectType");
    }

}
