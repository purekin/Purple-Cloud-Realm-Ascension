package net.thejadeproject.ascension.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.thejadeproject.ascension.AscensionCraft;
import net.thejadeproject.ascension.common.items.ModItems;
import net.thejadeproject.ascension.datagen.loot.AddItemModifier;
import net.thejadeproject.ascension.datagen.loot.AddPhysiqueRandomPurityModifier;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, AscensionCraft.MOD_ID);
    }
    @Override
    protected void start() {


        //Physiques
        add("mortal_from_chicken", new AddPhysiqueRandomPurityModifier(
                new LootItemCondition[] {
                        LootTableIdCondition.builder(ResourceLocation.parse("minecraft:entities/chicken")).build(),
                        LootItemRandomChanceCondition.randomChance(0.87f).build()
                },
                ResourceLocation.fromNamespaceAndPath(AscensionCraft.MOD_ID, "mortal"),
                15,
                25
        ));




        //Herbs

        this.add("hundred_year_ginseng_from_village", new AddItemModifier(new LootItemCondition[] {
                LootTableIdCondition.builder(ResourceLocation.withDefaultNamespace("chests/village/village_plains_house")).build(),
                LootItemRandomChanceCondition.randomChance(0.16F).build()
        }, ModItems.HUNDRED_YEAR_GINSENG.get()));
        this.add("hundred_year_ginseng_from_dungeon", new AddItemModifier(new LootItemCondition[] {
                LootTableIdCondition.builder(ResourceLocation.withDefaultNamespace("chests/simple_dungeon")).build(),
                LootItemRandomChanceCondition.randomChance(0.19F).build()
        }, ModItems.HUNDRED_YEAR_GINSENG.get()));
        this.add("hundred_year_snow_ginseng_from_igloo", new AddItemModifier(new LootItemCondition[] {
                LootTableIdCondition.builder(ResourceLocation.withDefaultNamespace("chests/igloo_chest")).build(),
                LootItemRandomChanceCondition.randomChance(0.18F).build()
        }, ModItems.HUNDRED_YEAR_SNOW_GINSENG.get()));
        this.add("hundred_year_snow_ginseng_from_snowy_village", new AddItemModifier(new LootItemCondition[] {
                LootTableIdCondition.builder(ResourceLocation.withDefaultNamespace("chests/village/village_snowy_house")).build(),
                LootItemRandomChanceCondition.randomChance(0.19F).build()
        }, ModItems.HUNDRED_YEAR_SNOW_GINSENG.get()));
        this.add("hundred_year_fire_ginseng_from_desert_temple", new AddItemModifier(new LootItemCondition[] {
                LootTableIdCondition.builder(ResourceLocation.withDefaultNamespace("chests/desert_pyramid")).build(),
                LootItemRandomChanceCondition.randomChance(0.14F).build()
        }, ModItems.HUNDRED_YEAR_FIRE_GINSENG.get()));
        this.add("hundred_year_fire_ginseng_from_bastion", new AddItemModifier(new LootItemCondition[] {
                LootTableIdCondition.builder(ResourceLocation.withDefaultNamespace("chests/bastion_treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.19F).build()
        }, ModItems.HUNDRED_YEAR_FIRE_GINSENG.get()));
        this.add("white_jade_orchid_from_mineshaft", new AddItemModifier(new LootItemCondition[] {
                LootTableIdCondition.builder(ResourceLocation.withDefaultNamespace("chests/abandoned_mineshaft")).build(),
                LootItemRandomChanceCondition.randomChance(0.20F).build()
        }, ModItems.WHITE_JADE_ORCHID.get()));
        this.add("white_jade_orchid_from_stronghold", new AddItemModifier(new LootItemCondition[] {
                LootTableIdCondition.builder(ResourceLocation.withDefaultNamespace("chests/stronghold_corridor")).build(),
                LootItemRandomChanceCondition.randomChance(0.18F).build()
        }, ModItems.WHITE_JADE_ORCHID.get()));



        this.add("jade_bamboo_of_serenity_from_bamboo", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("blocks/bamboo")).build(),
                LootItemRandomChanceCondition.randomChance(0.12F).build()}, ModItems.JADE_BAMBOO_OF_SERENITY.get()));

        //Living Core Drop Regular Mobs
        this.add("living_core_from_strider", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/strider")).build(),
                LootItemRandomChanceCondition.randomChance(0.15F).build()}, ModItems.LIVING_CORE.get()));
        this.add("living_core_from_piglin", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/piglin")).build(),
                LootItemRandomChanceCondition.randomChance(0.15F).build()}, ModItems.LIVING_CORE.get()));
        this.add("living_core_from_hoglin", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/hoglin")).build(),
                LootItemRandomChanceCondition.randomChance(0.15F).build()}, ModItems.LIVING_CORE.get()));
        this.add("living_core_from_zoglin", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/zoglin")).build(),
                LootItemRandomChanceCondition.randomChance(0.15F).build()}, ModItems.LIVING_CORE.get()));
        this.add("living_core_from_vex", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/vex")).build(),
                LootItemRandomChanceCondition.randomChance(0.15F).build()}, ModItems.LIVING_CORE.get()));
        this.add("living_core_from_allay", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/allay")).build(),
                LootItemRandomChanceCondition.randomChance(0.15F).build()}, ModItems.LIVING_CORE.get()));
        this.add("living_core_from_golem", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/iron_golem")).build(),
                LootItemRandomChanceCondition.randomChance(0.15F).build()}, ModItems.LIVING_CORE.get()));

        //Living Core Drop Boss Undead
        this.add("living_core_from_ender_dragon", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/ender_dragon")).build(),
                LootItemRandomChanceCondition.randomChance(0.29F).build()}, ModItems.LIVING_CORE.get()));







        //Undead Core Drop Regular Undead Mobs
        this.add("undead_core_from_zombie", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/zombie")).build(),
                LootItemRandomChanceCondition.randomChance(0.15F).build()}, ModItems.UNDEAD_CORE.get()));
        this.add("undead_core_from_skeleton", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/skeleton")).build(),
                LootItemRandomChanceCondition.randomChance(0.15F).build()}, ModItems.UNDEAD_CORE.get()));
        this.add("undead_core_from_wither_skeleton", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/wither_skeleton")).build(),
                LootItemRandomChanceCondition.randomChance(0.15F).build()}, ModItems.UNDEAD_CORE.get()));
        this.add("undead_core_from_stray", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/stray")).build(),
                LootItemRandomChanceCondition.randomChance(0.15F).build()}, ModItems.UNDEAD_CORE.get()));
        this.add("undead_core_from_husk", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/husk")).build(),
                LootItemRandomChanceCondition.randomChance(0.15F).build()}, ModItems.UNDEAD_CORE.get()));
        this.add("undead_core_from_drowned", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/drowned")).build(),
                LootItemRandomChanceCondition.randomChance(0.15F).build()}, ModItems.UNDEAD_CORE.get()));
        this.add("undead_core_from_zombie_villager", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/zombie_villager")).build(),
                LootItemRandomChanceCondition.randomChance(0.15F).build()}, ModItems.UNDEAD_CORE.get()));
        this.add("undead_core_from_zombified_piglin", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/zombified_piglin")).build(),
                LootItemRandomChanceCondition.randomChance(0.15F).build()}, ModItems.UNDEAD_CORE.get()));
        this.add("undead_core_from_zombie_horse", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/zombie_horse")).build(),
                LootItemRandomChanceCondition.randomChance(0.15F).build()}, ModItems.UNDEAD_CORE.get()));
        this.add("undead_core_from_phantom", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/phantom")).build(),
                LootItemRandomChanceCondition.randomChance(0.15F).build()}, ModItems.UNDEAD_CORE.get()));
        this.add("undead_core_from_zoglin", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/zoglin")).build(),
                LootItemRandomChanceCondition.randomChance(0.15F).build()}, ModItems.UNDEAD_CORE.get()));
        //Undead Core Drop Boss Undead
        this.add("undead_core_from_wither", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/wither")).build(),
                LootItemRandomChanceCondition.randomChance(0.29F).build()}, ModItems.UNDEAD_CORE.get()));



        // Example implementation of rank conditional drops (P.S. this can also be used in ModEntityLootTables (different pattern))

        /*
                Example of at least (if golden core or greater, drops a hundred-year ginseng when killed by a player)
        this.add("hundred_year_ginseng_from_ranked_zombie", new AddItemModifier(new LootItemCondition[] {
                LootTableIdCondition.builder(ResourceLocation.withDefaultNamespace("entities/zombie")).build(),
                LootItemKilledByPlayerCondition.killedByPlayer().build(),
                LootItemRandomChanceCondition.randomChance(0.10F).build(),
                MobRankLootCondition.atLeast("golden_core", 1).build()
        }, ModItems.HUNDRED_YEAR_GINSENG.get()));

                Example of exact (if exactly nascent soul, drops a white jade orchid when killed by a player)
        this.add("white_jade_orchid_from_ranked_zombie", new AddItemModifier(new LootItemCondition[] {
                LootTableIdCondition.builder(ResourceLocation.withDefaultNamespace("entities/zombie")).build(),
                LootItemKilledByPlayerCondition.killedByPlayer().build(),
                LootItemRandomChanceCondition.randomChance(0.25F).build(),
                MobRankLootCondition.exact("nascent_soul", 2).build()
        }, ModItems.WHITE_JADE_ORCHID.get()));

                Example of between (if between soul formation and tribulation transcendence, drops a hundred-year fire ginseng when killed by a player)
        this.add("hundred_year_fire_ginseng_from_ranked_zombie", new AddItemModifier(new LootItemCondition[] {
                LootTableIdCondition.builder(ResourceLocation.withDefaultNamespace("entities/zombie")).build(),
                LootItemKilledByPlayerCondition.killedByPlayer().build(),
                LootItemRandomChanceCondition.randomChance(0.18F).build(),
                MobRankLootCondition.between("soul_formation", 1, "tribulation_transcendence", 3).build()
        }, ModItems.HUNDRED_YEAR_FIRE_GINSENG.get()));
        */



    }
}
