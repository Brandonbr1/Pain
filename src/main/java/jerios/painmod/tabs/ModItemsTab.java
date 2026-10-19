package jerios.painmod.tabs;

import jerios.painmod.PainConstants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ModItemsTab extends CreativeTabs {

    public ModItemsTab() {
        super(PainConstants.MOD_ID_DOT + "items");
    }

    @Override
    public Item getTabIconItem() {
        return Items.coal;
    }
}
