package jerios.painmod.props;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import jerios.painmod.PainMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PainProps implements IExtendedEntityProperties {

    public static final String PROP_NAME = PainMod.MODID + "_EntityData";

    public boolean escapedDim = false;

    public PainProps() {
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound propertyData = new NBTTagCompound();
        // Write data to propertyData

        propertyData.setBoolean("escapedDim", escapedDim);

        compound.setTag(PROP_NAME, propertyData);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        if (compound.hasKey(PROP_NAME, Constants.NBT.TAG_COMPOUND)) {
            NBTTagCompound propertyData = compound.getCompoundTag(PROP_NAME);

           this.escapedDim = propertyData.getBoolean("escapedDim");
            // Read data from propertyData
        }

    }

    @Override
    public void init(Entity entity, World world) {

    }

    public static PainProps get(Entity p) {
        return (PainProps) p.getExtendedProperties(PROP_NAME);
    }

}
