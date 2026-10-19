package jerios.painmod.effects.potion;

import jerios.painmod.registry.ModPotions;
import jerios.painmod.utils.GRUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

import java.util.UUID;

public class SlothEffect extends BasePotion {

    public SlothEffect(int id, int color) {
        super(id, true, color);
        this.func_111184_a(SharedMonsterAttributes.movementSpeed, "9107DE5E-7CE8-4030-940E-514C1F160890", -0.29000000596046448D, 2);
    }

    @Override
    public String getName() {
        return "potion.sloth";
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

}
