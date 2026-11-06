package jerios.painmod.client.world;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jerios.painmod.core.world.data.PainWorldData;
import jerios.painmod.forge.PainShare;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;

import java.util.Random;

@SideOnly(Side.CLIENT)
public class PainSkyRender extends IRenderHandler {

    /** The star GL Call list */
    public int starGLCallList;
    /** OpenGL sky list */
    public int glSkyList;
    /** OpenGL sky list 2 */
    public int glSkyList2;

    private static final ResourceLocation locationSunPng = new ResourceLocation("painmod:textures/environment/moon_phases.png");

    private static final ResourceLocation locationMoonPhasesPng = new ResourceLocation("painmod:textures/environment/moon_phases.png");


    public PainSkyRender() {
        byte b0 = 34;
        byte b1 = 16;

        this.starGLCallList = GLAllocation.generateDisplayLists(3);
        GL11.glPushMatrix();
        GL11.glNewList(this.starGLCallList, GL11.GL_COMPILE);
        this.renderStars();
        GL11.glEndList();
        GL11.glPopMatrix();
        Tessellator tessellator = Tessellator.instance;
        this.glSkyList = this.starGLCallList + 1;
        GL11.glNewList(this.glSkyList, GL11.GL_COMPILE);
        byte b2 = 64;
        int i = 256 / b2 + 2;
        float f = 16.0F;
        int j;
        int k;

        for (j = -b2 * i; j <= b2 * i; j += b2)
        {
            for (k = -b2 * i; k <= b2 * i; k += b2)
            {
                tessellator.startDrawingQuads();
                tessellator.addVertex((double)(j + 0), (double)f, (double)(k + 0));
                tessellator.addVertex((double)(j + b2), (double)f, (double)(k + 0));
                tessellator.addVertex((double)(j + b2), (double)f, (double)(k + b2));
                tessellator.addVertex((double)(j + 0), (double)f, (double)(k + b2));
                tessellator.draw();
            }
        }

        GL11.glEndList();
        this.glSkyList2 = this.starGLCallList + 2;
        GL11.glNewList(this.glSkyList2, GL11.GL_COMPILE);
        f = -16.0F;
        tessellator.startDrawingQuads();

        for (j = -b2 * i; j <= b2 * i; j += b2)
        {
            for (k = -b2 * i; k <= b2 * i; k += b2)
            {
                tessellator.addVertex((double)(j + b2), (double)f, (double)(k + 0));
                tessellator.addVertex((double)(j + 0), (double)f, (double)(k + 0));
                tessellator.addVertex((double)(j + 0), (double)f, (double)(k + b2));
                tessellator.addVertex((double)(j + b2), (double)f, (double)(k + b2));
            }
        }

        tessellator.draw();
        GL11.glEndList();
    }

    float scaleF = 2.0F;
    double sizeStar = 65.0D;

