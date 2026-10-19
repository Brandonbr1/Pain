package jerios.painmod.integerationHandler.baubbles.registry;

import jerios.painmod.integerationHandler.baubbles.NourishmentAmulet;
import jerios.painmod.integerationHandler.baubbles.NourishmentRing;
import jerios.painmod.registry.ModItemBlocks;
import net.minecraft.item.Item;

public class BaubblesIntegration {
    public static Item nourishmentAmulet;
    public static Item nourishmentRing;

    public static void integrate() {
        nourishmentAmulet = new NourishmentAmulet();
        ModItemBlocks.registerItem(nourishmentAmulet, "nourishment Amulet");

        nourishmentRing = new NourishmentRing();
        ModItemBlocks.registerItem(nourishmentRing, "nourishment Ring");


    }

}
