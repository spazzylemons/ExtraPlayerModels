package spazzylemons.extraplayermodels.model

import net.minecraft.util.PacketByteBuf

/**
 * Represents a custom player model with six [CustomModelPart]s:
 * the [head], [torso], [leftArm], [rightArm], [leftLeg], and [rightLeg],
 * as well as a custom [texture].
 */
class CustomPlayerModel(
    val head: CustomModelPart,
    val torso: CustomModelPart,
    val leftArm: CustomModelPart,
    val rightArm: CustomModelPart,
    val leftLeg: CustomModelPart,
    val rightLeg: CustomModelPart,
    val texture: ImageArray
) {
    /**
     * Writes the data in this model to a [PacketByteBuf].
     *
     * @param buf The buffer to write to.
     */
    fun writeToBuf(buf: PacketByteBuf) {
        head.writeToBuf(buf)
        torso.writeToBuf(buf)
        leftArm.writeToBuf(buf)
        rightArm.writeToBuf(buf)
        leftLeg.writeToBuf(buf)
        rightLeg.writeToBuf(buf)
        buf.writeIntArray(texture.ints)
    }
}