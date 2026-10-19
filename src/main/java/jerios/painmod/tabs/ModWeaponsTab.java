package jerios.painmod.tabs;

import jerios.painmod.PainConstants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ModWeaponsTab extends CreativeTabs {
    public ModWeaponsTab() {
        super(PainConstants.MOD_ID_DOT + "weapons");
    }

    @Override
    public Item getTabIconItem() {
        return Items.baked_potato;
    }
}
