package jerios.painmod.forge.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import jerios.painmod.core.entity.PainCreeper;
import jerios.painmod.core.networking.packets.serverToClient.STCEscapedDim;
import jerios.painmod.core.networking.registry.PainPacketHandler;
import jerios.painmod.forge.PainShare;
import jerios.painmod.core.block.BlockPainPortal;
import jerios.painmod.forge.config.PainConfig;
import jerios.painmod.forge.props.PainProps;
import jerios.painmod.core.effects.registry.ModPotions;
import jerios.painmod.utils.side.SidedCheckers;
import jerios.painmod.core.world.teleport.PainTeleporter;
import jerios.painmod.core.world.teleport.TeleporterHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.brewing.PotionBrewEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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
                            TeleporterHandler.travelToDimension(playerMP, PainConfig.painDimensionID, new PainTeleporter(ws, false));
                        }
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public void tickPlayer(TickEvent.PlayerTickEvent tickEvent) {
        EntityPlayer player = tickEvent.player;
        PainProps props = PainProps.get(player);

        if (tickEvent.side == Side.SERVER)
        {

            if (!player.worldObj.isRemote)
            {

                if (props != null)
                {

                    if (props.jumpscareTimer > props.requiredJumpscareTimer)
                    {
                        player.worldObj.playSound(player.posX, player.posY, player.posZ, "painmod:jumpscare", 1.0F, 1.0F, false);
                        props.jumpscareTimer = -900;
                        props.requiredJumpscareTimer = 18000 + 400 + PainShare.rand.nextInt(9000);


                        if (PainShare.rand.nextBoolean()) {

                            PainCreeper creeper = new PainCreeper(player.worldObj);
                            creeper.setLocationAndAngles(player.posX, player.posY, player.posZ, 0.0f, 0);
                            player.worldObj.spawnEntityInWorld(creeper);
                        }


                    } else {
                        props.jumpscareTimer++;
                    }


                }





            }




        }



    }


    @SubscribeEvent
    public void playerChangedDim(PlayerEvent.PlayerChangedDimensionEvent event) {
        EntityPlayer player = event.player;
        final int dim = PainConfig.painDimensionID;
        if (event.fromDim != PainConfig.painDimensionID && event.toDim == PainConfig.painDimensionID) {

            String s =  player.getCommandSenderName() + " said ";
            ChatComponentTranslation s2 = (new ChatComponentTranslation("painmod.enterDim"));
            player.addChatMessage(new ChatComponentText(s + s2.getUnformattedTextForChat()));
        } else if (event.fromDim == PainConfig.painDimensionID && event.toDim != PainConfig.painDimensionID) {
           PainProps props = PainProps.get(player);
           if (props != null && props.escapedDim) {
               player.addChatMessage(new ChatComponentTranslation("painmod.ahhFinallyFree"));
           } else {
               if (player instanceof EntityPlayerMP) {
                   EntityPlayerMP playerMP = (EntityPlayerMP) player;
                   WorldServer ws = playerMP.mcServer.worldServerForDimension(dim);
                   if (ws != null) {
                       TeleporterHandler.travelToDimension(playerMP, PainConfig.painDimensionID, new PainTeleporter(ws, false));
                       player.addChatMessage(new ChatComponentTranslation("painmod.failedEscape"));
                   }
               }
           }
        }
    }


    // some mods may not fire the Transport Dimension Event
    @SubscribeEvent
    public void preventFullyEscape(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;


        if (!player.worldObj.isRemote) {

            PainProps props = PainProps.get(player);


            if (props != null) {
                if (!props.escapedDim) {
                    if (player.worldObj.provider.dimensionId != PainConfig.painDimensionID)
                    {
                        if (player instanceof EntityPlayerMP) {

                                final int dim = PainConfig.painDimensionID;
                                EntityPlayerMP playerMP = (EntityPlayerMP) player;
                                WorldServer ws = playerMP.mcServer.worldServerForDimension(dim);
                                TeleporterHandler.travelToDimension(playerMP, PainConfig.painDimensionID, new PainTeleporter(ws, false));
                                player.addChatMessage(new ChatComponentTranslation("painmod.failedEscape"));

                        }

                    }


                }


            }


    }


    }

    public void onBrew(PotionBrewEvent.Pre brewEvent) {

    }


    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.PlaceEvent placeEvent) {
        World world=placeEvent.world;
        EntityPlayer player = placeEvent.player;
        if (!world.isRemote && player.dimension == PainConfig.painDimensionID) {
            Block block = placeEvent.placedBlock;
            if (block instanceof BlockPortal && !(block instanceof BlockPainPortal)) {
                placeEvent.setCanceled(true);
                player.timeUntilPortal = 900;
            }
        }

    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        EntityPlayer player = event.entityPlayer;
        World world = player.worldObj;
        if (SidedCheckers.isServer(world)) {
            if (player.isPotionActive(ModPotions.sloth)) {
                boolean rand = PainShare.rand.nextBoolean();
                boolean rand2 = PainShare.rand.nextBoolean();


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
                player.attackEntityFrom(PainShare.RAGE, event.ammount * 0.5f);
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
    public void onItemFinish(PlayerUseItemEvent.Finish event) {
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


    /**
     *
     *
     *
     * IEEP EVENTS START
     *
     *
     */

    @SubscribeEvent
    public void entityConstruct(EntityEvent.EntityConstructing e) {
        if (e.entity instanceof EntityPlayer) {
            if (e.entity.getExtendedProperties(PainProps.PROP_NAME) == null) {
                e.entity.registerExtendedProperties(PainProps.PROP_NAME, new PainProps());
            }
        }
    }


    @SubscribeEvent
    public void onClonePlayer(net.minecraftforge.event.entity.player.PlayerEvent.Clone e) {
            NBTTagCompound compound = new NBTTagCompound();
            PainProps.get(e.original).saveNBTData(compound);
            PainProps.get(e.entityPlayer).loadNBTData(compound);
    }


    @SubscribeEvent
    public void entityJoinWorld(EntityJoinWorldEvent e) {
        if (e.entity instanceof EntityPlayerMP) {
            EntityPlayerMP playerMP = (EntityPlayerMP) e.entity;
            PainProps data = PainProps.get(e.entity);
            if (data != null) {
                PainPacketHandler.INSTANCE.sendTo(new STCEscapedDim(data.escapedDim), playerMP);
            }
        }
    }

    @SubscribeEvent
    public void playerStartedTracking(net.minecraftforge.event.entity.player.PlayerEvent.StartTracking e) {
        if (e.entityPlayer instanceof EntityPlayerMP) {
            EntityPlayerMP playerMP = (EntityPlayerMP) e.entityPlayer;
            PainProps data = PainProps.get(e.entityPlayer);
            if (data != null) {
                PainPacketHandler.INSTANCE.sendTo(new STCEscapedDim(data.escapedDim), playerMP);
            }
        }

    }



    /**
     *
     *
     *
     * IEEP EVENTS END
     *
     *
     */





}
