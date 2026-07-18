package jerios.painmod.asm;


import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;


@IFMLLoadingPlugin.MCVersion("1.7.10")
public class CoreLoadingPlugin implements IFMLLoadingPlugin /**, IEarlyMixinLoader **/{

    @Override
    public String[] getASMTransformerClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

 /**   @Override
    public String getMixinConfig() {
        return "mixins.painmod.early.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedCoreMods) {
        return IMixins.getEarlyMixins(MixinsList.class, loadedCoreMods);
    }
    **/

}