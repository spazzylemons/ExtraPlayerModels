package spazzylemons.extraplayermodels.packet

import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry
import net.fabricmc.fabric.api.network.PacketContext
import net.fabricmc.fabric.api.network.PacketRegistry
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Identifier
import net.minecraft.util.PacketByteBuf

/**
 * Represents a packet.
 */
interface Packet {
    /**
     * The identifier of the packet used to determine what the packet contains.
     */
    val id: Identifier

    /**
     * The action to be performed when the packet is received.
     */
    val action: (PacketContext, PacketByteBuf) -> Unit

    /**
     * Send this packet to a player.
     * @param player The player to send this packet to.
     * @param buffer The buffer with the packet's data.
     */
    fun sendToPlayer(player: PlayerEntity, buffer: PacketByteBuf = PacketByteBuf(Unpooled.buffer()))
            = ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, id, buffer)

    /**
     * Send this packet to a server.
     * @param buffer The buffer with the packet's data.
     */
    fun sendToServer(buffer: PacketByteBuf = PacketByteBuf(Unpooled.buffer()))
            = ClientSidePacketRegistry.INSTANCE.sendToServer(id, buffer)

    /**
     * Register this packet in a registry.
     * @param registry The registry to register this packet to.
     */
    fun register(registry: PacketRegistry)
            = registry.register(id, action)
}