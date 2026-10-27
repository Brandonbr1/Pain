package jerios.painmod.config;

import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;

public class PainConfig {

    public static float fogDensity = 0.3f;
    public static float fogDensityEnd = 0.8f;
    public static float fogDensityStart = 0.1f;

    public static float maxBrightnessInDim = 40;

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
    public static int painJungleID;

    public static int painDesertID;


     public static boolean spawnInPainDim;

    public static void syncConfig(Configuration con) {
            glutID = potionConfig(con, "Gluttony ", 24);
            malnourishment = potionConfig(con, "malnourishment ", 25);
            deadlyPoison = potionConfig(con, "Deadly Poison ", 26);
            plaugeEffect = potionConfig(con, "Plauge ", 27);
            rageEffect = potionConfig(con, "Wrath(Rage) ", 28);
            slothEffect = potionConfig(con, "Sloth ", 29);


            painDimensionID = con.getInt("Pain Dimension ID", "DIMENSION", freeID, Integer.MIN_VALUE, Integer.MAX_VALUE, "The Pain Dimension ID", "config.PainDimension");


            blacklistedPotionsHarmfulEssence = con.getStringList("Blacklisted Potions", "Blacklist", new String[]{"potion.harm"}, "Potions name which is blacklisted from applying on Harmful essence");

            painBiomeID = con.getInt("Pain Plains Biome ID", BIOME_ID, 80, 0, Integer.MAX_VALUE, "The Biome ID of the pain plains");
            painJungleID = con.getInt("Pain Jungle Biome ID", BIOME_ID, 81, 0, Integer.MAX_VALUE, "The Biome ID of the pain Jungle");
            painDesertID = con.getInt("Pain Desert Biome ID", BIOME_ID, 82, 0, Integer.MAX_VALUE, "The Biome ID of the Pain Desert");


        spawnInPainDim = con.getBoolean("Spawn in Pain Dim?", "Difficulty", true, "Should the player spawn in the dimension and have to escape it?");

            maxBrightnessInDim = con.getFloat("Max Brightness in Dim", "Visuals", 40.0F, 0.0F, 100.0F, "Max Brightness allowed when in Pain Dim");

        fogDensity = con.getFloat("Fog Density in Pain Dim", "Visuals", 1.0f, 0.0f, 10.0f, "Max Density of the fog in Pain Dim");

        fogDensityEnd = con.getFloat("Fog Density End in Pain Dim", "Visuals", 0.8f, 0.0f, 10.0f, "Max Density of the fog in Pain Dim End parameter");
        fogDensityStart =  con.getFloat("Fog Density Start in Pain Dim", "Visuals", 0.1f, 0.0f, 10.0f, "Max Density of the fog in Pain Dim Start parameter");


        if (con.hasChanged()) {
            con.save();
        }


    }

    private static int potionConfig(Configuration co, String potionName, int defVal) {

        return co.getInt(potionName + " Potion ID", POTION_CAT, defVal, Integer.MIN_VALUE, Integer.MAX_VALUE, potionName + "Potion ID", potionName + "potion");
    }




}
