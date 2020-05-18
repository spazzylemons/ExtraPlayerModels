package spazzylemons.extraplayermodels.client

import com.google.gson.JsonParseException
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.texture.NativeImageBackedTexture
import net.minecraft.util.Identifier
import spazzylemons.extraplayermodels.ExtraPlayerModels
import spazzylemons.extraplayermodels.IModelPartMixin
import spazzylemons.extraplayermodels.model.CustomPlayerModel
import spazzylemons.extraplayermodels.model.ModelLoader
import spazzylemons.extraplayermodels.packet.C2SInsertModel
import spazzylemons.extraplayermodels.packet.C2SRemoveModel
import spazzylemons.extraplayermodels.packet.newPacketByteBuf
import spazzylemons.extraplayermodels.render.CustomPlayerEntityRenderer
import java.util.*

/**
 * Contains a collection of player models as well as the current player model.
 * Has methods for adding, removing, and updating models.
 */
@Environment(EnvType.CLIENT)
object ClientData {
    /**
     * The player models known by the client, stored as player model renderers for efficiency.
     */
    val playerModels = mutableMapOf<UUID, CustomPlayerEntityRenderer>()

    /**
     * The current player model being used by the client.
     */
    var currentModel: CustomPlayerModel? = null

    /**
     * The current renderer being used by the client.
     */
    var currentRenderer: CustomPlayerEntityRenderer? = null

    /**
     * A map of user ids to texture identifiers
     */
    val textureIdentifiers = mutableMapOf<UUID, Identifier>()

    /**
     * The current identifier for the local texture.
     */
    var localTextureIdentifier: Identifier? = null

    /**
     * Add or insert a model.
     *
     * @param uuid The UUID of the owner of the model.
     * @param model The model.
     */
    fun insert(uuid: UUID, model: CustomPlayerModel) {
        textureIdentifiers[uuid] = MinecraftClient.getInstance().textureManager.registerDynamicTexture(
            "epmcustomtexture.$uuid",
            NativeImageBackedTexture(model.texture.toNativeImage())
        )
        playerModels[uuid] = CustomPlayerEntityRenderer(MinecraftClient.getInstance().entityRenderManager, model)
    }

    /**
     * Remove a model.
     *
     * @param uuid The UUID of the owner of the model.
     */
    fun remove(uuid: UUID) {
        textureIdentifiers.remove(uuid)
        playerModels.remove(uuid)
    }

    /**
     * Reload the player model from the config file.
     *
     * @return If the model was successfully loaded.
     */
    fun reload(): Boolean {
        try {
            // Try to load from the config file
            val model = ModelLoader.load().model
            if (model != null) {
                localTextureIdentifier = MinecraftClient.getInstance().textureManager.registerDynamicTexture(
                    "epmcustomtexture.local",
                    NativeImageBackedTexture(model.texture.toNativeImage())
                )
                currentRenderer = CustomPlayerEntityRenderer(MinecraftClient.getInstance().entityRenderManager, model)
            } else {
                currentRenderer = null
            }
            currentModel = model
        } catch (e: JsonParseException) {
            // Notify the user why the model failed
            ExtraPlayerModels.logError("text.extraplayermodels.loadmodel.explanation", e.message)
            e.printStackTrace()

            // Fail
            return false
        }

        // Success! Tell the server (if we're connected)
        if (MinecraftClient.getInstance().networkHandler != null) {
            notifyServer()
        }
        return true
    }

    /**
     * Notify the server that the client's model has changed.
     */
    fun notifyServer() {
        when (val model = currentModel) {
            null -> {
                // When model is null, remove the model in the server
                C2SRemoveModel.sendToServer()
            }
            else -> {
                // When model is not null, add the model in the server
                val buf = newPacketByteBuf()
                model.writeToBuf(buf)
                C2SInsertModel.sendToServer(buf)
            }
        }
    }
}