package com.schnurritv.sexmod.entity.ellie;
import com.schnurritv.sexmod.entity.BaseGirlEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
public class EllieEntity extends BaseGirlEntity {
    public EllieEntity(EntityType<? extends PathfinderMob> type, Level level) { super(type, level); }
    @Override public String getGirlName() { return "ellie"; }
    @Override public String getGeoFileName() { return "dressed"; }
    @Override public String getNudeGeoFileName() { return "nude"; }
    @Override public java.util.List<String> getAvailableActions() {
        return java.util.List.of("Follow", "Stay", "Missionary", "Stop");
    }
}
