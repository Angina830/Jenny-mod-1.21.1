package com.schnurritv.sexmod.entity.goblin;
import com.schnurritv.sexmod.entity.BaseGirlEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
public class GoblinEntity extends BaseGirlEntity {
    public GoblinEntity(EntityType<? extends PathfinderMob> type, Level level) { super(type, level); }
    @Override public String getGirlName() { return "goblin"; }
    @Override public java.util.List<String> getAvailableActions() {
        return java.util.List.of("Follow", "Stay", "Boobjob", "Stop");
    }
}
