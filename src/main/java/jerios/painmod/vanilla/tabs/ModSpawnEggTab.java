package jerios.painmod.vanilla.tabs;

import jerios.painmod.forge.PainShare;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ModSpawnEggTab extends CreativeTabs {
    public ModSpawnEggTab() {
        super(PainShare
                .MOD_ID_DOT + "spawnEggs");
    }

    @Override
    public Item getTabIconItem() {
        return Items.apple;
    }
}
