package spazzylemons.extraplayermodels.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import spazzylemons.extraplayermodels.client.ClientData;

import java.util.UUID;

/**
 * Mixin to override the usual skin texture logic.
 */
@Environment(EnvType.CLIENT)
@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin {

    // Use a custom skin texture if a custom model exists
    @Inject(at = @At("HEAD"), method = "getSkinTexture", cancellable = true)
    public void getSkinTexture(CallbackInfoReturnable<Identifier> cir) {
        UUID uuid = ((AbstractClientPlayerEntity) (Object) this).getUuid();
        if (MinecraftClient.getInstance().player != null && uuid.equals(MinecraftClient.getInstance().player.getUuid())) {
            // Only use custom texture if there is a model to go with it
            if (ClientData.INSTANCE.getCurrentModel() != null) {
                Identifier identifier = ClientData.INSTANCE.getLocalTextureIdentifier();
                if (identifier != null) {
                    cir.setReturnValue(identifier);
                }
            }
        } else if (ClientData.INSTANCE.getPlayerModels().containsKey(uuid)) {
            Identifier identifier = ClientData.INSTANCE.getTextureIdentifiers().get(uuid);
            if (identifier != null) {
                cir.setReturnValue(identifier);
            }
        }
    }
}
