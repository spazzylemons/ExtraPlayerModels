package spazzylemons.extraplayermodels

import io.github.prospector.modmenu.api.ConfigScreenFactory
import io.github.prospector.modmenu.api.ModMenuApi
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import spazzylemons.extraplayermodels.client.gui.ConfigScreen

@Suppress("unused")
class ExtraPlayerModelsModMenu : ModMenuApi {
    override fun getModId(): String = ExtraPlayerModels.NAMESPACE

    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> = ConfigScreenFactory { parent: Screen ->
        val screen = ConfigScreen(parent)
        MinecraftClient.getInstance().openScreen(screen)
        screen
    }
}