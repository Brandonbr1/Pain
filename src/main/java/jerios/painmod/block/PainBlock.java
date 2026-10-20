package jerios.painmod.block;

import jerios.painmod.registry.TabsRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class PainBlock extends Block {
    public PainBlock(Material materialIn, String name) {
        super(materialIn);
        setBlockName(name);
        setBlockTextureName("painmod:" + name);
        setCreativeTab(TabsRegistry.BLOCKS_TAB);
    }
}
