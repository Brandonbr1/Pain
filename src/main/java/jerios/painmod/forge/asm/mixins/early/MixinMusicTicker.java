package jerios.painmod.forge.asm.mixins.early;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import jerios.painmod.forge.config.PainConfig;
import jerios.painmod.forge.props.PainProps;
import jerios.painmod.forge.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MusicTicker.class)
public class MixinMusicTicker {

   @Shadow private @Final Minecraft field_147677_b;
   @Shadow private ISound field_147678_c;


    @ModifyExpressionValue(method = "update", at= @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;func_147109_W()Lnet/minecraft/client/audio/MusicTicker$MusicType;"))
    private MusicTicker.MusicType painmod$changeMusicType(MusicTicker.MusicType original) {
        if (field_147677_b.theWorld != null && field_147677_b.thePlayer != null) {
            EntityClientPlayerMP player = field_147677_b.thePlayer;

            PainProps props = PainProps.get(player);

            if (props != null) {

                if (field_147678_c != null) {

                    if (field_147678_c.getPositionedSoundLocation().equals(new ResourceLocation("painmod:music.escaped"))) {
                        if (!props.hasPlayedMusic1 && field_147677_b.getSoundHandler().isSoundPlaying(field_147678_c)) {
                            props.hasPlayedMusic1 = true;

                        }

                        if (props.hasPlayedMusic1 && !field_147677_b.getSoundHandler().isSoundPlaying(field_147678_c)) {
                            props.hasPlayedMusic2 = true;
                        }

                    }
                }


                if (field_147677_b.theWorld.provider.dimensionId == PainConfig.painDimensionID) {
                    return ClientProxy.PAIN_MUSIC;
                } else if (props.escapedDim && !props.hasPlayedMusic2) {
                    return ClientProxy.ESCAPED_MUSIC;
                } else {
                    return original;
                }

            }

        }


        return original;
    }


  /** @ModifyExpressionValue(method = "update", at= @At(value = "INVOKE", target = "Lnet/minecraft/client/audio/MusicTicker$MusicType;getMusicTickerLocation()Lnet/minecraft/util/ResourceLocation;", ordinal = 0))
    private ResourceLocation painmod$return(ResourceLocation original) {
       return original;
   }
  **/





}