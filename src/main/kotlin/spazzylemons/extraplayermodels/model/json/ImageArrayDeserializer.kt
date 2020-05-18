package spazzylemons.extraplayermodels.model.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import net.minecraft.client.MinecraftClient
import spazzylemons.extraplayermodels.model.ImageArray
import java.io.File
import java.lang.reflect.Type
import javax.imageio.ImageIO

/**
 * Deserialize an [ImageArray].
 */
class ImageArrayDeserializer : JsonDeserializer<ImageArray> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ImageArray {
        // Must be a string. ImageArray represents a texture, so we want a file name
        if (json?.isJsonPrimitive != true || !json.asJsonPrimitive.isString) {
            throw JsonParseException("Texture name must be a string")
        }
        // Get that string
        val filename = json.asJsonPrimitive.asString
        // Find the file
        val file = File(File(MinecraftClient.getInstance().runDirectory, "config/extraplayermodels/textures"), filename)
        // The file must exist, obviously
        if (!file.exists()) {
            throw JsonParseException("Skin file not found")
        }
        // Read it
        val image = ImageIO.read(file)
        // Enforce image size standard
        if (image.width != 64 || image.height != 64) {
            throw JsonParseException("Skin is not 64x64")
        }
        // Make an IntArray for the pixels
        val intArray = IntArray(4096)
        // Put each pixel in
        var i = 0
        for (y in 0..63) {
            for (x in 0..63) {
                val pixel = image.getRGB(x, y)
                // BGR to RGB conversion, or the other way around, I forgot
                intArray[i++] = (pixel.inv() or 0x00FF00FF).inv() + ((pixel and 0xFF0000) shr 16) + ((pixel and 0xFF) shl 16)
            }
        }
        // Wrap the IntArray in an ImageArray
        return ImageArray(intArray)
    }
}