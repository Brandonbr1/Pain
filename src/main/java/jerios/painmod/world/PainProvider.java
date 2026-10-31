package jerios.painmod.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jerios.painmod.client.world.PainSkyRender;
import jerios.painmod.client.world.PainWeatherRender;
import jerios.painmod.config.PainConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraftforge.client.IRenderHandler;

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
    public int getMoonPhase(long worldTime)
    {
        return 5;
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
                worldObj.getWorldInfo().setRainTime(9000);
                worldObj.getWorldInfo().setRaining(true);
                worldObj.getWorldInfo().setRainTime(9000);
                worldObj.getWorldInfo().setRaining(true);
                worldObj.getWorldInfo().setRainTime(9000);

                worldObj.prevRainingStrength = worldObj.rainingStrength;
                worldObj.rainingStrength = (float)((double)worldObj.rainingStrength + 0.01D);



                // So something here is bugged.....
                ///  How, what and why?

                /**
                int i = worldObj.getWorldInfo().getThunderTime();

                if (i <= 0)
                {
                    if (worldObj.getWorldInfo().isThundering())
                    {
                        worldObj.getWorldInfo().setThunderTime(worldObj.rand.nextInt(12000) + 3600);
                    }
                    else
                    {
                        worldObj.getWorldInfo().setThunderTime(worldObj.rand.nextInt(168000) + 12000);
                    }
                }
                else
                {
                    --i;
                    worldObj.getWorldInfo().setThunderTime(i);

                    if (i <= 0)
                    {
                        worldObj.getWorldInfo().setThundering(!worldObj.getWorldInfo().isThundering());
                    }
                }

                worldObj.prevThunderingStrength = worldObj.thunderingStrength;

                if (worldObj.getWorldInfo().isThundering())
                {
                    worldObj.thunderingStrength = (float)((double)worldObj.thunderingStrength + 0.01D);
                }
                else
                {
                    worldObj.thunderingStrength = (float)((double)worldObj.thunderingStrength - 0.01D);
                }

                worldObj.thunderingStrength = MathHelper.clamp_float(worldObj.thunderingStrength, 0.0F, 1.0F);
                int j = worldObj.getWorldInfo().getRainTime();

                if (j <= 0)
                {
                    if (worldObj.getWorldInfo().isRaining())
                    {
                        worldObj.getWorldInfo().setRainTime(worldObj.rand.nextInt(12000) + 12000);
                    }
                    else
                    {
                        worldObj.getWorldInfo().setRainTime(worldObj.rand.nextInt(168000) + 12000);
                    }
                }
                else
                {
                    --j;
                    worldObj.getWorldInfo().setRainTime(j);

                    if (j <= 0)
                    {
                        worldObj.getWorldInfo().setRaining(!worldObj.getWorldInfo().isRaining());
                    }
                }

                worldObj.prevRainingStrength = worldObj.rainingStrength;

                if (worldObj.getWorldInfo().isRaining())
                {
                    worldObj.rainingStrength = (float)((double)worldObj.rainingStrength + 0.01D);
                }
                else
                {
                    worldObj.rainingStrength = (float)((double)worldObj.rainingStrength - 0.01D);
                }

                worldObj.rainingStrength = MathHelper.clamp_float(worldObj.rainingStrength, 0.0F, 1.0F);
                 **/
            }

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
    public IRenderHandler getWeatherRenderer() {
        setWeatherRenderer(new PainWeatherRender());

        return super.getWeatherRenderer();
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

    @Override
    public Vec3 getSkyColor(Entity cameraEntity, float partialTicks) {
        return Vec3.createVectorHelper(000.0F, 000.0F, 000.0F);
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




}
