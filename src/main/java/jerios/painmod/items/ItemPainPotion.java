package jerios.painmod.items;

import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemPainPotion extends ItemPotion {

    public static Map<Integer,PotionsProps> integerPotionsPropsMap = new HashMap<>();

    public ItemPainPotion()
    {
        super();
    }


    /** Ignore the vanilla list and impl, it is near unusable and so awful **/
    public static void addToMap(int id, String potionName, int color1, int color2, String duration, int potency, PotionEffect potionEffect, Potion potion, List<ItemStack> requireItems) {
        integerPotionsPropsMap.put(id, new PotionsProps(potionName, color1, color2, duration, potency, potionEffect, potion, requireItems));
    }



    public static class PotionsProps{
        public String potionName;
        public int color1;
        public int color2;
        public String duration;
        public int potency;
        public PotionEffect effect;
        public Potion potion;
        public List<ItemStack> requiredItems;


        public PotionsProps(String potionName, int color1, int color2, String duration, int potency, PotionEffect potionEffect, Potion potion, List<ItemStack> requiredItems) {
            this.potionName =potionName;
            this.color1 = color1;
            this.color2 = color2;
            this.duration = duration;
            this.potency = potency;
            this.effect = potionEffect;
            this.potion = potion;
            this.requiredItems = requiredItems;
        }
    }

}
