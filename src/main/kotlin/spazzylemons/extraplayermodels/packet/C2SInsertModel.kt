package spazzylemons.extraplayermodels.packet

import net.fabricmc.fabric.api.network.PacketContext
import net.minecraft.util.Identifier
import net.minecraft.util.PacketByteBuf
import spazzylemons.extraplayermodels.ExtraPlayerModels
import spazzylemons.extraplayermodels.IMinecraftServerMixin
import spazzylemons.extraplayermodels.model.ModelBuilder

/**
 * Inserts a model into a server's model collection.
 */
object C2SInsertModel : Packet {
    override val id = Identifier(ExtraPlayerModels.PACKET_NAMESPACE, "insert")
    override val action = { ctx: PacketContext, data: PacketByteBuf ->
        val model = ModelBuilder.modelFromBuf(data)
        val server = ctx.player.server
        val uuid = ctx.player.uuid

        ctx.taskQueue.execute {
            (server as IMinecraftServerMixin).playerModelData.insert(uuid, model)
        }
    }
}