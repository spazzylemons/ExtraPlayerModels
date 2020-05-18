package spazzylemons.extraplayermodels.model

import net.minecraft.client.model.ModelPart

/**
 * Rotate a [ModelPart] more directly.
 */
fun ModelPart.rotate(pitch: Float, yaw: Float, roll: Float) {
    this.pitch = pitch
    this.yaw = yaw
    this.roll = roll
}