package jerios.painmod.asm;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import jerios.painmod.mixins.MixinsList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class LateMixinsLoader implements ILateMixinLoader {
    @Override
    public String getMixinConfig() {
        return "mixins.painmod.early.json";
    }

    @Override
    public @NotNull List<String> getMixins(Set<String> loadedMods) {
        return IMixins.getLateMixins(MixinsList.class, loadedMods);
    }

}
