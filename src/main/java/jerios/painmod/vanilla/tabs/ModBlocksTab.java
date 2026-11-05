package jerios.painmod.vanilla.tabs;

import jerios.painmod.forge.PainShare;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ModBlocksTab extends CreativeTabs {
    public ModBlocksTab() {
        super(PainShare.MOD_ID_DOT + "Blocks");
    }

    @Override
    public Item getTabIconItem() {
        return Items.boat;
    }
}
