package spazzylemons.extraplayermodels.mixin;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spazzylemons.extraplayermodels.IMinecraftServerMixin;
import spazzylemons.extraplayermodels.packet.S2CInsertModel;

/**
 * Mixins for managing the server's player models
 */
@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Shadow @Final private MinecraftServer server;

    // When a player connects, send them all of the player models
    @Inject(at = @At("TAIL"), method = "onPlayerConnect")
    public void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        ((IMinecraftServerMixin) server).getPlayerModelData().getModelList().forEach((key, value) -> {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeUuid(key);
            value.writeToBuf(buf);
            ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, S2CInsertModel.INSTANCE.getId(), buf);
        });
    }

    // When a player leaves, remove their player model, in turn notifying everyone
    @Inject(at = @At("TAIL"), method = "remove")
    public void remove(ServerPlayerEntity player, CallbackInfo ci) {
        ((IMinecraftServerMixin) server).getPlayerModelData().remove(player.getUuid());
    }
}