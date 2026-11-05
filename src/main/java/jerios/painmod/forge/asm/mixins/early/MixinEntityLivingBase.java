package jerios.painmod.forge.asm.mixins.early;

import jerios.painmod.core.effects.registry.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase {

    @Inject(method = "jump", at=@At("HEAD"), cancellable = true)
    private void painmod$preventJumpingWithPotion(CallbackInfo ci) {
        EntityLivingBase entityLivingBase = ((EntityLivingBase)(Object)this);

        if (entityLivingBase.isPotionActive(ModPotions.sloth)) {
            ci.cancel();
        }

    }







}
