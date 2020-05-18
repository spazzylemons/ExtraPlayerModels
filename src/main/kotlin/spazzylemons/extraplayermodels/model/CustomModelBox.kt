package spazzylemons.extraplayermodels.model

import net.minecraft.client.model.ModelPart
import net.minecraft.client.util.math.Vector3f
import net.minecraft.util.PacketByteBuf

/**
 * Represents a cuboid part of a [CustomModelPart].
 *
 * @property start The starting coordinate of the box, relative to something??? (figure this out)
 * @property size The size of the box.
 * @property extra The outer padding of the box.
 * @property uv The location of the box's texture.
 */
class CustomModelBox(
    private val start: Vector3f,
    private val size: Vector3f,
    private val extra: Vector3f,
    private val uv: UVCoordinate
) {
    /**
     * Adds this box to a [ModelPart].
     *
     * @param part The part to add this box to.
     */
    fun addToPart(part: ModelPart) {
        part.setTextureOffset(uv.u, uv.v)
        part.addCuboid(start.x, start.y, start.z, size.x, size.y, size.z, extra.x, extra.y, extra.z)
    }

    /**
     * Writes the data in this box to a [PacketByteBuf].
     *
     * @param buf The buffer to write to.
     */
    fun writeToBuf(buf: PacketByteBuf) {
        buf.writeFloat(start.x)
        buf.writeFloat(start.y)
        buf.writeFloat(start.z)
        buf.writeFloat(size.x)
        buf.writeFloat(size.y)
        buf.writeFloat(size.z)
        buf.writeFloat(extra.x)
        buf.writeFloat(extra.y)
        buf.writeFloat(extra.z)
        buf.writeVarInt(uv.u)
        buf.writeVarInt(uv.v)
    }
}