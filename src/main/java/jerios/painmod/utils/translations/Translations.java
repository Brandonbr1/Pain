package jerios.painmod.utils.translations;

import net.minecraft.util.StatCollector;

public class Translations {

    public static String translate(String localString) {
        return (StatCollector.translateToLocal(localString)).trim();
    }


}
