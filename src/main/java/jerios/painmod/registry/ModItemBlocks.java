package jerios.painmod.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import jerios.painmod.items.ItemPainPotion;
import jerios.painmod.items.ItemPainSpawnEgg;
import net.minecraft.item.Item;

public class ModItemBlocks {

    public static Item spawnEgg;
    public static ItemPainPotion potionPain;

    public static void register() {
        spawnEgg = new ItemPainSpawnEgg().setCreativeTab(TabsRegistry.SPAWN_EGG_TAB);
        registerItem(spawnEgg, "spawn_egg");

        potionPain = (ItemPainPotion) new ItemPainPotion().setCreativeTab(TabsRegistry.SPAWN_EGG_TAB).setUnlocalizedName("potion").setTextureName("potion");
        registerItem(potionPain, "pain_potion");
    }



    private static void registerItem(Item item, String name) {
        GameRegistry.registerItem(item, name);
    }

}
