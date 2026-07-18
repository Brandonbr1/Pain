package jerios.painmod.utils;

import net.minecraft.util.StatCollector;

public class Translations {

    public static String translate(String localString) {
        return (StatCollector.translateToLocal(localString)).trim();
    }


}
