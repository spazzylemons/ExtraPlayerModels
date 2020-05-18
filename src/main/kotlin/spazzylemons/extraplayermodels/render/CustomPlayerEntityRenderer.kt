package spazzylemons.extraplayermodels.render

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.entity.EntityRenderDispatcher
import net.minecraft.client.render.entity.PlayerEntityRenderer
import spazzylemons.extraplayermodels.model.CustomPlayerModel

/**
 * Used in place of [PlayerEntityRenderer] to allow custom models.
 */
@Environment(EnvType.CLIENT)
class CustomPlayerEntityRenderer(dispatcher: EntityRenderDispatcher, model: CustomPlayerModel) : PlayerEntityRenderer(dispatcher, false) {
    init {
        this.model = CustomPlayerEntityModel(0.0F, model)
    }
}