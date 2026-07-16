package jerios.painmod.mixins;


import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder;

import javax.annotation.Nonnull;

public enum MixinsList implements IMixins {
    UNBURNING_ZOMBIES(new MixinBuilder()
            .setPhase(Phase.EARLY)
            .addCommonMixins("MixinEntityZombie")),
    UNBURNING_SKELETONS(new MixinBuilder()
            .setPhase(Phase.EARLY)
            .addCommonMixins("MixinEntitySkeleton")),
    CREEPER_INVOKER(new MixinBuilder()
            .setPhase(Phase.EARLY)
            .addCommonMixins("ICreeperAccess")),
    STRONGER_CREEPERS(new MixinBuilder()
            .setPhase(Phase.EARLY)
            .addCommonMixins("MixinEntityCreeper")),

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
