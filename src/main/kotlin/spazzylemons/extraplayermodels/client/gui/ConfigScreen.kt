package spazzylemons.extraplayermodels.client.gui

import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.widget.ButtonWidget.PressAction
import net.minecraft.client.resource.language.I18n
import net.minecraft.text.TranslatableText
import net.minecraft.util.Util
import spazzylemons.extraplayermodels.client.ClientData
import java.io.File

/**
 * Represents a screen for configuring the player model.
 *
 * @property parent The parent screen
 */
class ConfigScreen(private val parent: Screen) : Screen(TranslatableText("text.extraplayermodels.config.title")) {
    companion object {
        /**
         * Message that the config screen isn't finished
         */
        private val unfinishedText = TranslatableText("text.extraplayermodels.config.unfinished")
    }

    override fun init() {
        // Add a button to open the config folder
        addButton(ButtonWidget(
            width / 2 - 100, height / 4 - 12, 200, 20, I18n.translate("text.extraplayermodels.config.view"),
            PressAction {
                val dir = minecraft?.runDirectory
                if (dir != null) {
                    Util.getOperatingSystem().open(File(dir, "config/extraplayermodels"))
                }
            }
        ))

        // Add a button to reload the configuration
        addButton(ButtonWidget(
            width / 2 - 100, height / 4 + 12, 200, 20, I18n.translate("text.extraplayermodels.config.reload"),
            PressAction {
                ClientData.reload()
            }
        ))

        // Add a button to leave this screen
        addButton(ButtonWidget(
            width / 2 - 100, height - 48, 200, 20, I18n.translate("gui.done"),
            PressAction {
                minecraft?.openScreen(parent)
            }
        ))
    }

    override fun render(mouseX: Int, mouseY: Int, delta: Float) {
        // Render background
        renderBackground()
        // Draw title
        drawCenteredString(font, title.asFormattedString(), width / 2, 15, 16777215)
        // Draw "this is unfinished" text
        drawCenteredString(font, unfinishedText.asFormattedString(), width / 2, 30, 16777215)
        // Render the player as a preview
        renderPlayerOnScreen(width / 2, height - 108, 60)
        // Super call
        super.render(mouseX, mouseY, delta)
    }
}