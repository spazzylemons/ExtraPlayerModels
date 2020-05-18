package spazzylemons.extraplayermodels

import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry
import net.minecraft.client.util.InputUtil
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.lwjgl.glfw.GLFW
import spazzylemons.extraplayermodels.packet.C2SInsertModel
import spazzylemons.extraplayermodels.packet.C2SRemoveModel
import spazzylemons.extraplayermodels.packet.S2CInsertModel
import spazzylemons.extraplayermodels.packet.S2CRemoveModel

/**
 * Contains useful variables and methods for the initialization and operation of the mod.
 */
object ExtraPlayerModels {
    /**
     * The mod's namespace.
     */
    const val NAMESPACE = "extraplayermodels"

    /**
     * In older versions of Minecraft, the length of a custom packet's name is limited. To enable the possibility of
     * creating server-side plugins that can run on old Minecraft versions such as 1.8, a shorter namespace is used
     * for packet channels.
     */
    const val PACKET_NAMESPACE = "xtrapmodels"

    /**
     * The mod logger.
     */
    private val LOGGER: Logger = LogManager.getLogger()

    /**
     * Log information.
     * @param info The translation key.
     * @param args The arguments
     */
    fun logInfo(info: String, vararg args: Any?) {
        LOGGER.info(TranslatableText(info, args).string)
    }

    /**
     * Log an error.
     * @param info The translation key.
     * @param args The arguments
     */
    fun logError(info: String, vararg args: Any?) {
        LOGGER.error(TranslatableText(info, args).string)
    }

    fun init() {
        // Packets
        C2SInsertModel.register(ServerSidePacketRegistry.INSTANCE)
        C2SRemoveModel.register(ServerSidePacketRegistry.INSTANCE)
    }

    fun clientInit() {
        // Packets
        S2CInsertModel.register(ClientSidePacketRegistry.INSTANCE)
        S2CRemoveModel.register(ClientSidePacketRegistry.INSTANCE)

        // Keybinding
        CameraKeybind = FabricKeyBinding.Builder.create(
            Identifier(NAMESPACE, "camerakey"),
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_Y,
            "Extra Player Models"
        ).build()

        KeyBindingRegistry.INSTANCE.addCategory("Extra Player Models")
        KeyBindingRegistry.INSTANCE.register(CameraKeybind)
    }
}

/*
ClientTickCallback.EVENT.register(e ->
{
    if(keyBinding.isPressed()) System.out.println("was pressed!");
});
 */