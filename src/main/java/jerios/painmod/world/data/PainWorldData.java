package jerios.painmod.world.data;

import jerios.painmod.PainMod;
import jerios.painmod.Share;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

// stores weatherdata
public class PainWorldData extends WorldSavedData {

    private static final String DATA_NAME = PainMod.MODID + "_WorldData";

    public PainWorldData() {
        super(DATA_NAME);
        this.randTime = 0;
    }

    public PainWorldData(String name) {
        super(DATA_NAME);
    }

    private int rainTime;
    private boolean isRaining;
    private boolean isThunder;
    private int thunderTime;
    private int randTime;
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        rainTime = nbt.getInteger("PainRainTime");
        isRaining = nbt.getBoolean("PainIsRaining");
        isThunder = nbt.getBoolean("PainIsThunder");
        thunderTime = nbt.getInteger("thunderTime");
        randTime= nbt.getInteger("randTime");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("PainRainTime", rainTime);
        nbt.setBoolean("PainIsRaining", isRaining);
        nbt.setBoolean("PainIsThunder", isThunder);
        nbt.setInteger("thunderTime", thunderTime);
        nbt.setInteger("randTime", randTime);
    }

    public void setRaining(boolean isRaining) {
        this.isRaining = isRaining;
        markDirty();
    }

    public void setRandTime(int time) {
        this.randTime =time;
        markDirty();
    }

    public int getRandTime() {
        return randTime;
    }

    public boolean isRainingP() {
        return this.isRaining;
    }

    public void setRainTime(int rainTime) {
        this.rainTime = rainTime;
        markDirty();
    }

    public int getRainTime() {
        return this.rainTime;
    }

    public void setThunderTime(int thunderTime) {
        this.thunderTime = thunderTime;
        markDirty();
    }

    public int getThunderTime() {
        return this.thunderTime;
    }

    public boolean isThundering() {
        return isThunder;
    }

    public void setThundering(boolean state) {
        this.isThunder = state;
        markDirty();
    }


    public static PainWorldData get(World world) {
        if (world.isRemote){
            Share.LOG.info("ran from client");
        }
        MapStorage storage =  world.perWorldStorage;
        PainWorldData instance = (PainWorldData) storage.loadData(PainWorldData.class, DATA_NAME);

        if (instance == null) {
            instance = new PainWorldData();
            storage.setData(DATA_NAME, instance);
        }
        return instance;
    }


}
