package net.thejadeproject.ascension.refactor_packages.skills.custom.cultivation.elemental;

import net.lucent.easygui.gui.textures.ITextureData;
import net.lucent.easygui.gui.textures.TextureData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.thejadeproject.ascension.AscensionCraft;
import net.thejadeproject.ascension.refactor_packages.entity_data.IEntityData;
import net.thejadeproject.ascension.refactor_packages.paths.ModPaths;
import net.thejadeproject.ascension.refactor_packages.techniques.custom.essence.ElementalEssenceTechnique;
import net.thejadeproject.ascension.refactor_packages.techniques.custom.essence.WindEssenceTechnique;

public class WindEssenceCultivationSkill extends ElementalEssenceCultivationSkill {

    @Override
    protected ResourceLocation getElementPath() {
        return ModPaths.WIND.getId();
    }

    @Override
    protected double getEnvironmentMultiplier(Entity caster) {
        int seaLevel = caster.level().getSeaLevel();
        int heightAboveSea = caster.blockPosition().getY() - seaLevel;
        boolean openSky = caster.level().canSeeSky(caster.blockPosition());

        if (!openSky) {
            return 0.75D;
        }

        if (heightAboveSea >= 140) {
            return 6.4D;
        }

        if (heightAboveSea >= 80) {
            return 5.4D;
        }

        if (heightAboveSea >= 32) {
            return 4.6D;
        }

        return 1.00D;
    }

    @Override
    protected Class<? extends ElementalEssenceTechnique> getTechniqueClass() {
        return WindEssenceTechnique.class;
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public ITextureData getIcon(IEntityData entityData) {
        return new TextureData(
                ResourceLocation.fromNamespaceAndPath(
                        AscensionCraft.MOD_ID,
                        "textures/spells/icon/wind_essence_cultivation_skill.png"
                ),
                16,
                16
        );
    }

}