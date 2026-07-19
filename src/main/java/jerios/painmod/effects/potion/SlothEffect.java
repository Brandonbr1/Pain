package jerios.painmod.effects.potion;

import jerios.painmod.utils.GRUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;

import java.util.UUID;

public class SlothEffect extends Potion {

    public SlothEffect(int id, int color) {
        super(id, true, color);
        this.func_111184_a(SharedMonsterAttributes.movementSpeed, "9107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448D, 2);
    }

    @Override
    public String getName() {
        return "TEST";
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
