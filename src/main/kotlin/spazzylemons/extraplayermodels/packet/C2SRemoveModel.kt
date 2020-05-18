package spazzylemons.extraplayermodels.packet

import net.fabricmc.fabric.api.network.PacketContext
import net.minecraft.util.Identifier
import net.minecraft.util.PacketByteBuf
import spazzylemons.extraplayermodels.ExtraPlayerModels
import spazzylemons.extraplayermodels.IMinecraftServerMixin

/**
 * Removes a model from a server's model collection.
 */
object C2SRemoveModel : Packet {
    override val id = Identifier(ExtraPlayerModels.PACKET_NAMESPACE, "remove")
    override val action = { ctx: PacketContext, _: PacketByteBuf ->
        val server = ctx.player.server
        val uuid = ctx.player.uuid

        ctx.taskQueue.execute {
            (server as IMinecraftServerMixin).playerModelData.remove(uuid)
        }
    }
}