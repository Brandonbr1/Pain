package jerios.painmod.forge.asm.mixins;


import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder;

import javax.annotation.Nonnull;

public enum MixinsList implements IMixins {
    MUSIC_TICKER(new MixinBuilder()
            .setPhase(Phase.EARLY)
            .addClientMixins("MixinMusicTicker")),

    MUSIC_INVOKER(new MixinBuilder()
            .setPhase(Phase.EARLY)
            .addClientMixins("IMusicTicker")),

    POTION_EFFECTS(new MixinBuilder()
            .setPhase(Phase.EARLY)
            .addCommonMixins("MixinEntityLivingBase")),


    ;




    private final MixinBuilder builder;

    MixinsList(MixinBuilder builder) {
        this.builder = builder;
    }

    @Nonnull
    @Override
    public MixinBuilder getBuilder() {
        return builder;
    }




}