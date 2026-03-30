package com.schnurritv.sexmod.client;

import com.schnurritv.sexmod.Main;
import com.schnurritv.sexmod.SexModConfig;
import com.schnurritv.sexmod.client.gui.CameraConfigScreen;
import com.schnurritv.sexmod.entity.BaseGirlEntity;
import com.schnurritv.sexmod.entity.SexEntity;
import com.schnurritv.sexmod.networking.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
public class ClientForgeEvents {

    private static boolean wasJumpDown = false;
    private static boolean wasConfigKeyDown = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            if (player == null || mc.level == null) return;

            // --- Hotkey Alt + \ ---
            boolean isAltDown = GLFW.glfwGetKey(mc.getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS ||
                               GLFW.glfwGetKey(mc.getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_ALT) == GLFW.GLFW_PRESS;
                               
            boolean isBackslashDown = GLFW.glfwGetKey(mc.getWindow().getWindow(), GLFW.GLFW_KEY_BACKSLASH) == GLFW.GLFW_PRESS;
            
            if (isAltDown && isBackslashDown) {
                if (!wasConfigKeyDown) {
                    mc.setScreen(new CameraConfigScreen());
                }
                wasConfigKeyDown = true;
            } else {
                wasConfigKeyDown = false;
            }

            BaseGirlEntity partneredGirl = null;
            for (BaseGirlEntity girl : player.level().getEntitiesOfClass(BaseGirlEntity.class, player.getBoundingBox().inflate(10.0D))) {
                if (girl.getEntityData().get(SexEntity.IS_LOCKED) &&
                    girl.getEntityData().get(SexEntity.PARTNER_UUID).equals(player.getUUID().toString())) {
                    partneredGirl = girl;
                    break;
                }
            }

            if (partneredGirl != null) {
                // Exit scene on Sneak
                if (mc.options.keyShift.isDown()) {
                    NetworkHandler.sendSceneAction(partneredGirl.getId(), "Stop");
                    return;
                }

                boolean isJumpDown = mc.options.keyJump.isDown();
                if (isJumpDown && !wasJumpDown) {
                    NetworkHandler.sendSceneAction(partneredGirl.getId(), "Thrust");
                }
                wasJumpDown = isJumpDown;
            } else {
                wasJumpDown = false;
            }
        }
    }

    private static class CameraOffset {
        final double y;
        final double z;
        CameraOffset(double y, double z) { this.y = y; this.z = z; }
    }

    private static CameraOffset getCameraOffset(com.schnurritv.sexmod.entity.SexModAnimation anim) {
        String name = anim.name().toLowerCase();
        if (name.contains("doggy")) return new CameraOffset(SexModConfig.DOGGY_Y.get(), SexModConfig.DOGGY_Z.get());
        if (name.contains("missionary")) return new CameraOffset(SexModConfig.MISSIONARY_Y.get(), SexModConfig.MISSIONARY_Z.get());
        if (name.contains("blowjob")) return new CameraOffset(SexModConfig.BLOWJOB_Y.get(), SexModConfig.BLOWJOB_Z.get());
        if (name.contains("paizuri")) return new CameraOffset(SexModConfig.PAIZURI_Y.get(), SexModConfig.PAIZURI_Z.get());
        return new CameraOffset(SexModConfig.DEFAULT_Y.get(), SexModConfig.DEFAULT_Z.get());
    }

    @SubscribeEvent
    public static void onMovementInput(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        if (player == null) return;
        
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        for (BaseGirlEntity girl : player.level().getEntitiesOfClass(BaseGirlEntity.class, player.getBoundingBox().inflate(10.0D))) {
            if (girl.getEntityData().get(SexEntity.IS_LOCKED) &&
                girl.getEntityData().get(SexEntity.PARTNER_UUID).equals(player.getUUID().toString())) {
                var input = event.getInput();
                input.forwardImpulse = 0;
                input.leftImpulse = 0;
                input.up = false;
                input.down = false;
                input.left = false;
                input.right = false;
                input.jumping = false;
                return;
            }
        }
    }

    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        if (player == null) return;
        
        if (Minecraft.getInstance().level == null) return;
        
        for (net.minecraft.world.entity.Entity entity : player.level().getEntitiesOfClass(SexEntity.class, player.getBoundingBox().inflate(10.0D))) {
            if (entity instanceof SexEntity sexEntity && sexEntity.getEntityData().get(SexEntity.IS_LOCKED)) {
                 if (sexEntity.getEntityData().get(SexEntity.PARTNER_UUID).equals(player.getUUID().toString())) {
                     // Hide player entirely when locked in a scene (prevents own arm clipping)
                     event.setCanceled(true);
                     return;
                 }
            }
        }
    }

    @SubscribeEvent
    public static void onCameraSetup(net.minecraftforge.client.event.ViewportEvent.ComputeCameraAngles event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || mc.level == null) return;

        if (!mc.options.getCameraType().isFirstPerson()) return;

        for (BaseGirlEntity girl : player.level().getEntitiesOfClass(BaseGirlEntity.class, player.getBoundingBox().inflate(10.0D))) {
            if (girl.getEntityData().get(SexEntity.IS_LOCKED) &&
                girl.getEntityData().get(SexEntity.PARTNER_UUID).equals(player.getUUID().toString())) {
                
                float partialTick = (float) event.getPartialTick();
                double girlX = girl.xo + (girl.getX() - girl.xo) * partialTick;
                double girlY = girl.yo + (girl.getY() - girl.yo) * partialTick;
                double girlZ = girl.zo + (girl.getZ() - girl.zo) * partialTick;

                CameraOffset offset = getCameraOffset(girl.getSexModAnimation());
                Vec3 forward = Vec3.directionFromRotation(0, girl.getYRot());
                double camX = girlX - (forward.x * offset.z);
                double camY = girlY + offset.y;
                double camZ = girlZ - (forward.z * offset.z);

                try {
                    java.lang.reflect.Method setPosMethod = event.getCamera().getClass().getDeclaredMethod("m_90568_", double.class, double.class, double.class);
                    setPosMethod.setAccessible(true);
                    setPosMethod.invoke(event.getCamera(), camX, camY, camZ);
                } catch (Exception e1) {
                    try {
                        java.lang.reflect.Method setPosMethod = event.getCamera().getClass().getDeclaredMethod("setPosition", double.class, double.class, double.class);
                        setPosMethod.setAccessible(true);
                        setPosMethod.invoke(event.getCamera(), camX, camY, camZ);
                    } catch (Exception e2) {
                        // ignore
                    }
                }
                return;
            }
        }
    }
}
