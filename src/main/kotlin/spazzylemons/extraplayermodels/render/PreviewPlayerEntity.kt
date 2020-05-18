package spazzylemons.extraplayermodels.render

import com.mojang.authlib.GameProfile
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.network.PlayerListEntry
import net.minecraft.client.recipe.book.ClientRecipeBook
import net.minecraft.client.render.entity.PlayerModelPart
import net.minecraft.client.world.ClientWorld
import net.minecraft.network.ClientConnection
import net.minecraft.network.NetworkSide
import net.minecraft.recipe.RecipeManager
import net.minecraft.stat.StatHandler
import net.minecraft.util.Identifier
import net.minecraft.util.profiler.DisableableProfiler
import net.minecraft.world.GameMode
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.level.LevelGeneratorType
import net.minecraft.world.level.LevelInfo
import spazzylemons.extraplayermodels.client.ClientData
import java.util.*
import java.util.function.IntSupplier

/**
 * A player entity used only for previewing the current model.
 */
@Environment(EnvType.CLIENT)
class PreviewPlayerEntity : ClientPlayerEntity(
    MinecraftClient.getInstance(),
    ClientWorld(
        ClientPlayNetworkHandler(
            MinecraftClient.getInstance(),
            null,
            ClientConnection(
                NetworkSide.CLIENTBOUND
            ),
            GameProfile(
                UUID(0L, 0L),
                MinecraftClient.getInstance().session.username
            )
        ),
        LevelInfo(0, GameMode.CREATIVE, false, false, LevelGeneratorType.FLAT),
        DimensionType.OVERWORLD,
        1,
        DisableableProfiler(IntSupplier { 20 }),
        MinecraftClient.getInstance().worldRenderer
    ),
    ClientPlayNetworkHandler(
        MinecraftClient.getInstance(),
        null,
        ClientConnection(
            NetworkSide.CLIENTBOUND
        ),
        GameProfile(
            UUID(0L, 0L),
            MinecraftClient.getInstance().session.username
        )
    ),
    StatHandler(),
    ClientRecipeBook(RecipeManager())
) {
    // Get the skin texture from the local data
    override fun getSkinTexture(): Identifier = if (ClientData.currentModel != null) {
        ClientData.localTextureIdentifier!!
    } else {
        super.getSkinTexture()
    }

    // Override to avoid NullPointerExceptions in the super method
    override fun getPlayerListEntry(): PlayerListEntry? = null

    // Ditto
    override fun isSpectator(): Boolean = false

    // Ditto
    override fun isCreative(): Boolean = true

    // Use local data
    override fun isPartVisible(modelPart: PlayerModelPart?): Boolean = MinecraftClient.getInstance().options.enabledPlayerModelParts.contains(modelPart)
}