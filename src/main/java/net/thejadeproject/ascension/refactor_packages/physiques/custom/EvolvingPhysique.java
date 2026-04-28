package net.thejadeproject.ascension.refactor_packages.physiques.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.thejadeproject.ascension.refactor_packages.entity_data.IEntityData;
import net.thejadeproject.ascension.refactor_packages.physiques.IPhysique;
import net.thejadeproject.ascension.refactor_packages.physiques.IPhysiqueData;
import net.thejadeproject.ascension.refactor_packages.registries.AscensionRegistries;

public class EvolvingPhysique extends GenericPhysique {

    private final ResourceLocation evolvesInto;

    public EvolvingPhysique(Component title, ResourceLocation evolvesInto) {
        super(title);
        this.evolvesInto = evolvesInto;
    }

    public ResourceLocation getEvolvesInto() {
        return evolvesInto;
    }


    public boolean tryEvolve(ServerPlayer player, IEntityData entityData) {
        if (entityData == null) return false;
        if (entityData.getPhysique() != this) return false;

        ResourceLocation currentForm = entityData.getPhysiqueForm();
        if (currentForm == null) return false;

        if (!canEvolve(player, entityData)) return false;

        IPhysique newPhysique = AscensionRegistries.Physiques.PHSIQUES_REGISTRY.get(evolvesInto);
        if (newPhysique == null) return false;

        IPhysiqueData newData = newPhysique.freshPhysiqueData(entityData);

        boolean changed = entityData.setPhysique(evolvesInto, newData, currentForm);

        if (changed) {
            player.sendSystemMessage(
                    Component.translatable("ascension.message.physique.evolved")
            );
        }

        return changed;
    }

    protected boolean canEvolve(ServerPlayer player, IEntityData entityData) {
        return true;
    }
}