package jerios.painmod.utils;

import org.lwjgl.opengl.GL11;

public abstract class PGL {

    public static void fog(int par, float dist) {
        GL11.glFogf(par, dist);
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


}
