package com.schnurritv.sexmod.entity;

public enum SexModAnimation {
    NULL(0, true),
    IDLE(0, true),
    WALK(0, true),
    MISS_IDLE(40, true),
    MISSIONARY_START(65, true),
    MISSIONARY_SLOW(58, true),
    MISSIONARY_FAST(37, true),
    MISSIONARY_CUM(289, true),
    BLOWJOBINTRO(60, true),
    BLOWJOBSUCK(40, true),
    BLOWJOBTHRUST(20, true),
    BLOWJOBCUM(80, true),
    PAIZURI_START(60, true),
    PAIZURI_SLOW(40, true),
    PAIZURI_FAST(20, true),
    PAIZURI_CUM(80, true),
    DOGGYSTART(74, true),
    DOGGYGOONBED(131, true),
    DOGGYWAIT(129, true),
    DOGGYSLOW(40, true),
    DOGGYFAST(20, true),
    DOGGYCUM(80, true);

    private final int length;
    public final boolean autoBlink;

    SexModAnimation(int length, boolean autoBlink) {
        this.length = length;
        this.autoBlink = autoBlink;
    }

    public int getLength() {
        return length;
    }
}
