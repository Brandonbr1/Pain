package jerios.painmod.utils;

import net.minecraft.world.GameRules;

public class GamerRulesUntil {

    public boolean isGameRuleTrue(GameRules rules, String gameRuleName) {
        return rules.getGameRuleBooleanValue(gameRuleName);
    }

    public boolean isExtraHardEnabled(GameRules rules) {
        return isGameRuleTrue(rules, "ExtraHard");
    }

}
