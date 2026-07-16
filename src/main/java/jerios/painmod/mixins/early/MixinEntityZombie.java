package jerios.painmod.mixins.early;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import jerios.painmod.entity.PainZombie;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityZombie.class)
public abstract class MixinEntityZombie extends EntityMob {

    public MixinEntityZombie(World world) {
        super(world);
    }

    @ModifyExpressionValue(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/monster/EntityZombie;getBrightness(F)F"))
    public float painmod$forcePainZombieToNotBurnIfPainZombie(float original) {

        if (((EntityZombie) (Object) this) instanceof PainZombie) {
            return 0.0F;
        }

        return original;
    }

}
