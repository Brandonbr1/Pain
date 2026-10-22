package jerios.painmod.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jerios.painmod.client.world.PainSkyRender;
import jerios.painmod.config.PainConfig;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

import java.util.Random;

//TODO: MAKE THE MOON LOOKS CORRUPTED AND BROKEN IF POSSIBLE
// MAKE STARS LOOKS CORRUPTED
// MAKE CLOUDS LOOKS UNCANNY
// MAKE VOID FOG APPEAR RIGHT NEAR THE SURFACE
public class PainProvider extends WorldProvider {


    @Override
    protected void registerWorldChunkManager() {
        this.worldChunkMgr = new PainChunkManager(worldObj);
        this.dimensionId = PainConfig.painDimensionID;
        this.hasNoSky = true;
    }

    @Override
    public IChunkProvider createChunkGenerator()
    {
        return new PainChunkProviderGenerate(
                worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled()

        );
    }


    public boolean shouldMapSpin(String entity, double x, double y, double z)
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float skyAngle, float tickDelta)
    {
        return Vec3.createVectorHelper(000.0F, 000.0F, 000.0F);
    }

    @Override
    public boolean isDaytime() {
        return false;
    }

    @Override
    public IRenderHandler getSkyRenderer() {
        return super.getSkyRenderer();
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

    @Override
    public boolean canCoordinateBeSpawn(int x, int z)
    {
        return this.worldObj.getTopBlock(x, z).getMaterial().blocksMovement();
    }

    @Override
    public float getSunBrightnessFactor(float par1) {
        return 0.0F;
    }

    /**
     * Returns array with sunrise/sunset colors
     */
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks)
    {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public boolean isSkyColored()
    {
        return false;
    }

    // make it really dark
    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks)
    {
        return 0.5F;
    }

    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float par1)
    {
        return 0.0F;
    }

    /**
     * Gets the Star Brightness for rendering sky.
     * */
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
        return 0.0F;
    }

    @Override
    public String getDimensionName() {
        return "PainMod_Dimension";
    }


    @SideOnly(Side.CLIENT)
    public boolean getWorldHasVoidParticles()
    {
        return true;
    }

    //TODO: FIGURE OUT GOOD VALUE
    @SideOnly(Side.CLIENT)
    public double getVoidFogYFactor()
    {
        return 0.5D;
    }

    // Make is useless for traveling...
    @Override
    public double getMovementFactor()
    {
        return 1.0f;
    }

    @Override
    public int getRespawnDimension(EntityPlayerMP player)
    {
        return PainConfig.painDimensionID;
    }


    @Override
    public boolean canDoLightning(Chunk chunk) {
        if (new Random().nextBoolean()) {
            return super.canDoLightning(chunk);
        }
        return false;
    }
}
