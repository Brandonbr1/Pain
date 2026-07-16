package jerios.painmod.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import jerios.painmod.items.ItemPainSpawnEgg;
import net.minecraft.item.Item;

public class ModItemBlocks {

    public static Item spawnEgg;

    public static void register() {
        spawnEgg = new ItemPainSpawnEgg().setCreativeTab(TabsRegistry.SPAWN_EGG_TAB);
        registerItem(spawnEgg, "spawn_egg");
    }



    private static void registerItem(Item item, String name) {
        GameRegistry.registerItem(item, name);
    }

}
