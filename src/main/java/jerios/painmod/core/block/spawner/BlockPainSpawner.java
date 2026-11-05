package jerios.painmod.core.block.spawner;

import jerios.painmod.vanilla.tabs.registry.PainTabsRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockPainSpawner extends Block{

    public BlockPainSpawner() {
        super(Material.ground);
        this.setBlockName("mobSpawner");
        this.disableStats();
        this.setBlockTextureName("mob_spawner");
        setCreativeTab(PainTabsRegistry.SPAWN_EGG_TAB);
    }
}
