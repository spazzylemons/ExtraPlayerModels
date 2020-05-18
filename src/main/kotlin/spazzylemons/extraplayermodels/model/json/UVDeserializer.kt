package spazzylemons.extraplayermodels.model.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import spazzylemons.extraplayermodels.model.UVCoordinate
import java.lang.reflect.Type

/**
 * Deserializes a [UVCoordinate].
 */
class UVDeserializer : JsonDeserializer<UVCoordinate> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): UVCoordinate {
        // Expect an array for the two numbers
        if (json?.isJsonArray != true) {
            throw JsonParseException("UV must be an array.")
        }
        // Convert
        val arr = json.asJsonArray
        // Two elements, please
        if (arr?.size() != 2) {
            throw JsonParseException("UV must have 2 elements")
        }
        // Must be numbers
        if (!arr[0].isJsonPrimitive or !arr[0].asJsonPrimitive.isNumber or
            !arr[1].isJsonPrimitive or !arr[1].asJsonPrimitive.isNumber) {
                throw JsonParseException("Element should be a number")
        }
        // Convert and make
        return UVCoordinate(
            arr[0].asJsonPrimitive.asNumber.toInt(),
            arr[1].asJsonPrimitive.asNumber.toInt()
        )
    }
}