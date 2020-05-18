package spazzylemons.extraplayermodels

import it.unimi.dsi.fastutil.objects.ObjectList
import net.minecraft.client.model.ModelPart
import spazzylemons.extraplayermodels.server.ServerData

interface IMinecraftServerMixin {
    val playerModelData: ServerData
}

interface IModelPartMixin {
    fun getCuboids(): ObjectList<ModelPart.Cuboid>
}