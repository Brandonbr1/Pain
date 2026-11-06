package jerios.painmod.forge.asm.mixins.early;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import jerios.painmod.forge.PainShare;
import jerios.painmod.forge.config.PainConfig;
import jerios.painmod.forge.props.PainProps;
import jerios.painmod.forge.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MusicTicker.class)
public class MixinMusicTicker {

   @Shadow private @Final Minecraft field_147677_b;
   @Shadow private ISound field_147678_c;
   @Unique
   boolean pain_Mod$playedEscapedMusic = false;


    @ModifyExpressionValue(method = "update", at= @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;func_147109_W()Lnet/minecraft/client/audio/MusicTicker$MusicType;"))
    private MusicTicker.MusicType painmod$changeMusicType(MusicTicker.MusicType original) {
        if (field_147677_b.theWorld != null && field_147677_b.thePlayer != null) {
            EntityClientPlayerMP player = field_147677_b.thePlayer;

            PainProps props = PainProps.get(player);


            if (field_147678_c != null){
                System.out.println(field_147678_c.getPositionedSoundLocation());

                if (field_147678_c.getPositionedSoundLocation().equals(new ResourceLocation("painmod:music.escaped")))
                {
                    if (!pain_Mod$playedEscapedMusic && field_147677_b.getSoundHandler().isSoundPlaying(field_147678_c))
                    {
                             PainShare.LOG.info("This is also a true statement v2");
                            pain_Mod$playedEscapedMusic = true;

                    }
                    PainShare.LOG.info("TRUE PLEASE");
                }
            }


            if (props != null)
            {
                if (field_147677_b.theWorld.provider.dimensionId == PainConfig.painDimensionID)
                {
                    return ClientProxy.PAIN_MUSIC;
                } else if (props.escapedDim && !pain_Mod$playedEscapedMusic) {
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