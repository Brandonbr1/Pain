package jerios.painmod.core.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jerios.painmod.client.world.PainClouldRender;
import jerios.painmod.client.world.PainSkyRender;
import jerios.painmod.client.world.PainWeatherRender;
import jerios.painmod.forge.config.PainConfig;
import jerios.painmod.core.world.data.PainWorldData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

// MAKE STARS LOOKS CORRUPTED
// MAKE CLOUDS LOOKS UNCANNY
public class PainProvider extends WorldProvider {

  //  private IRenderHandler cachedWeatherRenderer = null;
 //   private IRenderHandler cachedSkyRenderer = null;


    @Override
    protected void registerWorldChunkManager() {
        this.worldChunkMgr = new PainChunkManager(worldObj);
        setDimension(PainConfig.painDimensionID);
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
        return 3;
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

    boolean initSky = false;

    @Override
    public IRenderHandler getSkyRenderer() {
        if (!initSky) {
            setSkyRenderer(new PainSkyRender());
            initSky = true;
        }

        return super.getSkyRenderer();
    }

    boolean initWeather = false;
    @Override
    public IRenderHandler getWeatherRenderer() {
        if (!initWeather) {
            setWeatherRenderer(new PainWeatherRender());
            initWeather = true;
        }

        return super.getWeatherRenderer();
    }

    boolean initClould = false;
    @Override
    public IRenderHandler getCloudRenderer() {

        if (!initClould) {
            setCloudRenderer(new PainClouldRender());
            initClould = true;
        }

        return super.getCloudRenderer();
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

    @Override
    public void calculateInitialWeather()
    {

            PainWorldData data = PainWorldData.get(worldObj);
            if (data.isRainingP())
            {
                worldObj.rainingStrength = 1.0F;

                if (data.isThundering())
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
                PainWorldData data = PainWorldData.get(worldObj);
                int thunderTime = data.getThunderTime();

                if (thunderTime <= 0)
                {
                    if (data.isThundering())
                    {
                        data.setThunderTime(worldObj.rand.nextInt(12000) + 3600);
                    }
                    else
                    {
                        data.setThunderTime(worldObj.rand.nextInt(168000) + 12000);
                    }
                }
                else
                {
                    --thunderTime;
                    data.setThunderTime(thunderTime);

                    if (thunderTime <= 0)
                    {
                        data.setThundering(!data.isThundering());
                    }
                }

                worldObj.prevThunderingStrength = worldObj.thunderingStrength;

                if (data.isThundering())
                {
                    worldObj.thunderingStrength = (float)((double)worldObj.thunderingStrength + 0.01D);
                }
                else
                {
                    worldObj.thunderingStrength = (float)((double)worldObj.thunderingStrength - 0.01D);
                }

                worldObj.thunderingStrength = MathHelper.clamp_float(worldObj.thunderingStrength, 0.0F, 1.0F);
                int rainTime = data.getRainTime();

                if (rainTime <= 0)
                {
                    if (data.isRainingP())
                    {
                        data.setRainTime(worldObj.rand.nextInt(12000) + 12000);
                    }
                    else
                    {
                        data.setRainTime(worldObj.rand.nextInt(168000) + 12000);
                    }
                }
                else
                {
                    --rainTime;
                    data.setRainTime(rainTime);

                    if (rainTime <= 0)
                    {
                        data.setRaining(!data.isRainingP());
                    }
                }

                worldObj.prevRainingStrength = worldObj.rainingStrength;

                if (data.isRainingP())
                {
                    worldObj.rainingStrength = (float)((double)worldObj.rainingStrength + 0.01D);
                }
                else {
                    worldObj.rainingStrength = (float)((double)worldObj.rainingStrength - 0.01D);
                }

                worldObj.rainingStrength = MathHelper.clamp_float(worldObj.rainingStrength, 0.0F, 1.0F);
            }

    }


    @Override
    public String getSaveFolder() {
        return "PainMod_Dimension";
    }
}
