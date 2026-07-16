package jerios.painmod.entity;

import jerios.painmod.PainConstants;
import jerios.painmod.effects.PainEffects;
import jerios.painmod.mixins.early.ICreeperAccess;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


public class PainCreeper extends EntityCreeper implements IPainMobs{

    public PainCreeper(World world) {
        super(world);
    }

    int explosionCount;

    public void explodeCreeper()
    {
        if (!this.worldObj.isRemote)
        {
            boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

            int explosionSize = ((ICreeperAccess)this).getExplosion();

            if (this.getPowered())
            {
                this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, (float)(explosionSize * 2), flag, flag);
                ((ICreeperAccess)this).setIgnitedTime(-50);
                explosionCount++;
            }
            else
            {
                this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, (float)explosionSize, flag, flag);
                ((ICreeperAccess)this).setIgnitedTime(-50);
                explosionCount++;
            }

            if (explosionCount > 5) {
                this.setDead();
            }
        }
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
        

        return super.onSpawnWithEgg(p_110161_1_);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setInteger(PainConstants.MOD_ID_DOT + "ExplosionTimes", explosionCount);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        tagCompund.getInteger(PainConstants.MOD_ID_DOT + "ExplosionTimes");
    }


    @Override
    public boolean getEffectsType() {
        return false;
    }

    @Override
    public boolean setEffectType() {
        return false;
    }

    @Override
    public String[] blacklisted() {
        return null;
    }
}
