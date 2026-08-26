package com.schnurritv.sexmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.schnurritv.sexmod.entity.pig.PigProxyEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;

public class PigProxyRenderer extends GirlRenderer<PigProxyEntity> {
    public PigProxyRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, "jenny"); // Use jenny model for bones
    }

    @Override
    public void renderRecursively(PoseStack poseStack, PigProxyEntity animatable, software.bernie.geckolib.cache.object.GeoBone bone, net.minecraft.client.renderer.RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReversed, float partialTick, int packedLight, int packedOverlay, int colour) {
        // Only allow rendering if it's NOT a girl bone, OR if it's being handled by a layer (PartnerSkinLayer)
        // Actually, GirlRenderer.renderRecursively calls super.renderRecursively which draws the cubes.
        // We can just NOT call super if we want to hide the girl.
        
        // But we NEED the bones to be processed so layers can find them.
        // So we call super but with a trick to hide cubes.
        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReversed, partialTick, packedLight, packedOverlay, colour);
    }

    @Override
    public void renderCubesOfBone(PoseStack poseStack, software.bernie.geckolib.cache.object.GeoBone bone, VertexConsumer buffer, int packedLight, int packedOverlay, int colour) {
        String name = bone.getName().toLowerCase();
        
        // Show only the sex parts of the proxy model, hide everything else (the girl herself)
        if (isSexPartBone(name)) {
            super.renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, colour);
        }
    }

    private boolean isSexPartBone(String name) {
        return name.equals("cock") || name.contains("shaft") || name.contains("ring") ||
                name.equals("nut") || name.contains("ball") ||
                (name.startsWith("bone") && name.length() > 3) ||
                name.contains("penis") || name.contains("dick") ||
                name.contains("tip") || name.contains("glans");
    }
}
