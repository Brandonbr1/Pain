package jerios.painmod.effects.potion;

import jerios.painmod.utils.GRUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;

// given to by pain spiders if it's a rare version.
public class DeadlyPoision extends Potion {

    public DeadlyPoision(int id, int color) {
        super(id, true, color);
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {
        if (entityLivingBase.getHealth() > 1.0F)
        {
            entityLivingBase.attackEntityFrom(DamageSource.magic, 1.0F + amplifier + GRUtils.getScaledDifficulty(entityLivingBase.worldObj, entityLivingBase.worldObj.getGameRules()));
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        int k;
        k = 25 >> amplifier;
        return k > 0 ? duration % k == 0 : true;
    }

}
