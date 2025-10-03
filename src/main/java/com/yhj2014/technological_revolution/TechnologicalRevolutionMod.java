package com.yhj2014.technological_revolution;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TechnologicalRevolutionMod.MODID)
public class TechnologicalRevolutionMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "technological_revolution";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "technological_revolution" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "technological_revolution" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "technological_revolution" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    // Create a Deferred Register to hold BlockEntities which will all be registered under the "technological_revolution" namespace
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    // Create a Deferred Register to hold MenuTypes which will all be registered under the "technological_revolution" namespace
    public static final DeferredRegister<net.minecraft.world.inventory.MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

    // Creates a new Block with the id "technological_revolution:example_block", combining the namespace and path
    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    // Creates a new BlockItem with the id "technological_revolution:example_block", combining the namespace and path
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));

    // Creates a new food item with the id "technological_revolution:example_id", nutrition 1 and saturation 2
    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEat().nutrition(1).saturationMod(2f).build())));

    // Creates a creative tab with the id "technological_revolution:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("technological_revolution_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
            .build());

    // Technological Revolution Blocks
    // Energy Generators
    public static final RegistryObject<Block> SOLAR_PANEL = BLOCKS.register("solar_panel", 
        () -> new com.yhj2014.technological_revolution.block.SolarPanelBlock());
    public static final RegistryObject<Item> SOLAR_PANEL_ITEM = ITEMS.register("solar_panel", 
        () -> new BlockItem(SOLAR_PANEL.get(), new Item.Properties()));
        
    public static final RegistryObject<Block> WIND_TURBINE = BLOCKS.register("wind_turbine", 
        () -> new com.yhj2014.technological_revolution.block.AbstractMachineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)) {
            @Override
            public net.minecraft.world.level.block.entity.BlockEntity newBlockEntity(net.minecraft.core.BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
                return new com.yhj2014.technological_revolution.block.entity.WindTurbineBlockEntity(pos, state);
            }
        });
    public static final RegistryObject<Item> WIND_TURBINE_ITEM = ITEMS.register("wind_turbine", 
        () -> new BlockItem(WIND_TURBINE.get(), new Item.Properties()));
        
    public static final RegistryObject<Block> WATER_TURBINE = BLOCKS.register("water_turbine", 
        () -> new com.yhj2014.technological_revolution.block.AbstractMachineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)) {
            @Override
            public net.minecraft.world.level.block.entity.BlockEntity newBlockEntity(net.minecraft.core.BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
                return new com.yhj2014.technological_revolution.block.entity.WaterTurbineBlockEntity(pos, state);
            }
        });
    public static final RegistryObject<Item> WATER_TURBINE_ITEM = ITEMS.register("water_turbine", 
        () -> new BlockItem(WATER_TURBINE.get(), new Item.Properties()));
        
    public static final RegistryObject<Block> NUCLEAR_REACTOR = BLOCKS.register("nuclear_reactor", 
        () -> new com.yhj2014.technological_revolution.block.NuclearReactorBlock());
    public static final RegistryObject<Item> NUCLEAR_REACTOR_ITEM = ITEMS.register("nuclear_reactor", 
        () -> new BlockItem(NUCLEAR_REACTOR.get(), new Item.Properties()));

    // Machines
    public static final RegistryObject<Block> ORE_CRUSHER = BLOCKS.register("ore_crusher", 
        () -> new com.yhj2014.technological_revolution.block.AbstractMachineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)) {
            @Override
            public net.minecraft.world.level.block.entity.BlockEntity newBlockEntity(net.minecraft.core.BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
                return new com.yhj2014.technological_revolution.block.entity.OreCrusherBlockEntity(pos, state);
            }
        });
    public static final RegistryObject<Item> ORE_CRUSHER_ITEM = ITEMS.register("ore_crusher", 
        () -> new BlockItem(ORE_CRUSHER.get(), new Item.Properties()));
        
    public static final RegistryObject<Block> ADVANCED_FURNACE = BLOCKS.register("advanced_furnace", 
        () -> new com.yhj2014.technological_revolution.block.AbstractMachineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)) {
            @Override
            public net.minecraft.world.level.block.entity.BlockEntity newBlockEntity(net.minecraft.core.BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
                return new com.yhj2014.technological_revolution.block.entity.AdvancedFurnaceBlockEntity(pos, state);
            }
        });
    public static final RegistryObject<Item> ADVANCED_FURNACE_ITEM = ITEMS.register("advanced_furnace", 
        () -> new BlockItem(ADVANCED_FURNACE.get(), new Item.Properties()));

    // Wires
    public static final RegistryObject<Block> COPPER_WIRE = BLOCKS.register("copper_wire", 
        () -> new com.yhj2014.technological_revolution.block.WireBlock(64));
    public static final RegistryObject<Item> COPPER_WIRE_ITEM = ITEMS.register("copper_wire", 
        () -> new BlockItem(COPPER_WIRE.get(), new Item.Properties()));
        
    public static final RegistryObject<Block> GOLD_WIRE = BLOCKS.register("gold_wire", 
        () -> new com.yhj2014.technological_revolution.block.WireBlock(256));
    public static final RegistryObject<Item> GOLD_WIRE_ITEM = ITEMS.register("gold_wire", 
        () -> new BlockItem(GOLD_WIRE.get(), new Item.Properties()));

    // Ores
    public static final RegistryObject<Block> URANIUM_ORE = BLOCKS.register("uranium_ore", 
        () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).strength(3.0F, 3.0F)));
    public static final RegistryObject<Item> URANIUM_ORE_ITEM = ITEMS.register("uranium_ore", 
        () -> new BlockItem(URANIUM_ORE.get(), new Item.Properties()));
        
    public static final RegistryObject<Block> LITHIUM_ORE = BLOCKS.register("lithium_ore", 
        () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).strength(3.0F, 3.0F)));
    public static final RegistryObject<Item> LITHIUM_ORE_ITEM = ITEMS.register("lithium_ore", 
        () -> new BlockItem(LITHIUM_ORE.get(), new Item.Properties()));
        
    public static final RegistryObject<Block> TITANIUM_ORE = BLOCKS.register("titanium_ore", 
        () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(4.0F, 4.0F)));
    public static final RegistryObject<Item> TITANIUM_ORE_ITEM = ITEMS.register("titanium_ore", 
        () -> new BlockItem(TITANIUM_ORE.get(), new Item.Properties()));

    // Energy Items
    public static final RegistryObject<Item> URANIUM_INGOT = ITEMS.register("uranium_ingot", 
        () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LITHIUM_INGOT = ITEMS.register("lithium_ingot", 
        () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot", 
        () -> new Item(new Item.Properties()));
        
    // Energy Tools
    public static final RegistryObject<Item> ENERGY_PICKAXE = ITEMS.register("energy_pickaxe", 
        () -> new com.yhj2014.technological_revolution.item.EnergyPickaxeItem(100000, 100, net.minecraft.world.item.Tiers.DIAMOND, 1, -2.8F, new Item.Properties()));
        
    // Energy Armor
    public static final RegistryObject<Item> ENERGY_HELMET = ITEMS.register("energy_helmet", 
        () -> new com.yhj2014.technological_revolution.item.EnergyArmorItem(50000, 50, com.yhj2014.technological_revolution.item.ModArmorMaterials.ENERGY, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ENERGY_CHESTPLATE = ITEMS.register("energy_chestplate", 
        () -> new com.yhj2014.technological_revolution.item.EnergyArmorItem(100000, 50, com.yhj2014.technological_revolution.item.ModArmorMaterials.ENERGY, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> ENERGY_LEGGINGS = ITEMS.register("energy_leggings", 
        () -> new com.yhj2014.technological_revolution.item.EnergyArmorItem(75000, 50, com.yhj2014.technological_revolution.item.ModArmorMaterials.ENERGY, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> ENERGY_BOOTS = ITEMS.register("energy_boots", 
        () -> new com.yhj2014.technological_revolution.item.EnergyArmorItem(50000, 50, com.yhj2014.technological_revolution.item.ModArmorMaterials.ENERGY, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));

    // Containers
    public static final RegistryObject<net.minecraft.world.inventory.MenuType<com.yhj2014.technological_revolution.container.SolarPanelContainer>> SOLAR_PANEL_CONTAINER = MENUS.register("solar_panel_container",
        () -> new net.minecraft.world.inventory.MenuType<>(new IContainerFactory<com.yhj2014.technological_revolution.container.SolarPanelContainer>() {
            @Override
            public com.yhj2014.technological_revolution.container.SolarPanelContainer create(int id, net.minecraft.world.entity.player.Inventory playerInventory, net.minecraft.network.FriendlyByteBuf data) {
                return new com.yhj2014.technological_revolution.container.SolarPanelContainer(id, playerInventory);
            }
        }, net.minecraft.world.flag.FeatureFlags.VANILLA_SET));
            
    public static final RegistryObject<net.minecraft.world.inventory.MenuType<com.yhj2014.technological_revolution.container.OreCrusherContainer>> ORE_CRUSHER_CONTAINER = MENUS.register("ore_crusher_container",
        () -> new net.minecraft.world.inventory.MenuType<>(new IContainerFactory<com.yhj2014.technological_revolution.container.OreCrusherContainer>() {
            @Override
            public com.yhj2014.technological_revolution.container.OreCrusherContainer create(int id, net.minecraft.world.entity.player.Inventory playerInventory, net.minecraft.network.FriendlyByteBuf data) {
                return new com.yhj2014.technological_revolution.container.OreCrusherContainer(id, playerInventory);
            }
        }, net.minecraft.world.flag.FeatureFlags.VANILLA_SET));
            
    public static final RegistryObject<net.minecraft.world.inventory.MenuType<com.yhj2014.technological_revolution.container.AdvancedFurnaceContainer>> ADVANCED_FURNACE_CONTAINER = MENUS.register("advanced_furnace_container",
        () -> new net.minecraft.world.inventory.MenuType<>(new IContainerFactory<com.yhj2014.technological_revolution.container.AdvancedFurnaceContainer>() {
            @Override
            public com.yhj2014.technological_revolution.container.AdvancedFurnaceContainer create(int id, net.minecraft.world.entity.player.Inventory playerInventory, net.minecraft.network.FriendlyByteBuf data) {
                return new com.yhj2014.technological_revolution.container.AdvancedFurnaceContainer(id, playerInventory);
            }
        }, net.minecraft.world.flag.FeatureFlags.VANILLA_SET));
            
    public static final RegistryObject<net.minecraft.world.inventory.MenuType<com.yhj2014.technological_revolution.container.NuclearReactorContainer>> NUCLEAR_REACTOR_CONTAINER = MENUS.register("nuclear_reactor_container",
        () -> new net.minecraft.world.inventory.MenuType<>(new IContainerFactory<com.yhj2014.technological_revolution.container.NuclearReactorContainer>() {
            @Override
            public com.yhj2014.technological_revolution.container.NuclearReactorContainer create(int id, net.minecraft.world.entity.player.Inventory playerInventory, net.minecraft.network.FriendlyByteBuf data) {
                return new com.yhj2014.technological_revolution.container.NuclearReactorContainer(id, playerInventory);
            }
        }, net.minecraft.world.flag.FeatureFlags.VANILLA_SET));

    // Block Entities
    public static final RegistryObject<BlockEntityType<?>> SOLAR_PANEL_ENTITY = BLOCK_ENTITIES.register("solar_panel_entity",
        () -> BlockEntityType.Builder.of(com.yhj2014.technological_revolution.block.entity.SolarPanelBlockEntity::new,
            SOLAR_PANEL.get()).build(null));
            
    public static final RegistryObject<BlockEntityType<?>> WIND_TURBINE_ENTITY = BLOCK_ENTITIES.register("wind_turbine_entity",
        () -> BlockEntityType.Builder.of(com.yhj2014.technological_revolution.block.entity.WindTurbineBlockEntity::new,
            WIND_TURBINE.get()).build(null));
            
    public static final RegistryObject<BlockEntityType<?>> WATER_TURBINE_ENTITY = BLOCK_ENTITIES.register("water_turbine_entity",
        () -> BlockEntityType.Builder.of(com.yhj2014.technological_revolution.block.entity.WaterTurbineBlockEntity::new,
            WATER_TURBINE.get()).build(null));
            
    public static final RegistryObject<BlockEntityType<?>> ORE_CRUSHER_ENTITY = BLOCK_ENTITIES.register("ore_crusher_entity",
        () -> BlockEntityType.Builder.of(com.yhj2014.technological_revolution.block.entity.OreCrusherBlockEntity::new,
            ORE_CRUSHER.get()).build(null));
            
    public static final RegistryObject<BlockEntityType<?>> ADVANCED_FURNACE_ENTITY = BLOCK_ENTITIES.register("advanced_furnace_entity",
        () -> BlockEntityType.Builder.of(com.yhj2014.technological_revolution.block.entity.AdvancedFurnaceBlockEntity::new,
            ADVANCED_FURNACE.get()).build(null));
            
    public static final RegistryObject<BlockEntityType<?>> WIRE_BLOCK_ENTITY = BLOCK_ENTITIES.register("wire_block_entity",
        () -> BlockEntityType.Builder.of(com.yhj2014.technological_revolution.block.entity.WireBlockEntity::new,
            COPPER_WIRE.get(), GOLD_WIRE.get()).build(null));
            
    public static final RegistryObject<BlockEntityType<?>> NUCLEAR_REACTOR_ENTITY = BLOCK_ENTITIES.register("nuclear_reactor_entity",
        () -> BlockEntityType.Builder.of(com.yhj2014.technological_revolution.block.entity.NuclearReactorBlockEntity::new,
            NUCLEAR_REACTOR.get()).build(null));

    public TechnologicalRevolutionMod(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so block entities get registered
        BLOCK_ENTITIES.register(modEventBus);
        // Register the Deferred Register to the mod event bus so menus get registered
        MENUS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
        
        // 注册进度
        event.enqueueWork(() -> {
            com.yhj2014.technological_revolution.advancement.ModAdvancements.register();
        });
        
        // 注册配方条件
        event.enqueueWork(() -> {
            net.minecraftforge.common.crafting.CraftingHelper.register(com.yhj2014.technological_revolution.recipe.ResearchCondition.Serializer.INSTANCE);
        });
        
        LOGGER.info("Technological Revolution Mod initialized");
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(EXAMPLE_BLOCK_ITEM);
            
        // 添加所有方块和物品到科技革命标签页
        if (event.getTab() == EXAMPLE_TAB.get()) {
            // 添加所有方块
            event.accept(SOLAR_PANEL_ITEM.get());
            event.accept(WIND_TURBINE_ITEM.get());
            event.accept(WATER_TURBINE_ITEM.get());
            event.accept(NUCLEAR_REACTOR_ITEM.get());
            event.accept(ORE_CRUSHER_ITEM.get());
            event.accept(ADVANCED_FURNACE_ITEM.get());
            event.accept(COPPER_WIRE_ITEM.get());
            event.accept(GOLD_WIRE_ITEM.get());
            event.accept(URANIUM_ORE_ITEM.get());
            event.accept(LITHIUM_ORE_ITEM.get());
            event.accept(TITANIUM_ORE_ITEM.get());
            
            // 添加所有物品
            event.accept(URANIUM_INGOT.get());
            event.accept(LITHIUM_INGOT.get());
            event.accept(TITANIUM_INGOT.get());
            event.accept(ENERGY_PICKAXE.get());
            event.accept(ENERGY_HELMET.get());
            event.accept(ENERGY_CHESTPLATE.get());
            event.accept(ENERGY_LEGGINGS.get());
            event.accept(ENERGY_BOOTS.get());
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            
            // 注册屏幕
            event.enqueueWork(() -> {
                net.minecraft.client.gui.screens.MenuScreens.register(SOLAR_PANEL_CONTAINER.get(), com.yhj2014.technological_revolution.client.screen.SolarPanelScreen::new);
                net.minecraft.client.gui.screens.MenuScreens.register(ORE_CRUSHER_CONTAINER.get(), com.yhj2014.technological_revolution.client.screen.OreCrusherScreen::new);
                net.minecraft.client.gui.screens.MenuScreens.register(ADVANCED_FURNACE_CONTAINER.get(), com.yhj2014.technological_revolution.client.screen.AdvancedFurnaceScreen::new);
                net.minecraft.client.gui.screens.MenuScreens.register(NUCLEAR_REACTOR_CONTAINER.get(), com.yhj2014.technological_revolution.client.screen.NuclearReactorScreen::new);
            });
        }
        
        @SubscribeEvent
        public static void registerBlockColors(net.minecraftforge.client.event.RegisterColorHandlersEvent.Block event) {
            // 注册方块颜色处理器
            event.register(new com.yhj2014.technological_revolution.client.render.ModBlockColor(), 
                SOLAR_PANEL.get(), WIND_TURBINE.get(), WATER_TURBINE.get(), NUCLEAR_REACTOR.get(),
                ORE_CRUSHER.get(), ADVANCED_FURNACE.get(), COPPER_WIRE.get(), GOLD_WIRE.get(),
                URANIUM_ORE.get(), LITHIUM_ORE.get(), TITANIUM_ORE.get());
        }
        
        @SubscribeEvent
        public static void registerItemColors(net.minecraftforge.client.event.RegisterColorHandlersEvent.Item event) {
            // 注册物品颜色处理器
            event.register(new com.yhj2014.technological_revolution.client.render.ModItemColor(),
                SOLAR_PANEL_ITEM.get(), WIND_TURBINE_ITEM.get(), WATER_TURBINE_ITEM.get(), NUCLEAR_REACTOR_ITEM.get(),
                ORE_CRUSHER_ITEM.get(), ADVANCED_FURNACE_ITEM.get(), COPPER_WIRE_ITEM.get(), GOLD_WIRE_ITEM.get(),
                URANIUM_ORE_ITEM.get(), LITHIUM_ORE_ITEM.get(), TITANIUM_ORE_ITEM.get(),
                URANIUM_INGOT.get(), LITHIUM_INGOT.get(), TITANIUM_INGOT.get(),
                ENERGY_PICKAXE.get(), ENERGY_HELMET.get(), ENERGY_CHESTPLATE.get(), 
                ENERGY_LEGGINGS.get(), ENERGY_BOOTS.get());
        }
    }
    
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModWorldEvents {
        @SubscribeEvent
        public static void onBootstrapConfigured(net.minecraftforge.registries.RegisterEvent event) {
            // 注册世界生成
            // 这里将在后续版本中实现
        }
    }
}
