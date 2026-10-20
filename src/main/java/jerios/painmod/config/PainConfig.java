package jerios.painmod.config;

import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;

public class PainConfig {


    public static int glutID = 24;
    public static int malnourishment = 25;
    public static int deadlyPoison = 26;
    public static int plaugeEffect = 27;
    public static int rageEffect = 28;
    public static int slothEffect = 29;

    static int freeID = DimensionManager.getNextFreeDimId();
    public static int painDimensionID = freeID;

     static String POTION_CAT = "POTIONS";
     static String BIOME_ID = "BIOME IDS";

     public static String[] blacklistedPotionsHarmfulEssence;


     public static int painBiomeID;

    public static void syncConfig(Configuration con) {
            glutID = potionConfig(con, "Gluttony ", 24);
            malnourishment = potionConfig(con, "malnourishment ", 25);
            deadlyPoison = potionConfig(con, "Deadly Poison ", 26);
            plaugeEffect = potionConfig(con, "Plauge ", 27);
            rageEffect = potionConfig(con, "Wrath(Rage) ", 28);
            slothEffect = potionConfig(con, "Sloth ", 29);


            painDimensionID = con.getInt("Pain Dimension ID", "DIMENSION", freeID, Integer.MIN_VALUE, Integer.MAX_VALUE, "The Pain Dimension ID", "config.PainDimension");


            blacklistedPotionsHarmfulEssence = con.getStringList("Blacklisted Potions", "Blacklist", new String[]{}, "Potions name which is blacklisted from applying on Harmful essence. Instant damage is excluded automatically");

            painBiomeID = con.getInt("Pain Plains Biome ID", BIOME_ID, 80, 0, Integer.MAX_VALUE, "The Biome ID of the pain plains");


        if (con.hasChanged()) {
            con.save();
        }


    }

    private static int potionConfig(Configuration co, String potionName, int defVal) {

        return co.getInt(potionName + " Potion ID", POTION_CAT, defVal, Integer.MIN_VALUE, Integer.MAX_VALUE, potionName + "Potion ID", potionName + "potion");
    }




}
