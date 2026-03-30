package com.schnurritv.sexmod.entity.bia;
import com.schnurritv.sexmod.entity.BaseGirlEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
public class BiaEntity extends BaseGirlEntity {
    public BiaEntity(EntityType<? extends PathfinderMob> type, Level level) { super(type, level); }
    @Override public String getGirlName() { return "bia"; }
    @Override public String getGeoFileName() { return "biadressed"; }
    @Override public String getNudeGeoFileName() { return "bianude"; }
    @Override public java.util.List<String> getAvailableActions() {
        return java.util.List.of("Follow", "Stay", "Doggy", "Stop");
    }
}
