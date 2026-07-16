package jerios.painmod.registry;

import jerios.painmod.PainConstants;
import net.minecraft.world.GameRules;

public class GameRulesRegistry {

    public static void register(GameRules rules) {
        addGameRules(rules, "ExtraHard", "false");
    }

    public static void addGameRules(GameRules rules, String inputName, String output) {
        rules.addGameRule(PainConstants.MOD_ID_DOT + inputName, output);
    }


}
