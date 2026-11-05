package jerios.painmod.vanilla.tabs;

import jerios.painmod.forge.PainShare;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ModItemsTab extends CreativeTabs {

    public ModItemsTab() {
        super(PainShare.MOD_ID_DOT + "items");
    }

    @Override
    public Item getTabIconItem() {
        return Items.coal;
    }
}
