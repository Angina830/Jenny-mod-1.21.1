package com.schnurritv.sexmod.client;

import software.bernie.geckolib.cache.object.GeoBone;

public class TestBone {
    public static void test(GeoBone bone) {
        // Just checking methods
        float x = bone.getPosX();
        float y = bone.getPosY();
        float z = bone.getPosZ();
        bone.setHidden(true);
    }
}
