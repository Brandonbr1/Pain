package jerios.painmod.tabs;

import jerios.painmod.PainConstants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ModBlocksTab extends CreativeTabs {
    public ModBlocksTab() {
        super(PainConstants.MOD_ID_DOT + "Blocks");
    }

    @Override
    public Item getTabIconItem() {
        return Items.boat;
    }
}
