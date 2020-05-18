package spazzylemons.extraplayermodels.mixin;

import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.model.ModelPart;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import spazzylemons.extraplayermodels.IModelPartMixin;

/**
 * Interface implementation
 */
@Mixin(ModelPart.class)
public class ModelPartMixin implements IModelPartMixin {
    @Shadow @Final private ObjectList<ModelPart.Cuboid> cuboids;

    // Accessor for a ModelPart's cuboids, useful for making a custom model
    @NotNull @Override
    public ObjectList<ModelPart.Cuboid> getCuboids() {
        return this.cuboids;
    }
}
