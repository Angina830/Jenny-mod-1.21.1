package com.schnurritv.sexmod.client.model;

import com.schnurritv.sexmod.Main;
import com.schnurritv.sexmod.entity.SexEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GirlModel<T extends SexEntity> extends GeoModel<T> {
    @Override
    public ResourceLocation getModelResource(T animatable) {
        String name = animatable.getModelName();
        int clothingState = animatable.getEntityData().get(com.schnurritv.sexmod.entity.SexEntity.CLOTHING_STATE);
        String geoFile = clothingState > 0 ? animatable.getNudeGeoFileName() : animatable.getGeoFileName();
        return ResourceLocation.fromNamespaceAndPath(Main.MODID, "geo/" + name + "/" + geoFile + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        String name = animatable.getModelName();
        return ResourceLocation.fromNamespaceAndPath(Main.MODID, "textures/entity/" + name + "/" + name + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        String name = animatable.getModelName();
        return ResourceLocation.fromNamespaceAndPath(Main.MODID, "animations/" + name + "/" + name + ".animation.json");
    }
}
