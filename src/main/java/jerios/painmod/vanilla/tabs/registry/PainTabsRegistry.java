package jerios.painmod.vanilla.tabs.registry;

import jerios.painmod.vanilla.tabs.ModBlocksTab;
import jerios.painmod.vanilla.tabs.ModItemsTab;
import jerios.painmod.vanilla.tabs.ModSpawnEggTab;
import jerios.painmod.vanilla.tabs.ModWeaponsTab;
import net.minecraft.creativetab.CreativeTabs;

public class PainTabsRegistry {

    public static final CreativeTabs SPAWN_EGG_TAB = new ModSpawnEggTab();
    public static final CreativeTabs ITEM_TAB = new ModItemsTab();
    public static final CreativeTabs WEAPONS_TAB = new ModWeaponsTab();
    public static final CreativeTabs BLOCKS_TAB = new ModBlocksTab();
}
