package spazzylemons.extraplayermodels.model.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import spazzylemons.extraplayermodels.model.CustomPlayerModel
import java.lang.reflect.Type

class CustomPlayerModelDeserializer : JsonDeserializer<CustomPlayerModel> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): CustomPlayerModel {
        // Make sure it is an object
        if (json?.isJsonObject != true) {
            throw JsonParseException("Model must be an object")
        }
        // Convert it
        val obj = json.asJsonObject
        // Make it
        return CustomPlayerModel(
            obj.requiredProperty("head"),
            obj.requiredProperty("torso"),
            obj.requiredProperty("leftArm"),
            obj.requiredProperty("rightArm"),
            obj.requiredProperty("leftLeg"),
            obj.requiredProperty("rightLeg"),
            obj.requiredProperty("texture")
        )
    }
}