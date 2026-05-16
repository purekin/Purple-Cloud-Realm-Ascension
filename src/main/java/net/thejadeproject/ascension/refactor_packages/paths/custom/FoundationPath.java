package net.thejadeproject.ascension.refactor_packages.paths.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.thejadeproject.ascension.refactor_packages.entity_data.IEntityData;
import net.thejadeproject.ascension.refactor_packages.paths.data.IPathData;
import net.thejadeproject.ascension.refactor_packages.paths.data.SimplePathData;
import net.thejadeproject.ascension.refactor_packages.paths.data.foundation.FoundationPathData;
import net.thejadeproject.ascension.refactor_packages.paths.data.foundation.stability.IStabilityHandler;
import net.thejadeproject.ascension.refactor_packages.registries.AscensionRegistries;

public class FoundationPath extends GenericPath{
    private IStabilityHandler stabilityHandler;

    public FoundationPath(Component title,IStabilityHandler stabilityHandler) {
        super(title);
        this.stabilityHandler = stabilityHandler;
    }


    public IStabilityHandler getStabilityHandler(int realm){
        return stabilityHandler;
    }

    public void onFoundationBreakthrough(IEntityData entityData, int majorRealm, int foundationStage){

    }
    public void onFoundationDown(IEntityData entityData,int majorRealm,int newStage){

    }

    @Override
    public IPathData freshPathData(IEntityData heldEntity) {
        return new FoundationPathData(AscensionRegistries.Paths.PATHS_REGISTRY.getKey(this));
    }
    @Override
    public IPathData fromCompound(CompoundTag tag, IEntityData heldEntity) {
        //todo handle cultivation data simulations
        IPathData pathData = freshPathData(heldEntity);
        heldEntity.addPathData(AscensionRegistries.Paths.PATHS_REGISTRY.getKey(this),pathData);
        pathData = heldEntity.getPathData(AscensionRegistries.Paths.PATHS_REGISTRY.getKey(this)); //makes sure we are modifying the saved instance
        pathData.load(tag,heldEntity);
        return pathData;
    }
    @Override
    public IPathData fromNetwork(RegistryFriendlyByteBuf buf) {
        IPathData pathData = new FoundationPathData(AscensionRegistries.Paths.PATHS_REGISTRY.getKey(this));
        pathData.load(buf);
        return pathData;
    }
    public double foundationBuildingSpeed(){
        return 1;
    }
}
