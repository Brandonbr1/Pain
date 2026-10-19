package jerios.painmod.world.teleport;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterHandler {


    /**
     * Teleports only Player to dim
     * @param playerMP
     * @param dimensionId
     * @param teleporter
     */
    public static void travelToDimension(EntityPlayerMP playerMP, int dimensionId, Teleporter teleporter)
    {

        playerMP.mcServer.getConfigurationManager().transferPlayerToDimension(playerMP, dimensionId, teleporter);
        playerMP.lastExperience = -1;
        playerMP.lastHealth = -1.0F;
        playerMP.lastFoodLevel = -1;

    }

    /**
     * Teleports only Entity to dim
     * @param ent
     * @param dimensionId
     * @param teleporter
     */
    public static void travelToDimension(Entity ent, int dimensionId, Teleporter teleporter)
    {
        if (!ent.worldObj.isRemote && !ent.isDead)
        {
            ent.worldObj.theProfiler.startSection("changeDimension");
            MinecraftServer minecraftserver = MinecraftServer.getServer();
            int originDim = ent.dimension;
            WorldServer worldserver = minecraftserver.worldServerForDimension(originDim);
            WorldServer worldserver1 = minecraftserver.worldServerForDimension(dimensionId);
            ent.dimension = dimensionId;

            if (originDim == 1 && dimensionId == 1)
            {
                worldserver1 = minecraftserver.worldServerForDimension(0);
                ent.dimension = 0;
            }

            ent.worldObj.removeEntity(ent);
            ent.isDead = false;
            ent.worldObj.theProfiler.startSection("reposition");
            minecraftserver.getConfigurationManager().transferEntityToWorld(ent, originDim, worldserver, worldserver1, teleporter);
            ent.worldObj.theProfiler.endStartSection("reloading");
            Entity entity = EntityList.createEntityByName(EntityList.getEntityString(ent), worldserver1);

            if (entity != null)
            {
                entity.copyDataFrom(ent, true);

                if (originDim == 1 && dimensionId == 1)
                {
                    ChunkCoordinates chunkcoordinates = worldserver1.getSpawnPoint();
                    chunkcoordinates.posY = ent.worldObj.getTopSolidOrLiquidBlock(chunkcoordinates.posX, chunkcoordinates.posZ);
                    entity.setLocationAndAngles((double)chunkcoordinates.posX, (double)chunkcoordinates.posY, (double)chunkcoordinates.posZ, entity.rotationYaw, entity.rotationPitch);
                }

                worldserver1.spawnEntityInWorld(entity);
            }

            ent.isDead = true;
            ent.worldObj.theProfiler.endSection();
            worldserver.resetUpdateEntityTick();
            worldserver1.resetUpdateEntityTick();
            ent.worldObj.theProfiler.endSection();
        }
    }


    /**
     * Travel EntityItem
     * @param ent
     * @param dimensionId
     * @param teleporter
     */

    public static void travelToDimension(EntityItem ent, int dimensionId, Teleporter teleporter)
    {
        travelToDimension((Entity)ent, dimensionId, teleporter);

        if (!ent.worldObj.isRemote)
        {
           ent.searchForOtherItemsNearby();
        }
    }

}
