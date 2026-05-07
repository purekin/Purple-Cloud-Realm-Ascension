package net.thejadeproject.ascension.refactor_packages.bloodlines.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.thejadeproject.ascension.refactor_packages.entity_data.IEntityData;

/**
 * The awakened or evolved form of the Beast bloodline.
 * its Still a PurityBloodline so it can continue evolving further if desired —
 * just pass null for evolvesInto if this is the final tier.
 *
 * Inherits all GenericBloodline attribute modifier logic.
 * Override getPurityPerKill to slow purity gain at this tier (harder to advance further).
 */

public class AwakenedBeastBloodline extends PurityBloodline {


    /**
     * Kills are worth less purity at this tier — evolution is harder to achieve.
     * Set evolvesInto to a third-tier bloodline ResourceLocation, or null if this is the cap.
     */
    public AwakenedBeastBloodline(Component title, ResourceLocation evolvesInto) {
        super(title, evolvesInto);
    }

    @Override
    protected double getPurityPerKill(ServerPlayer player, IEntityData entityData, LivingEntity killed) {
        // Half the gain rate — takes twice as many kills to evolve again
        return DEFAULT_PURITY_PER_KILL * 0.5;
    }

    @Override
    protected void onPurityMilestone(ServerPlayer player, IEntityData entityData, PurityBloodlineData data) {
        super.onPurityMilestone(player, entityData, data);
        // Add tier-specific effects here — sounds, particles, auras, etc.
    }
}