    private void renderStars()
    {
        Random random = new Random(FMLClientHandler.instance().getWorldClient().getSeed());

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();

        for (int i = 0; i < 1500; ++i) // 1500
        {


            double d0 = (double)(random.nextFloat() * 2.0F - 1.0F); // spacing 2
            double d1 = (double)(random.nextFloat() * 1.2 - 1.0F);// spacing 2
            double d2 = (double)(random.nextFloat() * 2.0F - 1.0F);// spacing 2
            double d3 = (double)(0.05F + random.nextFloat() * 0.25F); // size?


            double d4 = d0 * d0 + d1 * d1 + d2 * d2;
            // 0.01 - 4

            if (d4 < 6.0D && d4 > 0.01D   /**d4 < 1.0D && d4 > 0.01D**/) // allow more stars in
            {
                d4 = 1.0D / Math.sqrt(d4); // was 1.0D
                d0 *= d4;
                d1 *= d4;
                d2 *= d4;
                double d5 = d0 * 100; // size 100 80 good ?
                double d6 = d1 * 100; // size 100
                double d7 = d2 * 100; // size 100
                double d8 = Math.atan2(d0, d2);
                double d9 = Math.sin(d8);
                double d10 = Math.cos(d8);
                double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
                double d12 = Math.sin(d11);
                double d13 = Math.cos(d11);
                double d14 = random.nextDouble() * Math.PI * 2.0D;
                double d15 = Math.sin(d14);
                double d16 = Math.cos(d14);

                for (int j = 0; j < 4; ++j) // was 4
                {
                    double d17 = 0.0D;
                    double d18 = (double)((j & 2) - 1) * d3;
                    double d19 = (double)((j + 1 & 2) - 1) * d3;
                    double d20 = d18 * d16 - d19 * d15;
                    double d21 = d19 * d16 + d18 * d15;
                    double d22 = d20 * d12 + d17 * d13;
                    double d23 = d17 * d12 - d20 * d13;
                    double d24 = d23 * d9 - d21 * d10;
                    double d25 = d21 * d9 + d23 * d10;
                    tessellator.addVertex(d5 + d24, d6 + d22, d7 + d25);
                }
            }
        }

        tessellator.draw();
    }


    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc)
    {
        renderSky(partialTicks, world, mc);
    }



    public void renderSky(float partialTickTime, WorldClient worldClient, Minecraft mc)
    {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        Vec3 vec3 = worldClient.getSkyColor(mc.renderViewEntity, partialTickTime);
        float f1 = (float)vec3.xCoord;
        float f2 = (float)vec3.yCoord;
        float f3 = (float)vec3.zCoord;
        float f6;


        GL11.glColor3f(f1, f2, f3);
        Tessellator tessellator = Tessellator.instance;
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glColor3f(f1, f2, f3);
        GL11.glCallList(glSkyList);
        GL11.glDisable(GL11.GL_FOG);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        RenderHelper.disableStandardItemLighting();
        float f7;
        float f8;
        float f9;
        float f10;

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.glBlendFunc(770, 1, 1, 0);
        GL11.glPushMatrix();
        f6 = 1.0F - worldClient.getRainStrength(partialTickTime);
        f7 = 0.0F;
        f8 = 0.0F;
        f9 = 0.0F;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, f6);
        GL11.glTranslatef(f7, f8, f9);
        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(worldClient.getCelestialAngle(partialTickTime) * 360.0F, 1.0F, 0.0F, 0.0F);
        f10 = 30.0F;
        mc.renderEngine.bindTexture(locationSunPng);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(-f10), 100.0D, (double)(-f10), 0.0D, 0.0D);
        tessellator.addVertexWithUV((double)f10, 100.0D, (double)(-f10), 1.0D, 0.0D);
        tessellator.addVertexWithUV((double)f10, 100.0D, (double)f10, 1.0D, 1.0D);
        tessellator.addVertexWithUV((double)(-f10), 100.0D, (double)f10, 0.0D, 1.0D);
        tessellator.draw();
        f10 = 20.0F;
        mc.renderEngine.bindTexture(locationMoonPhasesPng);
        int k = 2;
        int l = k % 4;
        int i1 = k / 4 % 2;
        float f14 = (float)(l + 0) / 4.0F;
        float f15 = (float)(i1 + 0) / 2.0F;
        float f16 = (float)(l + 1) / 4.0F;
        float f17 = (float)(i1 + 1) / 2.0F;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(-f10), -100.0D, (double)f10, (double)f16, (double)f17);
        tessellator.addVertexWithUV((double)f10, -100.0D, (double)f10, (double)f14, (double)f17);
        tessellator.addVertexWithUV((double)f10, -100.0D, (double)(-f10), (double)f14, (double)f15);
        tessellator.addVertexWithUV((double)(-f10), -100.0D, (double)(-f10), (double)f16, (double)f15);
        tessellator.draw();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        float starBrightness = worldClient.getStarBrightness(partialTickTime) * f6;

        if (starBrightness > 0.0F)
        {
            GL11.glColor4f(starBrightness, starBrightness, starBrightness, starBrightness);
            GL11.glCallList(starGLCallList);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor3f(0.0F, 0.0F, 0.0F);
        double d0 = mc.thePlayer.getPosition(partialTickTime).yCoord - 32;

        if (d0 < 0.0D)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 12.0F, 0.0F);  // important?
            GL11.glCallList(glSkyList2);
            GL11.glPopMatrix();
            f8 = 1.0F;
            f9 = -((float)(d0 + 65.0D));
            f10 = -f8;
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_I(0, 255);
            tessellator.addVertex((double)(-f8), (double)f9, (double)f8);
            tessellator.addVertex((double)f8, (double)f9, (double)f8);
            tessellator.addVertex((double)f8, (double)f10, (double)f8);
            tessellator.addVertex((double)(-f8), (double)f10, (double)f8);
            tessellator.addVertex((double)(-f8), (double)f10, (double)(-f8));
            tessellator.addVertex((double)f8, (double)f10, (double)(-f8));
            tessellator.addVertex((double)f8, (double)f9, (double)(-f8));
            tessellator.addVertex((double)(-f8), (double)f9, (double)(-f8));
            tessellator.addVertex((double)f8, (double)f10, (double)(-f8));
            tessellator.addVertex((double)f8, (double)f10, (double)f8);
            tessellator.addVertex((double)f8, (double)f9, (double)f8);
            tessellator.addVertex((double)f8, (double)f9, (double)(-f8));
            tessellator.addVertex((double)(-f8), (double)f9, (double)(-f8));
            tessellator.addVertex((double)(-f8), (double)f9, (double)f8);
            tessellator.addVertex((double)(-f8), (double)f10, (double)f8);
            tessellator.addVertex((double)(-f8), (double)f10, (double)(-f8));
            tessellator.addVertex((double)(-f8), (double)f10, (double)(-f8));
            tessellator.addVertex((double)(-f8), (double)f10, (double)f8);
            tessellator.addVertex((double)f8, (double)f10, (double)f8);
            tessellator.addVertex((double)f8, (double)f10, (double)(-f8));
            tessellator.draw();
        }


        GL11.glColor3f(f1, f2, f3);

        GL11.glPushMatrix();
        //   GL11.glTranslatef(0.0F, -((float)(d0 - 16.0D)), 0.0F); // important?
        GL11.glCallList(glSkyList2);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(true);

    }


}