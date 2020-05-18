package spazzylemons.extraplayermodels.model

import net.minecraft.client.model.Model
import net.minecraft.client.model.ModelPart
import net.minecraft.client.util.math.Vector3f
import net.minecraft.util.PacketByteBuf
import net.minecraft.util.math.MathHelper

/**
 * Represents a part of a custom model.
 *
 * @property pivot Where the part rotates.
 * @property rotation The part's rotation.
 * @property boxes A list of [CustomModelBox]es for this part.
 * @property children A list of [CustomModelPart]s that inherit some properties of this part.
 */
class CustomModelPart(
    private val pivot: Vector3f,
    private val rotation: Vector3f,
    private val boxes: List<CustomModelBox>,
    private val children: List<CustomModelPart>
) {
    /**
     * Convert this part to a [ModelPart].
     *
     * @param model The [Model] to connect this part to.
     * @return The [ModelPart] generated from this part.
     */
    fun asModelPart(model: Model): ModelPart {
        // Make a ModelPart.
        val part = ModelPart(model)
        // Set its pivot.
        part.setPivot(pivot.x, pivot.y, pivot.z)
        // Add the boxes.
        for (box in boxes) {
            box.addToPart(part)
        }
        // Use 0.017453292 to convert degrees to radians.
        part.rotate(
            MathHelper.wrapDegrees(rotation.x) * 0.017453292F,
            MathHelper.wrapDegrees(rotation.y) * 0.017453292F,
            MathHelper.wrapDegrees(rotation.z) * 0.01745329F
        )
        // Add children.
        for (child in children) {
            part.addChild(child.asModelPart(model))
        }
        // Return.
        return part
    }

    /**
     * Writes the data in this part to a [PacketByteBuf].
     *
     * @param buf The buffer to write to.
     */
    fun writeToBuf(buf: PacketByteBuf) {
        buf.writeFloat(pivot.x)
        buf.writeFloat(pivot.y)
        buf.writeFloat(pivot.z)
        buf.writeFloat(rotation.x)
        buf.writeFloat(rotation.y)
        buf.writeFloat(rotation.z)
        buf.writeVarInt(boxes.size)
        for (box in boxes) {
            box.writeToBuf(buf)
        }
        buf.writeVarInt(children.size)
        for (child in children) {
            child.writeToBuf(buf)
        }
    }
}