package jerios.painmod.tabs;

import jerios.painmod.PainConstants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ModSpawnEggTab extends CreativeTabs {
    public ModSpawnEggTab() {
        super(PainConstants
                .MOD_ID_DOT + "spawnEggs");
    }

    @Override
    public Item getTabIconItem() {
        return Items.apple;
    }
}
