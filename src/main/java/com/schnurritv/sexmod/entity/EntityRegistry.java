package com.schnurritv.sexmod.entity;

import com.schnurritv.sexmod.Main;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Main.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MODID);

    // Entities
    public static final RegistryObject<EntityType<com.schnurritv.sexmod.entity.jenny.JennyEntity>> JENNY = ENTITIES.register("jenny", 
            () -> EntityType.Builder.of(com.schnurritv.sexmod.entity.jenny.JennyEntity::new, MobCategory.CREATURE).sized(0.6f, 1.8f).build("jenny"));

    public static final RegistryObject<EntityType<com.schnurritv.sexmod.entity.ellie.EllieEntity>> ELLIE = ENTITIES.register("ellie", 
            () -> EntityType.Builder.of(com.schnurritv.sexmod.entity.ellie.EllieEntity::new, MobCategory.CREATURE).sized(0.6f, 1.8f).build("ellie"));
            
    public static final RegistryObject<EntityType<com.schnurritv.sexmod.entity.slime.SlimeEntity>> SLIME = ENTITIES.register("slime", 
            () -> EntityType.Builder.of(com.schnurritv.sexmod.entity.slime.SlimeEntity::new, MobCategory.CREATURE).sized(0.6f, 1.8f).build("slime"));

    public static final RegistryObject<EntityType<com.schnurritv.sexmod.entity.bee.BeeEntity>> BEE = ENTITIES.register("bee", 
            () -> EntityType.Builder.of(com.schnurritv.sexmod.entity.bee.BeeEntity::new, MobCategory.CREATURE).sized(0.6f, 1.8f).build("bee"));

    public static final RegistryObject<EntityType<com.schnurritv.sexmod.entity.bia.BiaEntity>> BIA = ENTITIES.register("bia", 
            () -> EntityType.Builder.of(com.schnurritv.sexmod.entity.bia.BiaEntity::new, MobCategory.CREATURE).sized(0.6f, 1.8f).build("bia"));

    public static final RegistryObject<EntityType<com.schnurritv.sexmod.entity.allie.AllieEntity>> ALLIE = ENTITIES.register("allie", 
            () -> EntityType.Builder.of(com.schnurritv.sexmod.entity.allie.AllieEntity::new, MobCategory.CREATURE).sized(0.6f, 1.8f).build("allie"));

    public static final RegistryObject<EntityType<com.schnurritv.sexmod.entity.goblin.GoblinEntity>> GOBLIN = ENTITIES.register("goblin", 
            () -> EntityType.Builder.of(com.schnurritv.sexmod.entity.goblin.GoblinEntity::new, MobCategory.CREATURE).sized(0.6f, 1.8f).build("goblin"));

    public static final RegistryObject<EntityType<com.schnurritv.sexmod.entity.kobold.KoboldEntity>> KOBOLD = ENTITIES.register("kobold", 
            () -> EntityType.Builder.of(com.schnurritv.sexmod.entity.kobold.KoboldEntity::new, MobCategory.CREATURE).sized(0.6f, 1.8f).build("kobold"));

    public static final RegistryObject<EntityType<com.schnurritv.sexmod.entity.cat.CatEntity>> CAT = ENTITIES.register("cat", 
            () -> EntityType.Builder.of(com.schnurritv.sexmod.entity.cat.CatEntity::new, MobCategory.CREATURE).sized(0.6f, 1.8f).build("cat"));

    // Items (Spawn Eggs)
    public static final RegistryObject<Item> JENNY_SPAWN_EGG = ITEMS.register("jenny_spawn_egg", 
            () -> new ForgeSpawnEggItem(JENNY, 0xFFA500, 0xFFFFFF, new Item.Properties()));

    public static final RegistryObject<Item> ELLIE_SPAWN_EGG = ITEMS.register("ellie_spawn_egg", 
            () -> new ForgeSpawnEggItem(ELLIE, 0xFF69B4, 0xFFFFFF, new Item.Properties()));

    public static final RegistryObject<Item> SLIME_SPAWN_EGG = ITEMS.register("slime_spawn_egg", 
            () -> new ForgeSpawnEggItem(SLIME, 0x00FF00, 0xFFFFFF, new Item.Properties()));

    public static final RegistryObject<Item> BEE_SPAWN_EGG = ITEMS.register("bee_spawn_egg", 
            () -> new ForgeSpawnEggItem(BEE, 0xFFFF00, 0x000000, new Item.Properties()));

    public static final RegistryObject<Item> BIA_SPAWN_EGG = ITEMS.register("bia_spawn_egg", 
            () -> new ForgeSpawnEggItem(BIA, 0x0000FF, 0xFFFFFF, new Item.Properties()));

    public static final RegistryObject<Item> ALLIE_SPAWN_EGG = ITEMS.register("allie_spawn_egg", 
            () -> new ForgeSpawnEggItem(ALLIE, 0x800080, 0xFFFFFF, new Item.Properties()));

    public static final RegistryObject<Item> GOBLIN_SPAWN_EGG = ITEMS.register("goblin_spawn_egg", 
            () -> new ForgeSpawnEggItem(GOBLIN, 0x006400, 0xFFFFFF, new Item.Properties()));

    public static final RegistryObject<Item> KOBOLD_SPAWN_EGG = ITEMS.register("kobold_spawn_egg", 
            () -> new ForgeSpawnEggItem(KOBOLD, 0x8B4513, 0xFFFFFF, new Item.Properties()));

    public static final RegistryObject<Item> CAT_SPAWN_EGG = ITEMS.register("cat_spawn_egg", 
            () -> new ForgeSpawnEggItem(CAT, 0xD2B48C, 0xFFFFFF, new Item.Properties()));

    // Creative Tab
    public static final RegistryObject<CreativeModeTab> SEXMOD_TAB = CREATIVE_MODE_TABS.register("sexmod_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.sexmod"))
                    .icon(() -> new ItemStack(JENNY_SPAWN_EGG.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(JENNY_SPAWN_EGG.get());
                        output.accept(ELLIE_SPAWN_EGG.get());
                        output.accept(SLIME_SPAWN_EGG.get());
                        output.accept(BEE_SPAWN_EGG.get());
                        output.accept(BIA_SPAWN_EGG.get());
                        output.accept(ALLIE_SPAWN_EGG.get());
                        output.accept(GOBLIN_SPAWN_EGG.get());
                        output.accept(KOBOLD_SPAWN_EGG.get());
                        output.accept(CAT_SPAWN_EGG.get());
                    })
                    .build());

    public static void register(net.minecraftforge.eventbus.api.IEventBus bus) {
        ENTITIES.register(bus);
        ITEMS.register(bus);
        CREATIVE_MODE_TABS.register(bus);
    }
}
