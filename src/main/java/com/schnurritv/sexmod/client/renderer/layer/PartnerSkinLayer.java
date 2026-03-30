package com.schnurritv.sexmod.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.schnurritv.sexmod.client.renderer.GirlRenderer;
import com.schnurritv.sexmod.entity.SexEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class PartnerSkinLayer<T extends SexEntity> extends GeoRenderLayer<T> {

    public PartnerSkinLayer(GeoEntityRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    private ResourceLocation cachedSkin = null;

    @Override
    public void preRender(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        cachedSkin = null;
        if (!animatable.getEntityData().get(SexEntity.IS_LOCKED)) return;

        String partnerUuid = animatable.getEntityData().get(SexEntity.PARTNER_UUID);
        if (partnerUuid == null || partnerUuid.equals("null") || partnerUuid.trim().isEmpty()) return;

        Player localPlayer = Minecraft.getInstance().player;

        if (localPlayer != null && partnerUuid.equals(localPlayer.getUUID().toString())) {
            cachedSkin = ((AbstractClientPlayer) localPlayer).getSkin().texture();
        } else {
            try {
                Player player = animatable.level().getPlayerByUUID(java.util.UUID.fromString(partnerUuid));
                if (player instanceof AbstractClientPlayer clientPlayer) {
                    cachedSkin = clientPlayer.getSkin().texture();
                }
            } catch (Exception e) {
                // Ignore invalid UUID syncs
            }
        }

        if (cachedSkin == null && localPlayer != null) {
            cachedSkin = ((AbstractClientPlayer) localPlayer).getSkin().texture();
        }
    }

    @Override
    public void renderForBone(PoseStack poseStack, T animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (cachedSkin == null) return;
        
        String name = bone.getName().toLowerCase();
        
        // Hide head only in first person to avoid obstructing view, but keep it in 3rd person/F5
        if ((name.equals("head2") || name.equals("steve_head") || name.equals("alex_head") || (name.contains("head") && isPartnerBone(bone))) && Minecraft.getInstance().options.getCameraType().isFirstPerson()) return;
        
        boolean inPartner = isPartnerBone(bone);

        if (inPartner) {
            if (isSexPartBone(bone)) return; // Let the main GirlRenderer pass handle sex parts
            
            if (!bone.isHidden()) {
                RenderType skinRenderType = RenderType.entityCutoutNoCull(cachedSkin);
                VertexConsumer skinBuffer = bufferSource.getBuffer(skinRenderType);
                
                for (software.bernie.geckolib.cache.object.GeoCube cube : bone.getCubes()) {
                    getRenderer().renderCube(poseStack, cube, skinBuffer, packedLight, packedOverlay, 0xFFFFFFFF);
                }
            }
        }
    }

    private boolean isPartnerBone(GeoBone bone) {
        GeoBone current = bone;
        while (current != null) {
            String n = current.getName().toLowerCase();
            if (n.equals("torso2") || n.contains("steve") || n.contains("alex") || n.contains("male") || n.contains("boy") || n.contains("partner")) {
                // Ensure we don't accidentally catch a girl's bone that is named weirdly, although unlikely
                return true;
            }
            current = current.getParent();
        }
        return false;
    }

    private boolean isSexPartBone(GeoBone bone) {
        String name = bone.getName().toLowerCase();
        return name.equals("cock") || name.equals("nut") || name.contains("shaft") || name.contains("ring") ||
               name.equals("balll") || name.equals("ballr") || name.contains("ball") ||
               (name.startsWith("bone") && name.length() > 4) || name.contains("penis") || name.contains("dick") ||
               name.contains("midsection") || name.contains("nutside") || name.contains("upside") ||
               name.contains("downside") || name.contains("lside") || name.contains("bottom") ||
               name.contains("backside");
    }

    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        // Nothing here. We use preRender to cache texture, and renderForBone to draw each bone.
    }

}
