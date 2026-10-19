package jerios.painmod.integerationHandler.baubbles;

import baubles.api.expanded.BaubleItemHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BaubblesPain extends Item {

    public BaubblesPain(String name) {
        this.setCreativeTab(CreativeTabs.tabTools);
        this.setMaxStackSize(1);
        this.setUnlocalizedName(name);
    }


    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player) {
        return BaubleItemHelper.onBaubleRightClick(itemStackIn, worldIn, player);
    }

    @Override
    public boolean hasEffect(ItemStack itemStack, int a) {
        return true;
    }


}
