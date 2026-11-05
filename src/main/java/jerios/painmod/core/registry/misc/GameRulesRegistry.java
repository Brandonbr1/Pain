package jerios.painmod.core.registry.misc;

import jerios.painmod.forge.PainShare;
import net.minecraft.world.GameRules;

public class GameRulesRegistry {

    public static void register(GameRules rules) {
        addGameRules(rules, "ExtraHard", "false");
    }

    public static void addGameRules(GameRules rules, String inputName, String output) {
        rules.addGameRule(PainShare.MOD_ID_DOT + inputName, output);
    }


}
