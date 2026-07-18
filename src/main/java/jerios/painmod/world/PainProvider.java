package jerios.painmod.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

//TODO: MAKE THE MOON LOOKS CORRUPTED AND BROKEN IF POSSIBLE
// MAKE STARS LOOKS CORRUPTED
// MAKE CLOUDS LOOKS UNCANNY
// MAKE VOID FOG APPEAR RIGHT NEAR THE SURFACE
public class PainProvider extends WorldProvider {

    @Override
    protected void registerWorldChunkManager() {
        this.worldChunkMgr = new PainChunkManager(worldObj);
    }

    @Override
    public IChunkProvider createChunkGenerator()
    {
        return new PainChunkProviderGenerate(
                worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled()

        );
    }

    @Override
    public String getWelcomeMessage()
    {
        return "You have entered in a mysterious place. Very dark and ominous";
    }

    @Override
    public String getDepartMessage()
    {
        return "YOU HAVE FINALLY ESCAPED THE PAIN DIMENSION";
    }

    // Make is useless for traveling...
    @Override
    public double getMovementFactor()
    {
        return 0.1f;
    }

    //TODO: FIGURE OUT HOW THIS WORKS
    protected void generateLightBrightnessTable()
    {
        float f = 0.1F;

        for (int i = 0; i <= 15; ++i)
        {
            float f1 = 1.0F - (float)i / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
        }
    }

    //TODO: FIGURE OUT GOOD VALUE
    @SideOnly(Side.CLIENT)
    public double getVoidFogYFactor()
    {
        return 0.5D;
    }


    @Override
    public boolean canCoordinateBeSpawn(int par1, int par2) {
        return true;
    }


    // make it really dark
    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks)
    {
        return 0.5F;
    }

    @Override
    public String getDimensionName() {
        return "PainDimension";
    }

    @Override
    public boolean isSurfaceWorld()
    {
        return false;
    }

    @Override
    public int getRespawnDimension(EntityPlayerMP player)
    {
        return 5;
    }

    @SideOnly(Side.CLIENT)
    public boolean getWorldHasVoidParticles()
    {
        return true;
    }
}
