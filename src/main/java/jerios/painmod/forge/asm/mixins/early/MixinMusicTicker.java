package jerios.painmod.forge.asm.mixins.early;

import jerios.painmod.client.audio.PainMusic;
import jerios.painmod.forge.config.PainConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(MusicTicker.class)
public class MixinMusicTicker implements IMusicTicker{

    @Shadow private @Final Random field_147679_a = new Random();
    @Shadow
    private @Final Minecraft field_147677_b;
    @Shadow  private ISound field_147678_c;
    @Shadow  private int field_147676_d = 100;

    @Inject(method = "update", at=@At("HEAD"), cancellable = true)
    private void painmod$injectMusicTicker(CallbackInfo ci) {
        if (field_147677_b.theWorld != null) {
            if (field_147677_b.theWorld.provider.dimensionId == PainConfig.painDimensionID) {
                painmod$runMusicTickerCode();
                ci.cancel();
            }
        }


    }


    @Unique
    private void painmod$runMusicTickerCode() {

        PainMusic.MusicType musictype = PainMusic.MusicType.GAME;

        if (this.field_147678_c != null)
        {
            if (!musictype.getMusicTickerLocation().equals(this.field_147678_c.getPositionedSoundLocation()))
            {
                this.field_147677_b.getSoundHandler().stopSound(this.field_147678_c);
                this.field_147676_d = MathHelper.getRandomIntegerInRange(this.field_147679_a, musictype.getMinDelay(), musictype.getMaxDelay() / 2);
            }

            if (!this.field_147677_b.getSoundHandler().isSoundPlaying(this.field_147678_c))
            {
                this.field_147678_c = null;
                this.field_147676_d = Math.min(MathHelper.getRandomIntegerInRange(this.field_147679_a, musictype.getMinDelay(), musictype.getMaxDelay()), this.field_147676_d);
            }
        }

        if (this.field_147678_c == null && this.field_147676_d-- <= 0)
        {
            this.field_147678_c = PositionedSoundRecord.func_147673_a(musictype.getMusicTickerLocation());
            this.field_147677_b.getSoundHandler().playSound(this.field_147678_c);
            this.field_147676_d = Integer.MAX_VALUE;
        }

    }


    @Override
    public ISound getSound() {
        return field_147678_c;
    }
}