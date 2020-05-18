package spazzylemons.extraplayermodels.server

import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.server.PlayerStream
import net.minecraft.server.MinecraftServer
import net.minecraft.util.PacketByteBuf
import spazzylemons.extraplayermodels.model.CustomPlayerModel
import spazzylemons.extraplayermodels.packet.S2CInsertModel
import spazzylemons.extraplayermodels.packet.S2CRemoveModel
import java.util.*

/**
 * Used to store data about the custom player models in a [MinecraftServer].
 *
 * @property server The attached server.
 */
class ServerData(private val server: MinecraftServer) {
    /**
     * The list of players.
     */
    val modelList = mutableMapOf<UUID, CustomPlayerModel>()

    /**
     * Insert or update a player model.
     *
     * @param playerId The player's UUID.
     * @param model The model.
     */
    fun insert(playerId: UUID, model: CustomPlayerModel) {
        modelList[playerId] = model
        val buf = PacketByteBuf(Unpooled.buffer())
        buf.writeUuid(playerId)
        model.writeToBuf(buf)
        for (player in PlayerStream.all(server)) {
            S2CInsertModel.sendToPlayer(player, buf)
        }
    }

    /**
     * Remove a player model.
     *
     * @param playerId The player's UUID.
     */
    fun remove(playerId: UUID) {
        modelList.remove(playerId)
        val buf = PacketByteBuf(Unpooled.buffer())
        buf.writeUuid(playerId)
        for (player in PlayerStream.all(server)) {
            S2CRemoveModel.sendToPlayer(player, buf)
        }
    }
}