package jerios.painmod.utils;

import org.lwjgl.opengl.GL11;

public class PGL {

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

    public static void translateF(float x, float y, float z) {
        GL11.glTranslatef(x,y,z);
    }

    public static void translateD(double x, double y, double z) {
        GL11.glTranslated(x,y,z);
    }

    public static void loadIdentity() {
        GL11.glLoadIdentity();
    }

    public static void setMatrixMode(int matrixMode) {
        GL11.glMatrixMode(matrixMode);
    }

    public static void setRGBAlphaF(float red, float blue, float green, float alpha) {
        GL11.glColor4f(red, blue, green, alpha);
    }

    public static void setRGBAlphaD(double red, double blue, double green, double alpha) {
        GL11.glColor4d(red, blue, green, alpha);
    }

    public static void scaleF(float x, float y, float z) {
        GL11.glScalef(x,y,z);
    }

    public static void scaleD(double x, double y, double z) {
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




}
