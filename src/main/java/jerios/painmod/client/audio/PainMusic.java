package jerios.painmod.client.audio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class PainMusic {

    @SideOnly(Side.CLIENT)
    public enum MusicType
    {
        GAME(new ResourceLocation("painmod:music.game"), 300, 900),
        ESCAPED(new ResourceLocation("painmod:music.escaped"), 300, 900);


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
