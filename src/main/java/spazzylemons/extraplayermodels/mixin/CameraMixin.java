package spazzylemons.extraplayermodels.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spazzylemons.extraplayermodels.CameraKeybindKt;

/**
 * Mixin to add feature for looking at player model in third-person.
 */
@Environment(EnvType.CLIENT)
@Mixin(Camera.class)
public abstract class CameraMixin {
    @Shadow private float cameraY;

    @Shadow private float lastCameraY;

    @Shadow protected abstract void setRotation(float yaw, float pitch);

    @Shadow protected abstract void setPos(double x, double y, double z);

    @Shadow protected abstract void moveBy(double x, double y, double z);

    @Shadow protected abstract double clipToSpace(double desiredCameraDistance);

    // WIP camera method for looking around, useful for looking at model
    @Inject(at = @At("TAIL"), method = "update")
    public void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (thirdPerson) {
            if (CameraKeybindKt.getCameraKeybind().isPressed() && client.player != null) {
                Mouse mouse = client.mouse;
                this.setPos(MathHelper.lerp(tickDelta, focusedEntity.prevX, focusedEntity.getX()), MathHelper.lerp(tickDelta, focusedEntity.prevY, focusedEntity.getY()) + MathHelper.lerp(tickDelta, this.lastCameraY, this.cameraY), MathHelper.lerp(tickDelta, focusedEntity.prevZ, focusedEntity.getZ()));
                this.setRotation((float) mouse.getX(), 180 + (float) mouse.getY());
                this.moveBy(-this.clipToSpace(4.0D), 0.0D, 0.0D);
            }
        }
    }
}