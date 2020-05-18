package spazzylemons.extraplayermodels.packet

import net.fabricmc.fabric.api.network.PacketContext
import net.minecraft.util.Identifier
import net.minecraft.util.PacketByteBuf
import spazzylemons.extraplayermodels.ExtraPlayerModels
import spazzylemons.extraplayermodels.client.ClientData

/**
 * Removes a model from a client's model collection.
 */
object S2CRemoveModel : Packet {
    override val id = Identifier(ExtraPlayerModels.PACKET_NAMESPACE, "remove")
    override val action = { ctx: PacketContext, data: PacketByteBuf ->
        val uuid = data.readUuid()

        ctx.taskQueue.execute {
            ClientData.remove(uuid)
        }
    }
}