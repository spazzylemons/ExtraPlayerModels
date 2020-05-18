package spazzylemons.extraplayermodels.model.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import net.minecraft.client.util.math.Vector3f
import java.lang.reflect.Type

/**
 * Deserializes a [Vector3f].
 */
class VectorDeserializer : JsonDeserializer<Vector3f> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Vector3f {
        // Expect an array for the three numbers
        if (json?.isJsonArray != true) {
            throw JsonParseException("Vector must be an array")
        }
        // Convert
        val arr = json.asJsonArray
        // Three elements, please
        if (arr?.size() != 3) {
            throw JsonParseException("Vector must have 3 elements")
        }
        // Must be numbers
        if (!arr[0].isJsonPrimitive or !arr[0].asJsonPrimitive.isNumber or
            !arr[1].isJsonPrimitive or !arr[1].asJsonPrimitive.isNumber or
            !arr[2].isJsonPrimitive or !arr[2].asJsonPrimitive.isNumber) {
                throw JsonParseException("Element should be a number")
        }
        // Convert and make
        return Vector3f(
            arr[0].asJsonPrimitive.asNumber.toFloat(),
            arr[1].asJsonPrimitive.asNumber.toFloat(),
            arr[2].asJsonPrimitive.asNumber.toFloat()
        )
    }
}