package com.schnurritv.sexmod.entity.jenny;
import com.schnurritv.sexmod.entity.BaseGirlEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
public class JennyEntity extends BaseGirlEntity {
    public JennyEntity(EntityType<? extends PathfinderMob> type, Level level) { super(type, level); }
    @Override public String getGirlName() { return "jenny"; }
    @Override public String getGeoFileName() { return "jennydressed"; }
    @Override public String getNudeGeoFileName() { return "jennynude"; }
    @Override public java.util.List<String> getAvailableActions() {
        String homePosStr = this.entityData.get(HOME_POS);
        if (!"0|0|0".equals(homePosStr)) {
            String[] parts = homePosStr.split("\\|");
            if (parts.length == 3) {
                try {
                    double hX = Double.parseDouble(parts[0]);
                    double hY = Double.parseDouble(parts[1]);
                    double hZ = Double.parseDouble(parts[2]);
                    if (this.distanceToSqr(hX, hY, hZ) < 100.0) { // 10 blocks (squared)
                        return java.util.List.of("Follow", "Stay", "Missionary", "Doggy", "Blowjob", "Boobjob", "Leave House", "Stop");
                    }
                } catch (Exception ignored) {}
            }
        }
        return java.util.List.of("Follow", "Stay", "Missionary", "Doggy", "Blowjob", "Boobjob", "House", "Stop");
    }
}
