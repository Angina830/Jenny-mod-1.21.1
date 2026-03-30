package com.schnurritv.sexmod.entity.kobold;
import com.schnurritv.sexmod.entity.BaseGirlEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
public class KoboldEntity extends BaseGirlEntity {
    public KoboldEntity(EntityType<? extends PathfinderMob> type, Level level) { super(type, level); }
    @Override public String getGirlName() { return "kobold"; }
    @Override public java.util.List<String> getAvailableActions() {
        return java.util.List.of("Follow", "Stay", "Blowjob", "Stop");
    }
}
