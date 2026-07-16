package jerios.painmod.entity;

// prob use for special effects and for handling professions
public interface IPainMobs {

    /** Is atomic striker Infernal Mob or Lyc style**/
    boolean getEffectsType();

    /** Use atomic striker Infernal Mob or Lyc style**/
    boolean setEffectType();

    /** Effcts a Mob can not have **/
    String[] blacklisted();

}
