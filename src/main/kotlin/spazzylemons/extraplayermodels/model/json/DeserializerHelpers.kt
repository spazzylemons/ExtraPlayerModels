package spazzylemons.extraplayermodels.model.json

import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import spazzylemons.extraplayermodels.model.ModelLoader

/**
 * Deserialize a required property. If the property is not found, a [JsonParseException] is thrown.
 *
 * @param name The name of the property.
 */
inline fun <reified T> JsonObject.requiredProperty(name: String): T {
    val property = this[name] ?: throw JsonParseException("Property '$name' is required")
    return ModelLoader.gson.fromJson(property, T::class.java)
}


/**
 * Deserialize an optional property. If the property is not found, a sensible alternative is used.
 *
 * @param name The name of the property.
 * @param alternate The alternative object.
 */
inline fun <reified T> JsonObject.optionalProperty(name: String, alternate: T): T {
    val property = this[name] ?: return alternate
    return ModelLoader.gson.fromJson(property, T::class.java)
}