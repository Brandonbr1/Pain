package jerios.painmod.integerationHandler.baubbles;


import baubles.api.BaubleType;
import cpw.mods.fml.common.Optional;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "Baubles")
public class NourishmentRing extends BaubblesPain implements baubles.api.IBauble {


    public NourishmentRing() {
        super("nourishmentRing");
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.RING;
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
