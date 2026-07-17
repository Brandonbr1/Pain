package jerios.painmod.utils;

import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class GRUtils {

    public static boolean isGameRuleTrue(GameRules rules, String gameRuleName) {
        return rules.getGameRuleBooleanValue(gameRuleName);
    }

    public static boolean isExtraHardEnabled(GameRules rules) {
        return isGameRuleTrue(rules, "ExtraHard");
    }

    public static int getScaledDifficulty(World world, GameRules rules) {
        if (isExtraHardEnabled(rules)) {
            return world.difficultySetting.getDifficultyId();
        }
        return 0;
    }

}
