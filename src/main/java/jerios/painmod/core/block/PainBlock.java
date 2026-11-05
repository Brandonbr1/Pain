package jerios.painmod.core.block;

import jerios.painmod.vanilla.tabs.registry.PainTabsRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class PainBlock extends Block {
    public PainBlock(Material materialIn, String name) {
        super(materialIn);
        setBlockName(name);
        setBlockTextureName("painmod:" + name);
        setCreativeTab(PainTabsRegistry.BLOCKS_TAB);
    }
}
