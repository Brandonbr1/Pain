package jerios.painmod.forge.events;

import com.google.gson.JsonSyntaxException;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jerios.painmod.core.entity.PainCreeper;
import jerios.painmod.core.networking.packets.clientToServer.CTSPacketMusic;
import jerios.painmod.core.networking.packets.serverToClient.STCEscapedDim;
import jerios.painmod.core.networking.packets.serverToClient.STCPacketMusic;
import jerios.painmod.core.networking.registry.PainPacketHandler;
import jerios.painmod.forge.PainShare;
import jerios.painmod.core.block.BlockPainPortal;
import jerios.painmod.forge.config.PainConfig;
import jerios.painmod.core.items.weapons.IBow;
import jerios.painmod.forge.props.PainProps;
import jerios.painmod.core.effects.registry.ModPotions;
import jerios.painmod.utils.fakePlayer.PainFakePlayerFactory;
import jerios.painmod.utils.rendering.PGL;
import jerios.painmod.utils.side.SidedCheckers;
import jerios.painmod.core.world.teleport.PainTeleporter;
import jerios.painmod.core.world.teleport.TeleporterHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.util.JsonException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;

import java.io.IOException;
import java.util.Objects;

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
                            TeleporterHandler.travelToDimension(playerMP, PainConfig.painDimensionID, new PainTeleporter(ws, false));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void unloadWorld(WorldEvent.Unload event)
    {
        if (event.world instanceof WorldServer) {
            WorldServer world = (WorldServer) event.world;
            PainFakePlayerFactory.fakePlayers.entrySet().removeIf(entry -> entry.getValue().worldObj == world);
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
                PainPacketHandler.INSTANCE.sendToServer(new CTSPacketMusic(data.hasPlayedMusic1, data.hasPlayedMusic2));
                PainPacketHandler.INSTANCE.sendTo(new STCPacketMusic(data.hasPlayedMusic1, data.hasPlayedMusic2), playerMP);
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
                PainPacketHandler.INSTANCE.sendToServer(new CTSPacketMusic(data.hasPlayedMusic1, data.hasPlayedMusic2));
                PainPacketHandler.INSTANCE.sendTo(new STCPacketMusic(data.hasPlayedMusic1, data.hasPlayedMusic2), playerMP);
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


    /**
     *
     *
     * CLIENT START
     *
     *
     */


    @SideOnly(Side.CLIENT)
    boolean hasNightVision = false;
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void tickClient(TickEvent.ClientTickEvent tickEvent) {

        if (tickEvent.side == Side.CLIENT) {
            if (mc.thePlayer != null) {
                hasNightVision = mc.thePlayer.isPotionActive(Potion.nightVision);
            }
        }

        if (mc.theWorld != null) {
            if (inPainDim()) {
                if (mc.gameSettings != null && mc.gameSettings.gammaSetting >= PainConfig.maxBrightnessInDim) {
                    mc.gameSettings.gammaSetting = PainConfig.maxBrightnessInDim;
                }
            }

        }

    }


    @SideOnly(Side.CLIENT)
    private Minecraft mc = FMLClientHandler.instance().getClient();

    // this loads too early...?

    int failCompile;

   // @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onTickWorldRenderEvent(TickEvent.PlayerTickEvent event) {
        //         this.renderWorld(tickDelta, 0L);
        // has valueable code to see
        //leftClickCounter
        // rightClickTimer set high when we have the potion effect active

        String shader = "painmod:shaders/post/painShaders.json";



        if (event.phase == TickEvent.Phase.END && event.side == Side.CLIENT && mc.inGameHasFocus && mc.entityRenderer != null && !mc.isGamePaused() && mc.thePlayer != null && mc.theWorld != null && mc.renderViewEntity != null && mc.renderGlobal != null && mc.renderEngine != null && mc.ingameGUI != null && mc.getFramebuffer() != null && mc.currentScreen == null) {


            if (inPainDim()) {
                if (mc.entityRenderer.theShaderGroup == null) {
                    for (int i = 0; i < EntityRenderer.shaderResourceLocations.length; i++) {
                        ResourceLocation location = EntityRenderer.shaderResourceLocations[i];

                        if (location.toString().equals(shader)) {
                            activateNextShader(i);
                            break;
                        }

                    }
                } else {
                    if (!mc.entityRenderer.theShaderGroup.getShaderGroupName().equals(shader)) {
                        for (int i = 0; i < EntityRenderer.shaderResourceLocations.length; i++) {
                            ResourceLocation location = EntityRenderer.shaderResourceLocations[i];

                            if (location.toString().equals(shader)) {
                                activateNextShader(i);
                                break;
                            }

                        }


                    }
                }

            } else {

                if (mc.entityRenderer.theShaderGroup != null) {
                    for (int i = 0; i < EntityRenderer.shaderResourceLocations.length; i++) {
                        ResourceLocation location = EntityRenderer.shaderResourceLocations[i];

                        if (location.toString().equals(shader)) {
                            mc.entityRenderer.deactivateShader();
                            break;
                        }

                    }
                }


            }
        }





    }

    public void activateNextShader(int index)
    {
        if (OpenGlHelper.shadersSupported)
        {
            if (failCompile > 5) {
                throw new RuntimeException("Pain Mod was unable to compile shaders. Please report bug.");
            }


            if (mc.entityRenderer.theShaderGroup != null)
            {
                mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            }

            mc.entityRenderer.shaderIndex = index;

            if (mc.entityRenderer.shaderIndex != EntityRenderer.shaderCount)
            {
                try
                {
                    PainShare.LOG.info("Selecting effect " + EntityRenderer.shaderResourceLocations[mc.entityRenderer.shaderIndex]);
                    mc.entityRenderer.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), mc.getResourceManager(), this.mc.getFramebuffer(), EntityRenderer.shaderResourceLocations[mc.entityRenderer.shaderIndex]);

                    mc.entityRenderer.theShaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
                }
                catch (IOException ioexception)
                {
                    failCompile++;
                    PainShare.LOG.warn("Failed to load shader: " + EntityRenderer.shaderResourceLocations[mc.entityRenderer.shaderIndex], ioexception);
                    mc.entityRenderer.shaderIndex = EntityRenderer.shaderCount;
                }
                catch (JsonSyntaxException jsonsyntaxexception)
                {
                    failCompile++;
                    PainShare.LOG.warn("Failed to load shader: " + EntityRenderer.shaderResourceLocations[mc.entityRenderer.shaderIndex], jsonsyntaxexception);
                    mc.entityRenderer.shaderIndex = EntityRenderer.shaderCount;
                }
            }
            else {
                mc.entityRenderer.theShaderGroup = null;
                PainShare.LOG.info("No effect selected");
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void lowerRainVolume(PlaySoundEvent17 soundEvent) {
        if (inPainDim()) {
            if (Objects.equals(soundEvent.name, "ambient.weather.rain")) {

                float vol = soundEvent.sound.getVolume();
                float pitch = soundEvent.sound.getPitch();
                if (vol > 0.1F && pitch >= 0.5F) {
                    soundEvent.result = null;
                    mc.thePlayer.playSound("ambient.weather.rain", 0.1f, 0.2f);
                }

            }
        }

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void fogRenderEvent(EntityViewRenderEvent.RenderFogEvent fogEvent)
    {
        if (inPainDim())
        {
            float dist = fogEvent.farPlaneDistance;
            if (fogEvent.fogMode != PGL.GL_LINEAR) {
                PGL.fogLinear();
            }

            if (!hasNightVision) {
                PGL.fogStart( dist * PainConfig.fogDensityStart);
                PGL.fogEnd(dist * PainConfig.fogDensityEnd);
            } else {
                PGL.fogStart( 0);
                PGL.fogEnd(0.1f);
            }


          PGL.fogCapabilities();


        }

    }


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void fogDensityEvent(EntityViewRenderEvent.FogDensity density)
    {
        if (inPainDim())
        {
            if (!hasNightVision) {
                density.density = PainConfig.fogDensity;
            } else {
                density.density = 90;
            }
        }
    }


    // THIS CAUSESS NIGHT VISION TO BE VERY VISIBLE if fog color is 0.1, 0.1, 0.1....
    // Why?

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void fogColorChange(EntityViewRenderEvent.FogColors fogColors) {
        if (inPainDim())
        {
            if (!hasNightVision) {
                fogColors.blue = 0.1f;
                fogColors.green = 0.1f;
                fogColors.red = 0.1f;
            } else {
                fogColors.red = 000.0F;
                fogColors.blue =  000.0F;
                fogColors.green =  000.0F;

            }

        }
    }




    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void fovChanged(FOVUpdateEvent updateEvent) {
        float f = 1.0F;

        EntityPlayerSP playerSP = updateEvent.entity;

        if (playerSP.isUsingItem() && playerSP.getItemInUse().getItem() instanceof IBow)
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
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderTheBowCorrect(RenderPlayerEvent.Specials.Pre pre) {
        EntityPlayer p_77029_1_ = pre.entityPlayer;

        ModelBiped modelBipedMain = pre.renderer.modelBipedMain;

        ItemStack itemstack1 = p_77029_1_.inventory.getCurrentItem();

        float scale;

        if (itemstack1 != null)
        {

            if (itemstack1.getItem() instanceof IBow) {
                pre.renderItem = false;
            } else {
                pre.renderItem = true;
                return;
            }


            PGL.pushMatrix();
            modelBipedMain.bipedRightArm.postRender(0.0625F);
            PGL.translate(-0.0625F, 0.4375F, 0.0625F);


            scale = 0.625F;
            PGL.translate(0.0F, 0.125F, 0.3125F);
            PGL.rotate(-20.0F, 0.0F, 1.0F, 0.0F);
            PGL.scale(scale, -scale, scale);
            PGL.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
            PGL.rotate(45.0F, 0.0F, 1.0F, 0.0F);


            float f3;
            int k;
            float f12;

            k = itemstack1.getItem().getColorFromItemStack(itemstack1, 0);
            float f11 = (float)(k >> 16 & 255) / 255.0F;
            f12 = (float)(k >> 8 & 255) / 255.0F;
            f3 = (float)(k & 255) / 255.0F;
            PGL.color(f11, f12, f3, 1.0F);
            RenderManager.instance.itemRenderer.renderItem(p_77029_1_, itemstack1, 0);


            PGL.popMatrix();
        }

    }

    /**
     *
     *
     * CLIENT END
     *
     *
     */


    @SideOnly(Side.CLIENT)
    private boolean inPainDim() {
        return mc.theWorld != null && mc.theWorld.provider.dimensionId == PainConfig.painDimensionID;
    }


}