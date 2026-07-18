package jerios.painmod.entity;

import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class PainSkeleton extends EntitySkeleton {

    public PainSkeleton(World world) {
        super(world);
        this.tasks.removeTask(new EntityAIFleeSun(this, 1.0D));
        this.tasks.removeTask(new EntityAIRestrictSun(this));
        this.aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 30, 35.0F);
        this.aiAttackOnCollide =  new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, true);
    }


    @Override
    public float getBrightness(float partialTicks) {
        return 0;
    }
}
