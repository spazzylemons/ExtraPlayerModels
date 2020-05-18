package spazzylemons.extraplayermodels.mixin;

import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import spazzylemons.extraplayermodels.IMinecraftServerMixin;
import spazzylemons.extraplayermodels.server.ServerData;

/**
 * Interface implementation.
 */
@Mixin(MinecraftServer.class)
public class MinecraftServerMixin implements IMinecraftServerMixin {
    // Add server data to each instance of MinecraftServer
    public final ServerData playerModelData = new ServerData((MinecraftServer) (Object) this);

    // Accessor method because you can't type cast to a mixin, so an interface must be used instead.
    @NotNull @Override
    public ServerData getPlayerModelData() {
        return playerModelData;
    }
}
