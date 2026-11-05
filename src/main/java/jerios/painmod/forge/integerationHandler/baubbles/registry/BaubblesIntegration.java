package jerios.painmod.forge.integerationHandler.baubbles.registry;

import jerios.painmod.forge.integerationHandler.baubbles.NourishmentAmulet;
import jerios.painmod.forge.integerationHandler.baubbles.NourishmentRing;
import jerios.painmod.core.items.registry.ModItems;
import net.minecraft.item.Item;

public class BaubblesIntegration {
    public static Item nourishmentAmulet;
    public static Item nourishmentRing;

    public static void integrate() {
        nourishmentAmulet = new NourishmentAmulet();
        ModItems.registerItem(nourishmentAmulet, "nourishment Amulet");

        nourishmentRing = new NourishmentRing();
        ModItems.registerItem(nourishmentRing, "nourishment Ring");


    }

}
