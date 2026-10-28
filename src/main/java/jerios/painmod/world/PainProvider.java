package jerios.painmod.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jerios.painmod.Share;
import jerios.painmod.client.world.PainSkyRender;
import jerios.painmod.config.PainConfig;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.WorldInfo;
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

    @Override
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
        setSkyRenderer(new PainSkyRender());


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
        return "I AM FINALLY FREE.";
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
        return 1.0F;
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
    public void calculateInitialWeather()
    {
        calculateInitialWeatherBody();
    }

    public void calculateInitialWeatherBody()
    {
        if (worldObj.getWorldInfo().isRaining())
        {
            worldObj.rainingStrength = 1.0F;

            if (worldObj.getWorldInfo().isThundering())
            {
                worldObj.thunderingStrength = 1.0F;
            }
        }
    }

    @Override
    public void updateWeather()
    {
        updateWeatherBody();
    }



    public void updateWeatherBody()
    {

            if (!worldObj.isRemote)
            {

                WorldServerMulti worldserver = (WorldServerMulti) MinecraftServer.getServer().worldServers[PainConfig.painDimensionID];
                DerivedWorldInfo worldinfo = (DerivedWorldInfo) worldserver.getWorldInfo();


                int i =  worldinfo.getThunderTime();


                int sd = ((DerivedWorldInfo)worldserver.getWorldInfo()).getRainTime();
                Share.LOG.info("IN WORLD TIMER    "+sd);

                // worldinfo.getThunderTime();

                if (i <= 0)
                {
                    if (worldinfo.isThundering())
                    {
                        ((DerivedWorldInfo)worldserver.getWorldInfo()).setThunderTime(worldserver.rand.nextInt(12000) + 3600);
                    }
                    else
                    {
                        ((DerivedWorldInfo)worldserver.getWorldInfo()).setThunderTime(worldserver.rand.nextInt(168000) + 12000);
                    }
                }
                else
                {
                    --i;
                    ((DerivedWorldInfo)worldserver.getWorldInfo()).setThunderTime(i);

                    if (i <= 0)
                    {
                        ((DerivedWorldInfo)worldserver.getWorldInfo()).setThundering(!((DerivedWorldInfo)worldserver.getWorldInfo()).isThundering());
                    }
                }

                worldserver.prevThunderingStrength = worldserver.thunderingStrength;

                if (((DerivedWorldInfo)worldserver.getWorldInfo()).isThundering())
                {
                    worldserver.thunderingStrength = (float)((double)worldserver.thunderingStrength + 0.01D);
                }
                else
                {
                    worldserver.thunderingStrength = (float)((double)worldserver.thunderingStrength - 0.01D);
                }

                worldserver.thunderingStrength = MathHelper.clamp_float(worldserver.thunderingStrength, 0.0F, 1.0F);
                int j = ((DerivedWorldInfo)worldserver.getWorldInfo()).getRainTime();

                if (j <= 0)
                {
                    if (((DerivedWorldInfo)worldserver.getWorldInfo()).isRaining())
                    {
                        ((DerivedWorldInfo)worldserver.getWorldInfo()).setRainTime(worldserver.rand.nextInt(12000) + 12000);
                    }
                    else
                    {
                        ((DerivedWorldInfo)worldserver.getWorldInfo()).setRainTime(worldserver.rand.nextInt(168000) + 12000);
                    }
                }
                else
                {
                    --j;
                    ((DerivedWorldInfo)worldserver.getWorldInfo()).setRainTime(j);

                    if (j <= 0)
                    {
                        ((DerivedWorldInfo)worldserver.getWorldInfo()).setRaining(!((DerivedWorldInfo)worldserver.getWorldInfo()).isRaining());
                    }
                }

                worldserver.prevRainingStrength = worldserver.rainingStrength;

                if (((DerivedWorldInfo)worldserver.getWorldInfo()).isRaining())
                {
                    worldserver.rainingStrength = (float)((double)worldserver.rainingStrength + 0.01D);
                }
                else
                {
                    worldserver.rainingStrength = (float)((double)worldserver.rainingStrength - 0.01D);
                }

                worldserver.rainingStrength = MathHelper.clamp_float(worldserver.rainingStrength, 0.0F, 1.0F);
            }

    }


}
