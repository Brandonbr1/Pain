package jerios.painmod.mixins.early;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import jerios.painmod.Share;
import jerios.painmod.effects.potion.IPainnPotion;
import jerios.painmod.effects.potion.PainBasePotion;
import jerios.painmod.registry.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(EntityLivingBase.class)
public class MixinEntityLivingBase {

    @Shadow
    public void addPotionEffect(PotionEffect potioneffect)
    {

    }


    @WrapOperation(method = "curePotionEffects", at= @At(value = "INVOKE", target = "Ljava/util/Iterator;remove()V"), remap = false)
    private void painmod$reduceStatsIfPainEffects(Iterator<Integer> instance, Operation<Void> original,
                                     @Local(ordinal = 0) Integer potionID, @Local(ordinal = 0) PotionEffect effect) {


        Potion pot = Potion.potionTypes[potionID];

        if (pot instanceof IPainnPotion)
        {
            original.call(instance);
            addPotionEffect(new PotionEffect(effect.getPotionID(), (int) (effect.getDuration() * 0.5), 0    ));

        } else {
            original.call(instance);
        }



    }

    @WrapOperation(method = "curePotionEffects", at= @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;onFinishedPotionEffect(Lnet/minecraft/potion/PotionEffect;)V"), remap = false)
    private void painmod$reduceStatsIfPainEffects(EntityLivingBase instance, PotionEffect effect, Operation<Void> original
            ,@Local(ordinal = 0) Integer potionID) {



        Potion pot = Potion.potionTypes[potionID];

        if (pot instanceof IPainnPotion)
        {

        } else {
            original.call(instance, effect);
        }


    }

}
