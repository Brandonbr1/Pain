package jerios.painmod.sounds;

import net.minecraft.block.Block;

public class PainSoundType extends Block.SoundType {

    public PainSoundType(String name, float volume, float frequency) {
        super(name, volume, frequency);
    }

    @Override
    public String getBreakSound()
    {
        return "painmod:dig." + this.soundName;
    }

    @Override
    public String getStepResourcePath()
    {
        return "painmod:step." + this.soundName;
    }
}
