package jerios.painmod.core.items;

import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;

public class ItemCrash extends Item {


    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player) {

        throw new ReportedException(new CrashReport("Manually triggered debug crash From Item DEBUG ITEM ONLY", new Throwable()));

    }
}
