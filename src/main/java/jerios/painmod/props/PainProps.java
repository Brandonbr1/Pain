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

    public PainProps() {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound propertyData = new NBTTagCompound();
        // Write data to propertyData

        compound.setTag(PROP_NAME, propertyData);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        if (compound.hasKey(PROP_NAME, Constants.NBT.TAG_COMPOUND)) {
            NBTTagCompound propertyData = compound.getCompoundTag(PROP_NAME);

            // Read data from propertyData
        }

    }

    @Override
    public void init(Entity entity, World world) {

    }

    public static PainProps get(Entity p) {
        return (PainProps) p.getExtendedProperties(PROP_NAME);
    }

    public static class EventHandler {

        @SubscribeEvent
        public void entityConstruct(EntityEvent.EntityConstructing e) {
            if (e.entity instanceof EntityPlayer) {
                if (e.entity.getExtendedProperties(PROP_NAME) == null) {
                    e.entity.registerExtendedProperties(PROP_NAME, new PainProps());
                }
            }
        }

        @SubscribeEvent
        public void entityJoinWorld(EntityJoinWorldEvent e) {

        }

        @SubscribeEvent
        public void playerStartedTracking(PlayerEvent.StartTracking e) {

        }

        @SubscribeEvent
        public void onClonePlayer(PlayerEvent.Clone e) {
            if (e.wasDeath) {
                NBTTagCompound compound = new NBTTagCompound();
                PainProps.get(e.original).saveNBTData(compound);
                PainProps.get(e.entityPlayer).loadNBTData(compound);
            }

        }

    }
}
