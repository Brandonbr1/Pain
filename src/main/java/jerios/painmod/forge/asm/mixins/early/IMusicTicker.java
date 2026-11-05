package jerios.painmod.forge.asm.mixins.early;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MusicTicker.class)
public interface IMusicTicker {

    @Accessor("field_147678_c")
    ISound getSound();



}
