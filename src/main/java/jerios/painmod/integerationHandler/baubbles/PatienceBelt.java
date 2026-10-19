package jerios.painmod.integerationHandler.baubbles;

import baubles.api.BaubleType;
import cpw.mods.fml.common.Optional;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "Baubles")
public class PatienceBelt extends BaubblesPain implements baubles.api.IBauble {

    public PatienceBelt() {
        super("patienceBelt");
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.BELT;
    }

    @Override
    public void onWornTick(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    @Override
    public void onEquipped(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    @Override
    public void onUnequipped(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    @Override
    public boolean canEquip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return false;
    }

    @Override
    public boolean canUnequip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return false;
    }
}
