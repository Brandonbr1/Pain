package jerios.painmod.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import jerios.painmod.PainConstants;
import jerios.painmod.PainMod;
import jerios.painmod.block.BlockPainPortal;
import jerios.painmod.config.PainConfig;
import jerios.painmod.props.PainProps;
import jerios.painmod.registry.ModPotions;
import jerios.painmod.utils.SidedCheckers;
import jerios.painmod.world.teleport.PainTeleporter;
import jerios.painmod.world.teleport.TeleporterHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.world.BlockEvent;

public class PainEventsServer {

    @SubscribeEvent
    public void startInPainDim(PlayerEvent.PlayerLoggedInEvent joinWorldEvent) {
        World world = joinWorldEvent.player.worldObj;
       EntityPlayer entity = joinWorldEvent.player;
        final int dim = PainConfig.painDimensionID;
        if (PainConfig.spawnInPainDim && world instanceof WorldServer) {
            WorldServer server = (WorldServer) world;
            if (entity instanceof EntityPlayerMP) {
                EntityPlayerMP playerMP = (EntityPlayerMP) entity;
                if (playerMP.dimension != PainConfig.painDimensionID) {
                    PainProps props = PainProps.get(playerMP);
                    WorldServer ws = server.func_73046_m().worldServerForDimension(dim);
                    if (ws != null)
                    {
                        if (props == null || !props.escapedDim) {
                            TeleporterHandler.travelToDimension(playerMP, PainConfig.painDimensionID, new PainTeleporter(ws));
                        }
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void actuallySayText(PlayerEvent.PlayerChangedDimensionEvent event) {
        EntityPlayer player = event.player;
        if (event.fromDim != PainConfig.painDimensionID && event.toDim == PainConfig.painDimensionID) {
            player.addChatMessage(new ChatComponentTranslation("painmod.enterDim"));
        } else if (event.fromDim == PainConfig.painDimensionID && event.toDim != PainConfig.painDimensionID) {
            player.addChatMessage(new ChatComponentTranslation("painmod.ahhFinallyFree"));
        }
    }

    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.PlaceEvent placeEvent) {
        Block block = placeEvent.placedBlock;
        World world=placeEvent.world;
        EntityPlayer player = placeEvent.player;
        if (!world.isRemote && player.dimension == PainConfig.painDimensionID) {
            if (block instanceof BlockPortal && !(block instanceof BlockPainPortal)) {
                placeEvent.setCanceled(true);
                player.timeUntilPortal = 80;
            }
        }

    }

    @SubscribeEvent
    public void onJump(LivingEvent.LivingJumpEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            if (player.isPotionActive(ModPotions.sloth)) {
            player.motionY -= 0.8D;
            }
        }

    }


    @SubscribeEvent
    public void onFall(LivingFallEvent event) {
        if (!event.entityLiving.handleWaterMovement() && event.entityLiving.onGround) {
            if (event.entityLiving instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.entityLiving;
                if (player.isPotionActive(ModPotions.sloth)) {
                    player.motionY += 0.8D;
                }
            }
        }
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        EntityPlayer player = event.entityPlayer;
        World world = player.worldObj;
        if (SidedCheckers.isServer(world)) {
            if (player.isPotionActive(ModPotions.sloth)) {
                boolean rand = PainConstants.rand.nextBoolean();
                boolean rand2 = PainConstants.rand.nextBoolean();


                if (rand && !rand2)
                {
                    event.setCanceled(true);
                }
            }

        }

    }

    @SubscribeEvent
    public void onHurt(LivingHurtEvent event) {
        DamageSource source = event.source;
        Entity attacker = source.getEntity();

        if (attacker instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) attacker;

            if (player.isPotionActive(ModPotions.rageEffect)) {
                player.attackEntityFrom(PainMod.RAGE, event.ammount * 0.5f);
                player.addExhaustion(event.ammount * 0.8f);
                ItemStack stack = player.inventory.getCurrentItem();

               if (stack != null) {
                   Item item = stack.getItem();
                   if (item != null) {
                       if (item.isDamageable()) {
                           stack.damageItem((int) (event.ammount * Math.max(event.ammount, 3)), player);
                       }
                    }
                   }

               }

        }


        // if player gets hurt.
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            float damage = 1.0f;
            if (player.isPotionActive(ModPotions.malnourishment)) {
                damage += 0.6F;
            }
            if (player.isPotionActive(ModPotions.deadlyPoison)) {
                damage += 0.2F;
            }
            if (player.isPotionActive(ModPotions.plaugeEffect)) {
                damage += 0.4F;
            }
            event.ammount += damage;
        }
    }

    @SubscribeEvent
    public void onItemUse(PlayerUseItemEvent.Finish event) {
        EntityPlayer player= event.entityPlayer;
        ItemStack itemStack = event.item;
        Item item = itemStack.getItem();

        if (player.isPotionActive(ModPotions.malnourishment)) {
            if (item instanceof ItemFood) {
                ItemFood food = (ItemFood) item;
                int foodHeal = food.func_150905_g(itemStack);
                float foodSat = food.func_150906_h(itemStack);

                FoodStats playerStats = player.getFoodStats();

                int currentPlayerHeal = playerStats.getFoodLevel();
                float currentPlayerSat = playerStats.getSaturationLevel();

                int finalHeal = (int) (currentPlayerHeal - (foodHeal * 0.5));
                float finalSat = currentPlayerSat - (foodSat * 0.5f);

                player.getFoodStats().setFoodLevel(finalHeal);
                player.getFoodStats().setFoodSaturationLevel(finalSat);
            }

        }

    }

    @SubscribeEvent
    public void longerBowDrawTime(PlayerUseItemEvent.Start event) {
        EntityPlayer player = event.entityPlayer;
        ItemStack itemStack = event.item;
        Item item = itemStack.getItem();

        if (player.isPotionActive(ModPotions.malnourishment))
        {
            if (item instanceof ItemBow) {
                    event.duration += 15;
            }

        }

    }

    @SubscribeEvent
    public void onItemUse(PlayerUseItemEvent.Tick event) {
     EntityPlayer player= event.entityPlayer;
     ItemStack itemStack = event.item;
     Item item = itemStack.getItem();
     int duration = event.duration;

     if (player.isPotionActive(ModPotions.malnourishment)) {
         if (player.ticksExisted % 15 == 0) {
             event.duration += 13;
         }
     }



    }



}
