package spazzylemons.extraplayermodels.render

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.model.ModelPart
import net.minecraft.client.render.entity.model.PlayerEntityModel
import net.minecraft.entity.LivingEntity
import spazzylemons.extraplayermodels.IModelPartMixin
import spazzylemons.extraplayermodels.model.CustomPlayerModel

/**
 * Represents an entity model. May replace the [PlayerEntityModel] parts if it is custom
 */
@Environment(EnvType.CLIENT)
class CustomPlayerEntityModel<T : LivingEntity>(scale: Float, model: CustomPlayerModel) : PlayerEntityModel<T>(scale, false) {
    init {
        // Head and helmet
        head = ModelPart(this)
        (helmet as IModelPartMixin).getCuboids().clear()
        helmet.addChild(model.head.asModelPart(this))
        // Torso and jacket
        torso = ModelPart(this)
        (jacket as IModelPartMixin).getCuboids().clear()
        jacket.addChild(model.torso.asModelPart(this))
        // Arms and sleeves
        leftArm = ModelPart(this)
        (leftSleeve as IModelPartMixin).getCuboids().clear()
        leftSleeve.addChild(model.leftArm.asModelPart(this))
        rightArm = ModelPart(this)
        (rightSleeve as IModelPartMixin).getCuboids().clear()
        rightSleeve.addChild(model.rightArm.asModelPart(this))
        // Legs and pants
        leftLeg = ModelPart(this)
        (leftPantLeg as IModelPartMixin).getCuboids().clear()
        leftPantLeg.addChild(model.leftLeg.asModelPart(this))
        rightLeg = ModelPart(this)
        (rightPantLeg as IModelPartMixin).getCuboids().clear()
        rightPantLeg.addChild(model.rightLeg.asModelPart(this))
    }
}