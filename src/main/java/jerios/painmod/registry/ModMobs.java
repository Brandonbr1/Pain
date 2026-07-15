package jerios.painmod.registry;

import cpw.mods.fml.common.registry.EntityRegistry;
import jerios.painmod.PainMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

public class ModMobs {

    public static void register() {

    }

    static int id = 1;

    public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange,
                                      int updateFrequency, boolean sendsVelocityUpdates, int spot1, int spot2) {
        EntityRegistry.registerModEntity(
                entityClass,
                entityName,
                id++,
                PainMod.INSTANCE,
                trackingRange,
                updateFrequency,
                sendsVelocityUpdates);
    }

    public static void registerEntityWithEgg(Class<? extends Entity> entityClass, String entityName, int trackingRange,
                                             int updateFrequency, boolean sendsVelocityUpdates, int spot1, int spot2) {
        EntityRegistry.registerModEntity(
                entityClass,
                entityName,
                id++,
                PainMod.INSTANCE,
                trackingRange,
                updateFrequency,
                sendsVelocityUpdates);
        //TODO: ADD SPAWN EGG

    }

    private static void addBiomes(Class<? extends EntityLiving> entityClass, int weightedProb, int min, int max,
                                  EnumCreatureType typeOfCreature, BiomeGenBase... biomes) {
        EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biomes);
    }

}
