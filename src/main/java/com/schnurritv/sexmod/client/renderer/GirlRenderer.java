package com.schnurritv.sexmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.schnurritv.sexmod.client.model.GirlModel;
import com.schnurritv.sexmod.entity.SexEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.util.HashSet;
import java.util.Set;

public class GirlRenderer<T extends SexEntity> extends GeoEntityRenderer<T> {

    private final Set<String> skipCubeBones = new HashSet<>();
    private static final ResourceLocation SKIN_SOLID_TEXTURE = ResourceLocation.fromNamespaceAndPath("sexmod", "textures/entity/skin_solid.png");
    private static final ResourceLocation SKIN_PINK_TEXTURE = ResourceLocation.fromNamespaceAndPath("sexmod", "textures/entity/skin_pink.png");

    public GirlRenderer(EntityRendererProvider.Context renderManager, String girlName) {
        super(renderManager, new GirlModel<>());
        
        try {
            java.lang.reflect.Field field = software.bernie.geckolib.renderer.GeoEntityRenderer.class.getDeclaredField("renderLayers");
            field.setAccessible(true);
            java.util.List<?> list = (java.util.List<?>) field.get(this);
            list.clear();
        } catch (Exception e) {
            try {
                java.lang.reflect.Field field = software.bernie.geckolib.renderer.GeoRenderer.class.getDeclaredField("renderLayers");
                field.setAccessible(true);
                java.util.List<?> list = (java.util.List<?>) field.get(this);
                list.clear();
            } catch (Exception ignored) {}
        }
        
        super.addRenderLayer(new com.schnurritv.sexmod.client.renderer.layer.PartnerSkinLayer<>(this));
    }

    @Override
    public net.minecraft.client.renderer.RenderType getRenderType(T animatable, ResourceLocation texture, @org.jetbrains.annotations.Nullable net.minecraft.client.renderer.MultiBufferSource bufferSource, float partialTick) {
        return net.minecraft.client.renderer.RenderType.entityCutoutNoCull(texture);
    }

    @Override
    public void preRender(PoseStack poseStack, T animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReversed, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReversed, partialTick, packedLight, packedOverlay, colour);
        
        boolean inScene = animatable.getEntityData().get(SexEntity.IS_LOCKED);
        applyCustomRotations(model.topLevelBones().get(0), inScene); // Start recursion for cock tilt
        
