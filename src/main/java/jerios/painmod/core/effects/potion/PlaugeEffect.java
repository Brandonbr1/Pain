package jerios.painmod.core.effects.potion;

import jerios.painmod.utils.gamerules.GRUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class PlaugeEffect extends PainBasePotion implements IPainnPotion{

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
