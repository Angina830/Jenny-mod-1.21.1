package com.schnurritv.sexmod;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SexModConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.DoubleValue DOGGY_Y;
    public static final ForgeConfigSpec.DoubleValue DOGGY_Z;
    public static final ForgeConfigSpec.DoubleValue MISSIONARY_Y;
    public static final ForgeConfigSpec.DoubleValue MISSIONARY_Z;
    public static final ForgeConfigSpec.DoubleValue BLOWJOB_Y;
    public static final ForgeConfigSpec.DoubleValue BLOWJOB_Z;
    public static final ForgeConfigSpec.DoubleValue PAIZURI_Y;
    public static final ForgeConfigSpec.DoubleValue PAIZURI_Z;
    public static final ForgeConfigSpec.DoubleValue DEFAULT_Y;
    public static final ForgeConfigSpec.DoubleValue DEFAULT_Z;

    static {
        BUILDER.push("Camera Offsets");

        BUILDER.comment("Vertical (Y) and Distance (Z) offsets for different scenes.");

        DOGGY_Y = BUILDER.defineInRange("doggyY", 1.5, 0.0, 5.0);
        DOGGY_Z = BUILDER.defineInRange("doggyZ", 0.6, 0.0, 5.0);

        MISSIONARY_Y = BUILDER.defineInRange("missionaryY", 1.7, 0.0, 5.0);
        MISSIONARY_Z = BUILDER.defineInRange("missionaryZ", 0.7, 0.0, 5.0);

        BLOWJOB_Y = BUILDER.defineInRange("blowjobY", 1.2, 0.0, 5.0);
        BLOWJOB_Z = BUILDER.defineInRange("blowjobZ", 0.5, 0.0, 5.0);

        PAIZURI_Y = BUILDER.defineInRange("paizuriY", 1.4, 0.0, 5.0);
        PAIZURI_Z = BUILDER.defineInRange("paizuriZ", 0.6, 0.0, 5.0);

        DEFAULT_Y = BUILDER.defineInRange("defaultY", 1.5, 0.0, 5.0);
        DEFAULT_Z = BUILDER.defineInRange("defaultZ", 0.6, 0.0, 5.0);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
