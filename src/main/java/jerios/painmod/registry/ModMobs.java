package jerios.painmod.registry;

import cpw.mods.fml.common.registry.EntityRegistry;
import jerios.painmod.PainConstants;
import jerios.painmod.PainMod;
import jerios.painmod.entity.PainCreeper;
import jerios.painmod.entity.PainSkeleton;
import jerios.painmod.entity.PainZombie;
import jerios.painmod.items.ItemPainSpawnEgg;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

public class ModMobs {

    public static void register() {
        registerEntityWithEgg(PainZombie.class, "painZombie", 128, 1,true, 5,5);
        registerEntityWithEgg(PainSkeleton.class, "PainSkeleton", 128, 1,true, 5,5);
        registerEntityWithEgg(PainCreeper.class, "PainCreeper", 128, 1,true, 5,5);
    }

    static int id = 1;

    public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange,
                                      int updateFrequency, boolean sendsVelocityUpdates) {
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
        ItemPainSpawnEgg.addSpawnEgg(id++, entityClass, PainConstants.MOD_ID_DOT + entityName, spot1, spot2);

    }

    private static void addBiomes(Class<? extends EntityLiving> entityClass, int weightedProb, int min, int max,
                                  EnumCreatureType typeOfCreature, BiomeGenBase... biomes) {
        EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biomes);
    }

}
