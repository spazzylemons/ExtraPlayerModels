package spazzylemons.extraplayermodels.model.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import net.minecraft.client.util.math.Vector3f
import spazzylemons.extraplayermodels.model.CustomModelBox
import java.lang.reflect.Type

/**
 * Deserialize a [CustomModelBox].
 */
class CustomModelBoxDeserializer : JsonDeserializer<CustomModelBox> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): CustomModelBox {
        // Make sure it is an object
        if (json?.isJsonObject != true) {
            throw JsonParseException("Model box must be an object")
        }
        // Convert it
        val obj = json.asJsonObject
        // Make it
        return CustomModelBox(
            obj.requiredProperty("start"),
            obj.requiredProperty("size"),
            obj.optionalProperty("extra", Vector3f(0F, 0F, 0F)),
            obj.requiredProperty("uv")
        )
    }
}