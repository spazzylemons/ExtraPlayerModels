package spazzylemons.extraplayermodels.model

import net.minecraft.client.util.math.Vector3f
import net.minecraft.util.PacketByteBuf

/**
 * Contains methods for building models from [PacketByteBuf]s.
 */
object ModelBuilder {
    /**
     * Create a [CustomModelBox] from a [PacketByteBuf].
     *
     * @param buf The PacketByteBuf to read from.
     */
    private fun boxFromBuf(buf: PacketByteBuf): CustomModelBox = CustomModelBox(
        Vector3f(buf.readFloat(), buf.readFloat(), buf.readFloat()),
        Vector3f(buf.readFloat(), buf.readFloat(), buf.readFloat()),
        Vector3f(buf.readFloat(), buf.readFloat(), buf.readFloat()),
        UVCoordinate(buf.readVarInt(), buf.readVarInt())
    )

    /**
     * Create a [CustomModelPart] from a [PacketByteBuf].
     *
     * @param buf The PacketByteBuf to read from.
     */
    private fun partFromBuf(buf: PacketByteBuf): CustomModelPart = CustomModelPart(
        Vector3f(buf.readFloat(), buf.readFloat(), buf.readFloat()),
        Vector3f(buf.readFloat(), buf.readFloat(), buf.readFloat()),
        (0 until buf.readVarInt()).map {
            boxFromBuf(buf)
        },
        (0 until buf.readVarInt()).map {
            partFromBuf(buf)
        }
    )

    /**
     * Create a [CustomPlayerModel] from a [PacketByteBuf].
     *
     * @param buf The PacketByteBuf to read from.
     */
    fun modelFromBuf(buf: PacketByteBuf): CustomPlayerModel {
        return CustomPlayerModel(
            partFromBuf(buf),
            partFromBuf(buf),
            partFromBuf(buf),
            partFromBuf(buf),
            partFromBuf(buf),
            partFromBuf(buf),
            ImageArray(buf.readIntArray())
        )
    }
}