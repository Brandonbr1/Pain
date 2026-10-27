package jerios.painmod.client.audio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

@SideOnly(Side.CLIENT)
public class PainMusic extends MusicTicker {
    private final Random field_147679_a = new Random();
    private ISound field_147678_c;
    private int field_147676_d = 100;
    public Minecraft field_147677_b;

    public PainMusic(Minecraft minecraft) {
        super(minecraft);
        this.field_147677_b = minecraft;
    }


    @Override
    public void update()
    {
        PainMusic.MusicType musictype = MusicType.GAME;

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

    @SideOnly(Side.CLIENT)
    public enum MusicType
    {
        GAME(new ResourceLocation("painmod:music.game"), 300, 900);
        private final ResourceLocation location;
        private final int minDelay;
        private final int maxDelay;


        private MusicType(ResourceLocation location, int minDelay, int maxDelay)
        {
            this.location = location;
            this.minDelay = minDelay;
            this.maxDelay = maxDelay;
        }

        public ResourceLocation getMusicTickerLocation()
        {
            return this.location;
        }

        public int getMinDelay()
        {
            return this.minDelay;
        }

        public int getMaxDelay()
        {
            return this.maxDelay;
        }
    }



}
