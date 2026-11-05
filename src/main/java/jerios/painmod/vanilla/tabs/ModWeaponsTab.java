package jerios.painmod.vanilla.tabs;

import jerios.painmod.forge.PainShare;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ModWeaponsTab extends CreativeTabs {
    public ModWeaponsTab() {
        super(PainShare.MOD_ID_DOT + "weapons");
    }

    @Override
    public Item getTabIconItem() {
        return Items.baked_potato;
    }
}
