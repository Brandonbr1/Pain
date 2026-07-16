package jerios.painmod.mixins.early;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import jerios.painmod.entity.PainCreeper;
import jerios.painmod.entity.PainSkeleton;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityCreeper.class)
public abstract class MixinEntityCreeper extends EntityMob {

  @Shadow
  private int timeSinceIgnited;

    public MixinEntityCreeper(World world) {
        super(world);
    }

 /**   @ModifyExpressionValue(method = "func_146077_cc", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFZ)Lnet/minecraft/world/Explosion;", ordinal = 0))
    private Explosion painmod$IncreaseExplosion(Explosion original) {

        if (((EntityCreeper) (Object) this) instanceof PainCreeper) {
            original.explosionSize += 2F;
            original.isFlaming = true;
            timeSinceIgnited = 30;
        }

        return original;
    }

    @ModifyExpressionValue(method = "func_146077_cc", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFZ)Lnet/minecraft/world/Explosion;", ordinal = 1))
    private Explosion painmod$IncreaseExplosionNonCharged(Explosion original) {

        if (((EntityCreeper) (Object) this) instanceof PainCreeper) {
            original.explosionSize += 1.5F;
            original.isFlaming = true;
            timeSinceIgnited = 30;
        }

        return original;
    }
    **/

    @Redirect(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/monster/EntityCreeper;func_146077_cc()V"))
    private void painmod$useCustomMethodOrVanilla(EntityCreeper instance)
    {
        if (instance instanceof PainCreeper) {
            PainCreeper creeper = (PainCreeper) instance;
            creeper.explodeCreeper();
        } else {
            EntityCreeper creeper = ((EntityCreeper) (Object) this);
            ((ICreeperAccess)creeper).callOriginalMethod();
        }

    }


}
