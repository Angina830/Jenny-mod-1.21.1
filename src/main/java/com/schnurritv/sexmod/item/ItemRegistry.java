package com.schnurritv.sexmod.item;

import com.schnurritv.sexmod.Main;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    public static final RegistryObject<Item> BOOB_POTION = ITEMS.register("boob_potion",
            () -> new ItemBoobPotion(new Item.Properties().stacksTo(64), 2.0f));

    public static final RegistryObject<Item> BOOB_POTION_MEDIUM = ITEMS.register("boob_potion_medium", () -> new ItemBoobPotion(new Item.Properties().stacksTo(64), 0.66f));
    public static final RegistryObject<Item> BOOB_POTION_SMALL = ITEMS.register("boob_potion_small", () -> new ItemBoobPotion(new Item.Properties().stacksTo(64), 0.5f));
    public static final RegistryObject<Item> BOOB_POTION_TINY = ITEMS.register("boob_potion_tiny", () -> new ItemBoobPotion(new Item.Properties().stacksTo(64), 0.33f));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
