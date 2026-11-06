package jerios.painmod.utils.rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public abstract class PGL {

    public static void fog(int par, float dist) {
        GL11.glFogf(par, dist);
    }

    public static void fogLinear() {
        fog(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
    }

    public static void fogStart(float dist){
        fog(GL_FOG_START, dist);
    }

    public static void fogEnd(float dist){
        fog(GL_FOG_END, dist);
    }

    private static boolean hasFogCapabilities() {
        return GLContext.getCapabilities().GL_NV_fog_distance;
    }

    public static void fogCapabilities() {
        if (hasFogCapabilities()) {
            PGL.fog(34138, 34139);
        }
    }

    public static void enable(int cap) {
       GL11.glEnable(cap);
    }

    public static void disable(int cap) {
        GL11.glDisable(cap);
    }

    public static void popAttrib() {
        GL11.glPopAttrib();
    }

    public static void popMatrix() {
        GL11.glPopMatrix();
    }

    public static void color(float r, float g, float b , float alpha) {
        GL11.glColor4f(r,g,b,alpha);
    }

    public static void color(double r, double g, double b , double alpha) {
        GL11.glColor4d(r,g,b,alpha);
    }

    public static void pushAttrib(int mask) {
        GL11.glPushAttrib(mask);
    }

    public static void pushMatrix() {
        GL11.glPushMatrix();
    }

    public static void translate(float x, float y, float z) {
        GL11.glTranslatef(x,y,z);
    }

    public static void translate(double x, double y, double z) {
        GL11.glTranslated(x,y,z);
    }

    public static void loadIdentity() {
        GL11.glLoadIdentity();
    }

    public static void setMatrixMode(int matrixMode) {
        GL11.glMatrixMode(matrixMode);
    }

    public static void setRGBAlpha(float red, float blue, float green, float alpha) {
        GL11.glColor4f(red, blue, green, alpha);
    }

    public static void setRGBAlpha(double red, double blue, double green, double alpha) {
        GL11.glColor4d(red, blue, green, alpha);
    }

    public static void scale(float x, float y, float z) {
        GL11.glScalef(x,y,z);
    }

    public static void scale(double x, double y, double z) {
        GL11.glScaled(x,y,z);
    }

    public static void blend(int sFactor, int dFactor) {
        GL11.glBlendFunc(sFactor,dFactor);
    }

    public static void depthMask(boolean mask) {
        GL11.glDepthMask(mask);
    }


    public static void alpha(int func, int ref){
        GL11.glAlphaFunc(func, ref);
    }


    public static void rotate(float angle, float x, float y, float z) {
        GL11.glRotatef(angle, x,y,z);
    }

    public static void rotate(double angle, double x, double y, double z) {
        GL11.glRotated(angle, x,y,z);
    }



    public static final int
            GL_LINEAR = GL11.GL_LINEAR,
            GL_FOG_START =GL11.GL_FOG_START,
            GL_FOG_MODE = GL11.GL_FOG_MODE,
            GL_FOG_END = GL11.GL_FOG_END;



}
