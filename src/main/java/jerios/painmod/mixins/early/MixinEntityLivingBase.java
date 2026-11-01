package jerios.painmod.mixins.early;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import jerios.painmod.config.PainConfig;
import jerios.painmod.effects.potion.IPainnPotion;
import jerios.painmod.props.PainProps;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashMap;
import java.util.Iterator;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase {


    @Shadow
    public abstract void addPotionEffect(PotionEffect potioneffect);

    /**
     * If the player has the malnourishment effect, return instead only a potency of 1
     * and half time if it is a good effect.
     * This applies for if this existing potion effect **does** exist.
     */
    @WrapOperation(method = "addPotionEffect", at= @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;onChangedPotionEffect(Lnet/minecraft/potion/PotionEffect;Z)V"))
    private void painmod$weakPotionEffectIfMalnurishmentIfAlreadyExists(EntityLivingBase instance, PotionEffect id, boolean p_70695_2_, Operation<Void> original)
    {
        Potion potion = Potion.potionTypes[id.getPotionID()];
       if ((instance.isPotionActive(PainConfig.malnourishment)) && !potion.isBadEffect())
       {
           PotionEffect effect = new PotionEffect(id.getPotionID(), id.getDuration() / 2, 0);
           original.call(instance, effect, p_70695_2_);
       }
       else {
           original.call(instance, id, p_70695_2_);
       }

    }



    /**
     * If the player has the malnourishment effect, return instead only a potency of 1
     * and half time if it is a good effect.
     * This applies for if this existing potion effect **does not** exist.
     */
    @WrapOperation(method = "addPotionEffect", at= @At(value = "INVOKE", target = "Ljava/util/HashMap;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object painmod$weakenEffectsIfMalnurishmentNotIfAlreadyExists(HashMap instance, Object inte, Object ef, Operation<Object> original) {

        if (((EntityLivingBase)(Object)this).isPotionActive(PainConfig.malnourishment)  )
        {
            PotionEffect effectp= (PotionEffect) ef;
            int d =  effectp.getDuration() / 2;
         //   int effM = effectp.getAmplifier();
            Potion p = Potion.potionTypes[effectp.getPotionID()];

         //   PotionEffect effect = new PotionEffect((Integer) inte, d, effM);
            if (!p.isBadEffect()) {
             PotionEffect  effect = new PotionEffect((Integer) inte, d, 0);

                return original.call(instance, inte, effect);
            }

            return original.call(instance, inte, effectp);
        }


        return original.call(instance, inte,ef);
    }

    @WrapOperation(method = "addPotionEffect", at= @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;onNewPotionEffect(Lnet/minecraft/potion/PotionEffect;)V"))
    private void painmod$onAddNewPotionEffect(EntityLivingBase instance, PotionEffect theEffects, Operation<Void> original) {

        Potion potion = Potion.potionTypes[theEffects.getPotionID()];

        EntityLivingBase elb = ((EntityLivingBase)(Object)this);

        if (potion instanceof IPainnPotion)
        {

            if (elb instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) elb;

                PainProps props = PainProps.get(player);

                if (props != null) {

                    if (props.drinkMilk > 3) {
                        original.call(instance, theEffects);
                        props.drinkMilk = 0;
                    } else {
                        props.drinkMilk++;
                    }


                }
            }


        } else {
            original.call(instance, theEffects);
        }
    }


    /**
     * If the potion effect is an instance of the Pain Mod potion effects,
     * only reduce the amplification to the lowest potency and half the time.
     * If milk is drunk more than 3 times, outright remove the effects.
     */
    @WrapOperation(method = "curePotionEffects", at= @At(value = "INVOKE", target = "Ljava/util/Iterator;remove()V"), remap = false)
    private void painmod$modifyRemovalConditions(Iterator instance, Operation<Void> original,
                              @Local PotionEffect theEffects) {

        Potion potion = Potion.potionTypes[theEffects.getPotionID()];

        EntityLivingBase elb = ((EntityLivingBase)(Object)this);

        if (potion instanceof IPainnPotion)
        {

            if (elb instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) elb;

                PainProps props = PainProps.get(player);

                if (props != null) {

                    if (props.drinkMilk > 3) {
                        original.call(instance);
                        props.drinkMilk = 0;
                    } else {
                        props.drinkMilk++;

                        original.call(instance);

                        int lowerDuration = theEffects.getDuration() / 2;

                        addPotionEffect(new PotionEffect(potion.getId(),lowerDuration, 0));
                    }


                }
            }


        } else {
                original.call(instance);
        }



    }

    /**
     * If the potion effect is an instance of the Pain Mod potion effects,
     * only reduce the amplification to the lowest potency and half the time.
     * If milk is drunk more than 3 times, outright remove the effects.
     */
    @WrapOperation(method = "curePotionEffects", at= @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;onFinishedPotionEffect(Lnet/minecraft/potion/PotionEffect;)V"), remap = false)
    private void painmod$onFinishPotionModifications(EntityLivingBase instance, PotionEffect theEffects, Operation<Void> original) {

        Potion potion = Potion.potionTypes[theEffects.getPotionID()];

        EntityLivingBase elb = ((EntityLivingBase)(Object)this);

        if (potion instanceof IPainnPotion)
        {

            if (elb instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) elb;

                PainProps props = PainProps.get(player);

                if (props != null) {

                    if (props.drinkMilk > 3) {
                        original.call(instance, theEffects);
                        props.drinkMilk = 0;
                    } else {
                        props.drinkMilk++;
                    }


                }
            }


        } else {
            original.call(instance, theEffects);
        }



    }


}