        skipCubeBones.clear();
        for (GeoBone bone : model.topLevelBones()) {
            filterBones(bone, animatable, false);
        }
    }

    private void applyCustomRotations(GeoBone bone, boolean inScene) {
        String name = bone.getName().toLowerCase();
        if (inScene && name.equals("cock")) {
            // Tilting the penis root bone by 30 degrees (approx 0.52 radians)
            // Using setRotX ensures rotation around PIVOT, not the entity center
            bone.setRotX(bone.getRotX() + 0.5236f); // 30 degrees in radians
        }
        for (GeoBone child : bone.getChildBones()) {
            applyCustomRotations(child, inScene);
        }
    }

    @Override
    public void renderRecursively(PoseStack poseStack, T animatable, GeoBone bone, net.minecraft.client.renderer.RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReversed, float partialTick, int packedLight, int packedOverlay, int colour) {
        boolean inScene = animatable.getEntityData().get(SexEntity.IS_LOCKED);
        boolean shifted = false;
        
        if (inScene && bone.getParent() == null) {
            String name = bone.getName().toLowerCase();
            poseStack.pushPose();
            if (name.equals("steve") || name.equals("alex") || name.equals("partner") || name.equals("male") || name.equals("boy")) {
                // Partner: Final calibrated pos (-0.7y, 0.7z relative to bed top)
                poseStack.translate(0, -0.7f, 0.7f);
            } else {
                // Jenny: Final calibrated pos (-0.68y, 0.7z relative to bed top)
                poseStack.translate(0, -0.68f, 0.7f);
            }
            shifted = true;
        }
        
        // Swap buffer for sex part bones in partner tree to use solid skin/pink texture
        VertexConsumer effectiveBuffer = buffer;
        if (inScene && isSexPartBone(bone) && isInPartnerTree(bone)) {
            if (isGlansBone(bone)) {
                net.minecraft.client.renderer.RenderType pinkRenderType = net.minecraft.client.renderer.RenderType.entityCutoutNoCull(SKIN_PINK_TEXTURE);
                effectiveBuffer = bufferSource.getBuffer(pinkRenderType);
            } else {
                net.minecraft.client.renderer.RenderType skinRenderType = net.minecraft.client.renderer.RenderType.entityCutoutNoCull(SKIN_SOLID_TEXTURE);
                effectiveBuffer = bufferSource.getBuffer(skinRenderType);
            }
        }
        
        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, effectiveBuffer, isReversed, partialTick, packedLight, packedOverlay, colour);
        
        if (shifted) {
            poseStack.popPose();
        }
    }

    @Override
    public void renderCubesOfBone(PoseStack poseStack, GeoBone bone, VertexConsumer buffer, int packedLight, int packedOverlay, int colour) {
        if (skipCubeBones.contains(bone.getName().toLowerCase())) {
            String name = bone.getName().toLowerCase();
            if (name.contains("head")) {
                if (net.minecraft.client.Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                    return;
                }
            } else {
                return;
            }
        }
        super.renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, colour);
    }

    private void filterBones(GeoBone bone, T animatable, boolean insidePartnerTree) {
        String name = bone.getName().toLowerCase();
        
        bone.setHidden(false);

        // During a scene, show the partner model (player participation)
        boolean inScene = animatable.getEntityData().get(SexEntity.IS_LOCKED);

        // --- Check if this bone is a partner tree root ---
        boolean isPartnerRoot = name.equals("torso2") || name.contains("steve") || name.contains("alex") || name.contains("male") || name.contains("boy") || name.contains("partner");
        boolean inPartner = insidePartnerTree || isPartnerRoot;

        // --- Inside partner subtree: hide everything when NOT in scene ---
        if (inPartner && !inScene) {
            bone.setHidden(true);
            return;
        }

        // In scene + partner tree: pass partner context to children and skip cubes so they can be rendered by PartnerSkinLayer
        if (inPartner && inScene) {
            if (!isSexPartBone(bone)) {
                skipCubeBones.add(name);
            }
            for (GeoBone child : bone.getChildBones()) {
                filterBones(child, animatable, true);
            }
            return;
        }

        // --- Outside partner tree: hide standalone phantom bones (only when NOT in scene) ---
        boolean isStandalonePhantom = name.contains("steve") || 
                                     name.contains("alex") || 
                                     name.contains("partner") || 
                                     name.contains("diamond") || 
                                     name.contains("male") || 
                                     name.contains("boy");

        if (isStandalonePhantom && !inScene) {
            bone.setHidden(true);
            return;
        }

        for (GeoBone child : bone.getChildBones()) {
            filterBones(child, animatable, false);
        }
    }

    private boolean isSexPartBone(GeoBone bone) {
        String name = bone.getName().toLowerCase();
        return name.equals("cock") || name.equals("nut") || name.contains("shaft") || name.contains("ring") ||
               name.equals("balll") || name.equals("ballr") || name.contains("ball") ||
               (name.startsWith("bone") && name.length() > 3) || name.contains("penis") || name.contains("dick") ||
               name.contains("midsection") || name.contains("nutside") || name.contains("upside") ||
               name.contains("downside") || name.contains("lside") || name.contains("bottom") ||
               name.contains("backside") || name.contains("tip") || name.contains("glans");
    }

    private boolean isInPartnerTree(GeoBone bone) {
        GeoBone current = bone;
        while (current != null) {
            String n = current.getName().toLowerCase();
            if (n.equals("torso2") || n.contains("steve") || n.contains("alex") || n.contains("male") || n.contains("boy") || n.contains("partner")) {
                return true;
            }
            current = current.getParent();
        }
        return false;
    }

    private boolean isGlansBone(GeoBone bone) {
        // Check if this bone or any of its parents are the "nut" (glans/head)
        GeoBone current = bone;
        while (current != null) {
            String n = current.getName().toLowerCase();
            if (n.contains("nut") || n.contains("midsection") || n.contains("tip") || n.contains("glans")) {
                return true;
            }
            current = current.getParent();
        }
        return false;
    }
}
