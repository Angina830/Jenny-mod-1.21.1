package com.schnurritv.sexmod.entity.slime;
import com.schnurritv.sexmod.entity.BaseGirlEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
public class SlimeEntity extends BaseGirlEntity {
    public SlimeEntity(EntityType<? extends PathfinderMob> type, Level level) { super(type, level); }
    @Override public String getGirlName() { return "slime"; }
    @Override public String getGeoFileName() { return "dressed"; }
    @Override public String getNudeGeoFileName() { return "nude"; }
    @Override public java.util.List<String> getAvailableActions() {
        return java.util.List.of("Follow", "Stay", "Doggy", "Blowjob", "Stop");
    }
}
