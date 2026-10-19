package jerios.painmod.integerationHandler.baubbles;


import baubles.api.BaubleType;
import baubles.api.expanded.BaubleItemHelper;
import cpw.mods.fml.common.Optional;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "Baubles")
public class NourishmentAmulet extends BaubblesPain implements baubles.api.IBauble {

    public NourishmentAmulet() {
        super("nourishmentAmulet");
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.AMULET;
    }

    @Override
    public void onWornTick(ItemStack itemStack, EntityLivingBase entityLivingBase)
    {

    }

    @Override
    public void onEquipped(ItemStack itemStack, EntityLivingBase entityLivingBase)
    {
    }

    @Override
    public void onUnequipped(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    @Override
    public boolean canEquip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return true;
    }
}
