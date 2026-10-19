package jerios.painmod.effects.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

// given to by pain spiders if it's a rare version.
public class DeadlyPoision extends BasePotion {

    public DeadlyPoision(int id, int color) {
        super(id, true, color);
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {
        if (entityLivingBase.getHealth() > 3.0F - amplifier)
        {
            entityLivingBase.attackEntityFrom(DamageSource.magic, 1.0F);
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
