package jerios.painmod.events;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jerios.painmod.PainConstants;
import jerios.painmod.PainMod;
import jerios.painmod.Share;
import jerios.painmod.block.BlockPainPortal;
import jerios.painmod.client.audio.PainMusic;
import jerios.painmod.config.PainConfig;
import jerios.painmod.props.PainProps;
import jerios.painmod.registry.ModItemBlocks;
import jerios.painmod.registry.ModPotions;
import jerios.painmod.utils.PGL;
import jerios.painmod.utils.SidedCheckers;
import jerios.painmod.world.teleport.PainTeleporter;
import jerios.painmod.world.teleport.TeleporterHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class PainEvents {


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
    public void playerChangedDim(PlayerEvent.PlayerChangedDimensionEvent event) {
        EntityPlayer player = event.player;
        final int dim = PainConfig.painDimensionID;
        if (event.fromDim != PainConfig.painDimensionID && event.toDim == PainConfig.painDimensionID) {
            player.addChatMessage(new ChatComponentTranslation("painmod.enterDim"));
        } else if (event.fromDim == PainConfig.painDimensionID && event.toDim != PainConfig.painDimensionID) {
           PainProps props = PainProps.get(player);
           if (props != null && props.escapedDim) {
               player.addChatMessage(new ChatComponentTranslation("painmod.ahhFinallyFree"));
           } else {
               if (player instanceof EntityPlayerMP) {
                   EntityPlayerMP playerMP = (EntityPlayerMP) player;
                   WorldServer ws = playerMP.mcServer.worldServerForDimension(dim);
                   TeleporterHandler.travelToDimension(playerMP, PainConfig.painDimensionID, new PainTeleporter(ws));
                   player.addChatMessage(new ChatComponentTranslation("painmod.failedEscape"));
               }
           }
        }
    }





    @SubscribeEvent
    public void world(TickEvent.WorldTickEvent tickEvent) {


        WorldServerMulti worldserver = (WorldServerMulti) MinecraftServer.getServer().worldServers[PainConfig.painDimensionID];
        DerivedWorldInfo worldinfo = (DerivedWorldInfo) worldserver.getWorldInfo();


        Share.LOG.info("Event Rain Time"  +worldinfo.getRainTime());
    }

    // some mods may not fire the Transport Dimension Event
    @SubscribeEvent
    public void preventFullyEscape(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;


        if (!player.worldObj.isRemote) {


        if (player.ticksExisted % 200 == 0) {
            PainProps props = PainProps.get(player);


            if (props != null) {
                if (!props.escapedDim) {
                    if (player.worldObj.provider.dimensionId != PainConfig.painDimensionID)
                    {
                        if (player instanceof EntityPlayerMP) {

                                final int dim = PainConfig.painDimensionID;
                                EntityPlayerMP playerMP = (EntityPlayerMP) player;
                                WorldServer ws = playerMP.mcServer.worldServerForDimension(dim);
                                TeleporterHandler.travelToDimension(playerMP, PainConfig.painDimensionID, new PainTeleporter(ws));
                                player.addChatMessage(new ChatComponentTranslation("painmod.failedEscape"));

                        }

                    }


                }


            }

        }
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
    public void entityJoinWorld(EntityJoinWorldEvent e) {

    }

    @SubscribeEvent
    public void playerStartedTracking(net.minecraftforge.event.entity.player.PlayerEvent.StartTracking e) {

    }

    @SubscribeEvent
    public void onClonePlayer(net.minecraftforge.event.entity.player.PlayerEvent.Clone e) {
        if (e.wasDeath) {
            NBTTagCompound compound = new NBTTagCompound();
            PainProps.get(e.original).saveNBTData(compound);
            PainProps.get(e.entityPlayer).loadNBTData(compound);
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


    /**
     *
     *
     * CLIENT START
     *
     *
     */


    @SideOnly(Side.CLIENT)
    private Minecraft mc = FMLClientHandler.instance().getClient();


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onTickWorldRenderEvent(RenderWorldLastEvent event) {
        //leftClickCounter
        // rightClickTimer set high when we have the potion effect active

        if (mc.theWorld != null) {
            if (inPainDim()) {
                if (mc.gameSettings != null && mc.gameSettings.gammaSetting >= PainConfig.maxBrightnessInDim) {
                    mc.gameSettings.gammaSetting = PainConfig.maxBrightnessInDim;
                }
            }

        }


    }



    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOW)
    public void changeMusicTicker(EntityJoinWorldEvent load) {
        if (load.entity instanceof EntityPlayer) {
            if (load.world.isRemote) {
                if (load.world.provider.dimensionId == PainConfig.painDimensionID) {
                    System.out.println("RUN7");
                    if (!(mc.mcMusicTicker instanceof PainMusic)) {
                        System.out.println("RUN0");
                        if (prevMusicTicker == null) {
                            prevMusicTicker = mc.mcMusicTicker;
                        }
                        mc.mcMusicTicker = new PainMusic(mc);
                        mc.getSoundHandler().stopSounds(); // stop all sounds so we play our own
                    } else {
                        System.out.println("RUN6");
                        if (prevMusicTicker == null) {
                            System.out.println("RUN1");
                            prevMusicTicker = mc.mcMusicTicker;
                        }
                        if (mc.mcMusicTicker != prevMusicTicker) {
                            System.out.println("RUN2");
                            mc.mcMusicTicker = prevMusicTicker;
                        }

                    }
                } else {
                    System.out.println("RUN5");
                    if (prevMusicTicker == null) {
                        System.out.println("RUN3");
                        prevMusicTicker = mc.mcMusicTicker;
                    } else if (mc.mcMusicTicker != prevMusicTicker) {
                        System.out.println("RUN4");
                        mc.mcMusicTicker = prevMusicTicker;
                    }

                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void disconnect(WorldEvent.Unload unload) {
        if (unload.world.isRemote) {
            if (prevMusicTicker != null) {
                mc.mcMusicTicker = prevMusicTicker;
                prevMusicTicker = null;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public MusicTicker prevMusicTicker;


   @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void fogRenderEvent(EntityViewRenderEvent.RenderFogEvent fogEvent)
    {
        if (inPainDim())
        {
            fogEvent.setResult(Event.Result.ALLOW);
            float dist = fogEvent.farPlaneDistance;

                PGL.fog(GL11.GL_FOG_MODE, GL11.GL_LINEAR);

                GL11.glFogf(GL11.GL_FOG_START, dist * PainConfig.fogDensityStart);
                GL11.glFogf(GL11.GL_FOG_END, dist * PainConfig.fogDensityEnd);

                if (GLContext.getCapabilities().GL_NV_fog_distance) {
                    PGL.fog(34138, 34139);
                }


        }

    }


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void fogDensityEvent(EntityViewRenderEvent.FogDensity density)
    {
        if (inPainDim())
        {
                density.density = PainConfig.fogDensity;
        }
    }


    // THIS CAUSESS NIGHT VISION TO BE VERY VISIBLE if fog color is 0.1, 0.1, 0.1....
    // Why?


    @SubscribeEvent
    public void fogColorChange(EntityViewRenderEvent.FogColors fogColors) {
        if (inPainDim())
        {
            fogColors.blue = 0.1f;
            fogColors.green = 0.1f;
            fogColors.red = 0.1f;
        //IT WORKS NOW?
       //     System.out.println("WJFWJKSK "+fogColors.entity.isPotionActive(Potion.nightVision.id));
         //       System.out.println("TEST" + fogColors.entity.worldObj.isRemote);

        }
    }




    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void fovChanged(FOVUpdateEvent updateEvent) {
        float f = 1.0F;

        EntityPlayerSP playerSP = updateEvent.entity;

        if (playerSP.isUsingItem() && playerSP.getItemInUse().getItem() == ModItemBlocks.cursedBow)
        {
            int i = playerSP.getItemInUseDuration();
            float f1 = (float)i / 20.0F;

            if (f1 > 1.0F)
            {
                f1 = 1.0F;
            }
            else
            {
                f1 *= f1;
            }

            f *= 1.0F - f1 * 0.15F;

            updateEvent.newfov = f;
        }
    }

    // THIS IS AWFUL!
    @SubscribeEvent
    public void renderTheBowCorrect(RenderPlayerEvent.Specials.Pre pre) {
       EntityPlayer p_77029_1_ = pre.entityPlayer;

        ModelBiped modelBipedMain = pre.renderer.modelBipedMain;

        ItemStack itemstack1 = p_77029_1_.inventory.getCurrentItem();

        float f2;

        if (itemstack1 != null)
        {

            if (itemstack1.getItem() == ModItemBlocks.cursedBow) {
                pre.renderItem = false;
            } else {
                pre.renderItem = true;
                return;
            }


            GL11.glPushMatrix();
            modelBipedMain.bipedRightArm.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);


            f2 = 0.625F;
                 GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                 GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                 GL11.glScalef(f2, -f2, f2);
                 GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                 GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);


            float f3;
            int k;
            float f12;

                k = itemstack1.getItem().getColorFromItemStack(itemstack1, 0);
                float f11 = (float)(k >> 16 & 255) / 255.0F;
                f12 = (float)(k >> 8 & 255) / 255.0F;
                f3 = (float)(k & 255) / 255.0F;
                GL11.glColor4f(f11, f12, f3, 1.0F);
                RenderManager.instance.itemRenderer.renderItem(p_77029_1_, itemstack1, 0);


            GL11.glPopMatrix();
        }

    }

    /**
     *
     *
     * CLIENT END
     *
     *
     */


    /**
     *
     *
     * UTILS START
     *
     *
     */

    private boolean inPainDim() {
        return mc.theWorld != null && mc.theWorld.provider.dimensionId == PainConfig.painDimensionID;
    }


    /**
     *
     *
     * UTILS END
     *
     *
     */






































}
