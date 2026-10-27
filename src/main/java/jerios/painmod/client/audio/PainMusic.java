package jerios.painmod.client.audio;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jerios.painmod.config.PainConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

@SideOnly(Side.CLIENT)
public class PainMusic extends MusicTicker {
    private final Random rand = new Random();
    private ISound isound;
    private int timer = 100;
    public Minecraft minecraft = FMLClientHandler.instance().getClient();
    public MusicTicker wrapedTicker;

    public PainMusic(MusicTicker wrapped) {
        super(FMLClientHandler.instance().getClient());
        this.wrapedTicker = wrapped;
    }


    @Override
    public void update()
    {
        //Fallback just in case it somehow does not get replaced back.
        if (minecraft.theWorld != null && minecraft.theWorld.provider.dimensionId != PainConfig.painDimensionID)
        {
            if (wrapedTicker != null)
            {
                wrapedTicker.update();
            }
        }

        PainMusic.MusicType musictype = MusicType.GAME;

        if (this.isound != null)
        {
            if (!musictype.getMusicTickerLocation().equals(this.isound.getPositionedSoundLocation()))
            {
                this.minecraft.getSoundHandler().stopSound(this.isound);
                this.timer = MathHelper.getRandomIntegerInRange(this.rand, 0, musictype.getMinDelay() / 2);
            }

            if (!this.minecraft.getSoundHandler().isSoundPlaying(this.isound))
            {
                this.isound = null;
                this.timer = Math.min(MathHelper.getRandomIntegerInRange(this.rand, musictype.getMinDelay(), musictype.getMaxDelay()), this.timer);
            }
        }

        if (this.isound == null && this.timer-- <= 0)
        {
            this.isound = PositionedSoundRecord.func_147673_a(musictype.getMusicTickerLocation());
            this.minecraft.getSoundHandler().playSound(this.isound);
            this.timer = Integer.MAX_VALUE;
        }
    }

    @SideOnly(Side.CLIENT)
    public static enum MusicType
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
