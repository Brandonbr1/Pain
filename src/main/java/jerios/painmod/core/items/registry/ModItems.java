package jerios.painmod.core.items.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import jerios.painmod.core.block.BlockCorruptedDarknessBlock;
import jerios.painmod.core.block.BlockPainPortal;
import jerios.painmod.core.block.BlockPainStone;
import jerios.painmod.core.block.PainBlock;
import jerios.painmod.core.block.PainGrass;
import jerios.painmod.core.block.spawner.BlockPainSpawner;
import jerios.painmod.core.items.potions.ItemPainPotion;
import jerios.painmod.core.items.spawner.ItemPainSpawnEgg;
import jerios.painmod.core.items.essence.ItemHarmfulEssence;
import jerios.painmod.core.items.spawner.ItemMonsterSpawner;
import jerios.painmod.core.items.weapons.ItemBoneSword;
import jerios.painmod.core.items.weapons.ItemBowOfCursing;
import jerios.painmod.vanilla.tabs.registry.PainTabsRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;

public class ModItems {

    public static Item spawnEgg;
    public static ItemPainPotion potionPain;
    public static Block painPortal;
    public static ItemBow cursedBow;


    public static Item harmfulEssence;


    public static Block corruptedBlock;

    public static Block painGrass;

    public static Block painDirt;

    public static Block painLogs;

    public static Block painStone;

    public static Block spawner;

    public static Item boneSword;

    public static void register() {
        spawnEgg = new ItemPainSpawnEgg().setCreativeTab(PainTabsRegistry.SPAWN_EGG_TAB);
        registerItem(spawnEgg, "spawn_egg");

        potionPain = (ItemPainPotion) new ItemPainPotion().setCreativeTab(PainTabsRegistry.SPAWN_EGG_TAB).setUnlocalizedName("potion").setTextureName("potion");
        registerItem(potionPain, "pain_potion");


        cursedBow = new ItemBowOfCursing();
        registerItem(cursedBow, "cursed_bow");



        harmfulEssence = new ItemHarmfulEssence();
        registerItem(harmfulEssence, "harmful_essence");


        painPortal = new BlockPainPortal();
        registerBlock(painPortal, "pain_portal");

        corruptedBlock = new BlockCorruptedDarknessBlock();
        registerBlock(corruptedBlock, "corrupted_darkness_block");

        painGrass = new PainGrass("painGrass");
        registerBlock(painGrass, "paint_grass");

        painDirt = new PainBlock(Material.ground, "painDirt");
        registerBlock(painDirt, "pain_dirt");

        spawner = new BlockPainSpawner();

        GameRegistry.registerBlock(spawner, ItemMonsterSpawner.class, "pain_monster_spawner");

        boneSword = new ItemBoneSword();
        registerItem(boneSword, "bone_sword");

        painStone = new BlockPainStone();
        registerBlock(painStone, "pain_stone");

    }



    public static void registerItem(Item item, String name) {
        GameRegistry.registerItem(item, name);
    }

    public static void registerBlock(Block block, String name){
        GameRegistry.registerBlock(block, name);
    }


}
