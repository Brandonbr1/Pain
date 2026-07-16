package jerios.painmod.entity;

import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.world.World;

public class PainSkeleton extends EntitySkeleton {

    public PainSkeleton(World world) {
        super(world);
        this.tasks.removeTask(new EntityAIFleeSun(this, 1.0D));
        this.tasks.removeTask(new EntityAIRestrictSun(this));
    }



}
