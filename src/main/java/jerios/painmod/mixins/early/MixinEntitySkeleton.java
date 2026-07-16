package jerios.painmod.mixins.early;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import jerios.painmod.entity.PainSkeleton;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntitySkeleton.class)
public abstract class MixinEntitySkeleton extends EntityMob implements IRangedAttackMob {

    @Shadow private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
    @Shadow private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);

    public MixinEntitySkeleton(World world) {
        super(world);
    }

    @Inject(method = "<init>", at = @At(value = "CTOR_HEAD"))
    private void painmod$replaceInstancesIfPainSkeleton(World world, CallbackInfo ci) {

        if (((EntitySkeleton) (Object) this) instanceof PainSkeleton) {
            aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 30, 35.0F);
            aiAttackOnCollide =  new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, true);
        }

    }



    @ModifyExpressionValue(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/monster/EntitySkeleton;getBrightness(F)F"))
    private float painmod$forcePainZombieToNotBurnIfPainSkeleton(float original) {

        if (((EntitySkeleton) (Object) this) instanceof PainSkeleton) {
            return 0.0F;
        }

        return original;
    }
}
