package jerios.painmod.block.spawner;

import jerios.painmod.registry.TabsRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockPainSpawner extends Block{

    public BlockPainSpawner() {
        super(Material.ground);
        this.setBlockName("mobSpawner");
        this.disableStats();
        this.setBlockTextureName("mob_spawner");
        setCreativeTab(TabsRegistry.SPAWN_EGG_TAB);
    }
}
