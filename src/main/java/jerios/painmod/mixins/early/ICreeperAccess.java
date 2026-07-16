package jerios.painmod.mixins.early;

import net.minecraft.entity.monster.EntityCreeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityCreeper.class)
public interface ICreeperAccess {

    @Invoker("func_146077_cc")
    void callOriginalMethod() ;

    @Accessor("explosionRadius")
    int getExplosion();

    @Accessor("timeSinceIgnited")
    void setIgnitedTime(int time);

}
