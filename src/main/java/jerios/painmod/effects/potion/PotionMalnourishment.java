package jerios.painmod.effects.potion;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Potion;

public class PotionMalnourishment extends Potion {
    public PotionMalnourishment(int id, int color) {
        super(id, true, color);
    }

    /** getAttackStrength**/
    @Override
    public double func_111183_a(int amplifier, AttributeModifier modifier)
    {
        return -1.5F * amplifier;
    }

}
