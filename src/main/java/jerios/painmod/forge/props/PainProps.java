package jerios.painmod.forge.props;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jerios.painmod.forge.PainMod;
import jerios.painmod.forge.PainShare;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants;

public class PainProps implements IExtendedEntityProperties {

    public static final String PROP_NAME = PainMod.MODID + "_EntityData";

    public boolean escapedDim = false;
    public int drinkMilk = 0;
    public int jumpscareTimer = 0;
    public int requiredJumpscareTimer = 18000;

    public PainProps() {
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound propertyData = new NBTTagCompound();
        // Write data to propertyData

        propertyData.setBoolean("escapedDim", this.escapedDim);
        propertyData.setInteger("drinkMilk", this.drinkMilk);
        propertyData.setInteger("jumpscareTimer", this.jumpscareTimer);
        propertyData.setInteger("requiredJumpscareTimer", this.requiredJumpscareTimer);

        compound.setTag(PROP_NAME, propertyData);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        if (compound.hasKey(PROP_NAME, Constants.NBT.TAG_COMPOUND)) {
            NBTTagCompound propertyData = compound.getCompoundTag(PROP_NAME);

           this.escapedDim = propertyData.getBoolean("escapedDim");
           this.drinkMilk = propertyData.getInteger("drinkMilk");
            this.jumpscareTimer = propertyData.getInteger("jumpscareTimer");
            this.requiredJumpscareTimer = propertyData.getInteger("requiredJumpscareTimer");
        }

    }

    @Override
    public void init(Entity entity, World world) {

    }

    @SideOnly(Side.CLIENT)
    public void setEscapedDim(boolean escapedDim) {
        this.escapedDim = escapedDim;
    //    PainShare.LOG.info("RUN ON CLIENT PAIN PROPS");
    }

    public static PainProps get(Entity p) {
        return (PainProps) p.getExtendedProperties(PROP_NAME);
    }

}
