package jerios.painmod.registry;

import jerios.painmod.tabs.ModBlocksTab;
import jerios.painmod.tabs.ModItemsTab;
import jerios.painmod.tabs.ModSpawnEggTab;
import jerios.painmod.tabs.ModWeaponsTab;
import net.minecraft.creativetab.CreativeTabs;

public class TabsRegistry {

    public static final CreativeTabs SPAWN_EGG_TAB = new ModSpawnEggTab();
    public static final CreativeTabs ITEM_TAB = new ModItemsTab();
    public static final CreativeTabs WEAPONS_TAB = new ModWeaponsTab();
    public static final CreativeTabs BLOCKS_TAB = new ModBlocksTab();
}
