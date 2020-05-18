package spazzylemons.extraplayermodels.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import spazzylemons.extraplayermodels.CameraKeybindKt;

/**
 * Mixin for custom camera keybind.
 */
@Environment(EnvType.CLIENT)
@Mixin(Mouse.class)
public class MouseMixin {
    @Shadow @Final private MinecraftClient client;

    // More mixins for camera look keybind
    @Inject(at = @At("TAIL"), method = "updateMouse", locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void updateMouse(CallbackInfo ci, double e, double l, double m, int n) {
        if (this.client.gameRenderer.getCamera().isThirdPerson() && CameraKeybindKt.getCameraKeybind().isPressed() && this.client.player != null) {
            this.client.player.changeLookDirection(-l, -(m * n));
        }
    }
}