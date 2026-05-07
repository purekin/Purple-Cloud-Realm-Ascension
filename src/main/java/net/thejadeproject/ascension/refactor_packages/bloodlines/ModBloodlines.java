package net.thejadeproject.ascension.refactor_packages.bloodlines;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thejadeproject.ascension.AscensionCraft;
import net.thejadeproject.ascension.refactor_packages.bloodlines.custom.AwakenedBeastBloodline;
import net.thejadeproject.ascension.refactor_packages.bloodlines.custom.GenericBloodline;
import net.thejadeproject.ascension.refactor_packages.bloodlines.custom.PurityBloodline;
import net.thejadeproject.ascension.refactor_packages.registries.AscensionRegistries;
import net.thejadeproject.ascension.util.ModAttributes;

public class ModBloodlines {

    public static final DeferredRegister<IBloodline> BLOODLINES =
            DeferredRegister.create(AscensionRegistries.Bloodlines.BLOODLINE_REGISTRY, AscensionCraft.MOD_ID);

    // ── Modifier keys ────────────────────────────────────────────────────────
    private static final ResourceLocation PHOENIX_KEY = rl("phoenix_bloodline");
    private static final ResourceLocation BEAST_KEY = rl("beast_bloodline");
    private static final ResourceLocation AWAKENED_BEAST_KEY = rl("awakened_beast_bloodline");

    // ── Evolution target ResourceLocations ───────────────────────────────────
    // Defined as plain fields so they can be referenced before the DeferredHolder is constructed.
    private static final ResourceLocation BEAST_EVOLVES_INTO = rl("awakened_beast_bloodline");

    // null = final tier, no further evolution
    private static final ResourceLocation AWAKENED_BEAST_EVOLVES_INTO = null;

    // ── Registrations ────────────────────────────────────────────────────────

    /**
     * Mortal Bloodline nothing special just regular
     */
    public static final DeferredHolder<IBloodline, GenericBloodline> MORTAL_BLOODLINE =
            BLOODLINES.register("mortal_bloodline", () ->
                    new GenericBloodline(Component.translatable("ascension.bloodline.mortal_bloodline"))
                            .setShortDescription(Component.translatable("ascension.bloodline.mortal_bloodline.short"))
                            .setDescription(Component.translatable("ascension.bloodline.mortal_bloodline.desc"))
            );


    /*
    Phoenix Bloodline This is just an example for now. What do you guys think?
     */
    public static final DeferredHolder<IBloodline, GenericBloodline> PHOENIX_BLOODLINE =
            BLOODLINES.register("phoenix_bloodline", () ->
                            new GenericBloodline(Component.translatable("ascension.bloodline.phoenix_bloodline"))
                                    .setShortDescription(Component.translatable("ascension.bloodline.phoenix_bloodline.short"))
                                    .setDescription(Component.translatable("ascension.bloodline.phoenix_bloodline.desc"))
                                    .addFlatAttribute(ModAttributes.MAX_QI,          100, PHOENIX_KEY)
                                    .addFlatAttribute(ModAttributes.QI_REGEN_RATE,   0.5, PHOENIX_KEY)
                                    .addFlatAttribute(Attributes.MOVEMENT_SPEED,     0.04, PHOENIX_KEY)
                                    .addFlatAttribute(Attributes.MAX_HEALTH,         20,  PHOENIX_KEY)
                    // .addInherentSkill(ModSkills.PHOENIX_REBIRTH_PASSIVE.getId())
            );


    /**
     * Beast Bloodline — Tier 1. Raw physicality. Evolves into Awakened Beast at 100 purity.
     * Purity grows 0.01 per kill → ~10000 kills to awaken.
     */
    public static final DeferredHolder<IBloodline, PurityBloodline> BEAST_BLOODLINE =
            BLOODLINES.register("beast_bloodline", () ->
                    (PurityBloodline) new PurityBloodline(
                            Component.translatable("ascension.bloodline.beast_bloodline"),
                            BEAST_EVOLVES_INTO
                    )
                            .setShortDescription(Component.translatable("ascension.bloodline.beast_bloodline.short"))
                            .setDescription(Component.translatable("ascension.bloodline.beast_bloodline.desc"))
                            .addFlatAttribute(Attributes.MAX_HEALTH,    20,   BEAST_KEY)
                            .addFlatAttribute(Attributes.ATTACK_DAMAGE,  4,   BEAST_KEY)
                            .addFlatAttribute(Attributes.MOVEMENT_SPEED, 0.02, BEAST_KEY)
                            .addFlatAttribute(Attributes.ARMOR,          2,   BEAST_KEY)
            );

    /**
     * Awakened Beast Bloodline — Tier 2. Evolved form of the Beast bloodline.
     * Stronger modifiers.
     * Currently the final tier (evolvesInto = null).
     */
    public static final DeferredHolder<IBloodline, AwakenedBeastBloodline> AWAKENED_BEAST_BLOODLINE =
            BLOODLINES.register("awakened_beast_bloodline", () ->
                    (AwakenedBeastBloodline) new AwakenedBeastBloodline(
                            Component.translatable("ascension.bloodline.awakened_beast_bloodline"),
                            AWAKENED_BEAST_EVOLVES_INTO
                    )
                            .setShortDescription(Component.translatable("ascension.bloodline.awakened_beast_bloodline.short"))
                            .setDescription(Component.translatable("ascension.bloodline.awakened_beast_bloodline.desc"))
                            .addFlatAttribute(Attributes.MAX_HEALTH,      50,   AWAKENED_BEAST_KEY)
                            .addFlatAttribute(Attributes.ATTACK_DAMAGE,   10,   AWAKENED_BEAST_KEY)
                            .addFlatAttribute(Attributes.MOVEMENT_SPEED,  0.04, AWAKENED_BEAST_KEY)
                            .addFlatAttribute(Attributes.ARMOR,            6,   AWAKENED_BEAST_KEY)
                            .addFlatAttribute(Attributes.ARMOR_TOUGHNESS,  2,   AWAKENED_BEAST_KEY)
            );


    // ── Registration ─────────────────────────────────────────────────────────

    public static void register(IEventBus modEventBus) {
        BLOODLINES.register(modEventBus);
    }

    private static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(AscensionCraft.MOD_ID, path);
    }
}
