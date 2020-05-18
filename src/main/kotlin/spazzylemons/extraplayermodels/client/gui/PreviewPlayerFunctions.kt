package spazzylemons.extraplayermodels.client.gui

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.Camera
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.client.util.math.Vector3f
import spazzylemons.extraplayermodels.render.PreviewPlayerEntity
import kotlin.math.atan

private val entity = PreviewPlayerEntity()

/**
 * Render the dummy entity on the screen.
 */
fun renderPlayerOnScreen(x: Int, y: Int, size: Int) {
    val mouse = MinecraftClient.getInstance().mouse
    val scaleFactor = MinecraftClient.getInstance().window.scaleFactor
    val f = atan((x - mouse.x / scaleFactor) / 40.0).toFloat()
    val g = atan((y - (size * 1.5) - mouse.y / scaleFactor) / 40.0).toFloat()
    RenderSystem.pushMatrix()
    RenderSystem.translatef(x.toFloat(), y.toFloat(), 1050F)
    RenderSystem.scalef(1F, 1F, -1F)
    val matrixStack = MatrixStack()
    matrixStack.translate(0.0, 0.0, 1000.0)
    matrixStack.scale(size.toFloat(), size.toFloat(), size.toFloat())
    val quaternion = Vector3f.POSITIVE_Z.getDegreesQuaternion(180F)
    val quaternion2 = Vector3f.POSITIVE_X.getDegreesQuaternion(g * 20)
    quaternion.hamiltonProduct(quaternion2)
    matrixStack.multiply(quaternion)
    entity.bodyYaw = 180F + f * 20F
    entity.yaw = 180F + f * 40F
    entity.pitch = -g * 20F
    entity.headYaw = entity.yaw
    entity.prevHeadYaw = entity.yaw
    val entityRenderDispatcher = MinecraftClient.getInstance().entityRenderManager
    entityRenderDispatcher.camera = Camera()
    quaternion2.conjugate()
    entityRenderDispatcher.rotation = quaternion2
    entityRenderDispatcher.setRenderShadows(false)
    val immediate = MinecraftClient.getInstance().bufferBuilders.entityVertexConsumers
    entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0F, 1F, matrixStack, immediate, 15728880)
    immediate.draw()
    entityRenderDispatcher.setRenderShadows(true)
    RenderSystem.popMatrix()
}

/**
 * Tick the dummy entity, for arm animation
 */
fun tickDummyEntity() {
    ++entity.age
}