package spazzylemons.extraplayermodels.model.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import net.minecraft.client.util.math.Vector3f
import spazzylemons.extraplayermodels.model.CustomModelBox
import spazzylemons.extraplayermodels.model.CustomModelPart
import java.lang.reflect.Type

class CustomModelPartDeserializer : JsonDeserializer<CustomModelPart> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): CustomModelPart {
        // Make sure it is an object
        if (json?.isJsonObject != true) {
            throw JsonParseException("Model part must be an object")
        }
        // Convert it
        val obj = json.asJsonObject
        // Make it
        return CustomModelPart(
            obj.optionalProperty("pivot", Vector3f(0F, 0F, 0F)),
            obj.optionalProperty("rotation", Vector3f(0F, 0F, 0F)),
            (obj.optionalProperty("boxes", emptyArray<CustomModelBox>())).toList(),
            (obj.optionalProperty("children", emptyArray<CustomModelPart>())).toList()
        )
    }
}