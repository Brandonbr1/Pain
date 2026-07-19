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

    public static void syncConfig(Configuration con) {
            glutID = potionConfig(con, "Gluttony ", 24);
            malnourishment = potionConfig(con, "malnourishment ", 25);
            deadlyPoison = potionConfig(con, "Deadly Poison ", 26);
            plaugeEffect = potionConfig(con, "Plauge ", 27);
            rageEffect = potionConfig(con, "Wrath(Rage) ", 28);
            slothEffect = potionConfig(con, "Sloth ", 29);


        painDimensionID = con.getInt("Pain Dimension ID", "DIMENSION", freeID, Integer.MIN_VALUE, Integer.MAX_VALUE, "The Pain Dimension ID", "config.PainDimension");

        if (con.hasChanged()) {
            con.save();
        }


    }

    private static int potionConfig(Configuration co, String potionName, int defVal) {

        return co.getInt(potionName + " Potion ID", POTION_CAT, defVal, Integer.MIN_VALUE, Integer.MAX_VALUE, potionName + "Potion ID", potionName + "potion");
    }




}
