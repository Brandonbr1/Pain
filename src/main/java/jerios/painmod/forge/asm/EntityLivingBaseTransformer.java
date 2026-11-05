package jerios.painmod.forge.asm;

import jerios.painmod.forge.PainShare;
import net.minecraft.launchwrapper.IClassTransformer;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.GeneratorAdapter;


public class EntityLivingBaseTransformer /**implements IClassTransformer **/{



 /**   public class Trandform extends GeneratorAdapter {


        public Trandform(MethodVisitor mv, int access, String name, String desc) {
            super(Opcodes.ASM5, mv, access, name, desc);

        }


        boolean first = false;
        @Override
        public void visitVarInsn(int opcode, int var) {

            System.out.println("FIRST OPTICODE" + opcode);
            System.out.println("FIRST var" + var);

            if (!first) {

                if (opcode == 25 && var == 0 ) {
                    System.out.println("Loaded" + var);
                    mv.visitVarInsn(Opcodes.ALOAD, 0);
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net.minecraft.entity.EntityLivingBase", "isPotionActive", "");

                    first =true;
                }


            }



            super.visitVarInsn(opcode, var);
        }
    }


    @Override
    public byte[] transform(String name, String srgName, byte[] bytes) {
        if ("net.minecraft.entity.EntityLivingBase".equals(srgName)) {
            ClassReader reader = new ClassReader(bytes);
            ClassWriter writer = new ClassWriter(reader, Opcodes.ASM5);

            ClassVisitor visitor = new ClassVisitor(Opcodes.ASM5, writer) {

                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                                 String[] exceptions) {
                    MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);

                    if ("func_149746_a".equals(name) || "jump".equals(name))
                        return new Trandform(methodVisitor, access, name, desc);

                    return methodVisitor;
                }
            };

            reader.accept(visitor, ClassReader.EXPAND_FRAMES);

            bytes = writer.toByteArray();
        }

        return bytes;
    }
    **/
}
