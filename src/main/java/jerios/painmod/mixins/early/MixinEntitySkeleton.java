package jerios.painmod.mixins.early;

//@Mixin(EntitySkeleton.class)
public abstract class MixinEntitySkeleton /**extends EntityMob implements IRangedAttackMob **/{

  /**  @Shadow private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
    @Shadow private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);

    public MixinEntitySkeleton(World world) {
        super(world);
    }

    @Inject(method = "<init>", at = @At(value = "CTOR_HEAD"))
    private void painmod$replaceInstancesIfPainSkeleton(World world, CallbackInfo ci) {

        if (((EntitySkeleton) (Object) this) instanceof PainSkeleton) {
            aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 30, 35.0F);
            aiAttackOnCollide =  new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, true);
        }

    }
    **/

}
