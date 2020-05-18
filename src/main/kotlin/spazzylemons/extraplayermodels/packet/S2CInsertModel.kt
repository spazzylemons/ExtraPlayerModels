package spazzylemons.extraplayermodels.packet

import net.fabricmc.fabric.api.network.PacketContext
import net.minecraft.util.Identifier
import net.minecraft.util.PacketByteBuf
import spazzylemons.extraplayermodels.ExtraPlayerModels
import spazzylemons.extraplayermodels.client.ClientData
import spazzylemons.extraplayermodels.model.ModelBuilder

/**
 * Inserts a model into a client's model collection.
 */
object S2CInsertModel : Packet {
    override val id = Identifier(ExtraPlayerModels.PACKET_NAMESPACE, "insert")
    override val action = { ctx: PacketContext, data: PacketByteBuf ->
        val uuid = data.readUuid()
        val model = ModelBuilder.modelFromBuf(data)

        ctx.taskQueue.execute {
            ClientData.insert(uuid, model)
        }
    }
}