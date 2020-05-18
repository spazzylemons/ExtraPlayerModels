package spazzylemons.extraplayermodels.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spazzylemons.extraplayermodels.client.ClientData;
import spazzylemons.extraplayermodels.client.gui.PreviewPlayerFunctionsKt;

/**
 * Several mixins.
 */
@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow public ClientWorld world;

    @Shadow public abstract ClientPlayNetworkHandler getNetworkHandler();

    public boolean playerModelSentOnJoin;
    // Notify the server when we join a world
    @Inject(at = @At("TAIL"), method = "joinWorld")
    public void joinWorld(ClientWorld clientWorld, CallbackInfo ci) {
        playerModelSentOnJoin = false;
    }

    // 1. Try to send the first model update packet when connected
    // 2. Every tick, tick the dummy entity
    @Inject(at = @At("TAIL"), method = "tick")
    public void tick(CallbackInfo ci) {
        if (world != null && !playerModelSentOnJoin && getNetworkHandler() != null) {
            ClientData.INSTANCE.notifyServer();
            playerModelSentOnJoin = true;
        }
        PreviewPlayerFunctionsKt.tickDummyEntity();
    }
}
