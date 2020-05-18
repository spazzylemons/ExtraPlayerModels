package spazzylemons.extraplayermodels.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import spazzylemons.extraplayermodels.client.ClientData;
import spazzylemons.extraplayermodels.render.CustomPlayerEntityRenderer;
import spazzylemons.extraplayermodels.render.PreviewPlayerEntity;

/**
 * Mixin to override the usual renderer logic.
 */
@Environment(EnvType.CLIENT)
@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    // Use a custom renderer if we have one, or if the entity is the dummy entity, use the local renderer.
    @Inject(at = @At("HEAD"), method = "getRenderer", cancellable = true)
    public <T extends Entity> void getRenderer(T entity, CallbackInfoReturnable<EntityRenderer<? super T>> cir) {
        if (!(entity instanceof PlayerEntity)) return;
        PlayerEntity mcPlayer = MinecraftClient.getInstance().player;
        CustomPlayerEntityRenderer renderer;
        if (entity instanceof PreviewPlayerEntity || mcPlayer != null && mcPlayer.getUuid().equals(entity.getUuid())) {
            renderer = ClientData.INSTANCE.getCurrentRenderer();
        } else {
            renderer = ClientData.INSTANCE.getPlayerModels().get(entity.getUuid());
        }

        if (renderer != null) {
            cir.setReturnValue((EntityRenderer<? super T>) renderer);
        }
    }
}