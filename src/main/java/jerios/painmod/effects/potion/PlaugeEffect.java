package jerios.painmod.effects.potion;

import jerios.painmod.utils.GRUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;

public class PlaugeEffect extends Potion {

    public PlaugeEffect(int id, int color) {
        super(id, true, color);
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {
       entityLivingBase.attackEntityFrom(DamageSource.magic, 2.0F + amplifier + GRUtils.getScaledDifficulty(entityLivingBase.worldObj, entityLivingBase.worldObj.getGameRules()) * 0.5f );
    }


    @Override
    public boolean isReady(int duration, int amplifier)
    {
        int k;
        k = 40 >> amplifier;
        return k > 0 ? duration % k == 0 : true;
    }


}
