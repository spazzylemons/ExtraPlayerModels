package spazzylemons.extraplayermodels.model.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import net.minecraft.client.MinecraftClient
import spazzylemons.extraplayermodels.client.Configuration
import spazzylemons.extraplayermodels.model.CustomPlayerModel
import spazzylemons.extraplayermodels.model.ModelLoader
import java.io.File
import java.lang.reflect.Type

/**
 * Deserialize a [Configuration].
 */
class ConfigurationDeserializer : JsonDeserializer<Configuration> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Configuration {
        // Make sure configuration is an object
        if (json?.isJsonObject != true) {
            throw JsonParseException("Configuration must be an object")
        }
        // Convert it
        val obj = json.asJsonObject
        // Get the model property
        val modelProperty = obj["model"]
        return when {
            // If null, return a configuration with no model
            modelProperty.isJsonNull -> {
                Configuration(null)
            }
            // If string, look for model file and parse
            modelProperty.isJsonPrimitive and modelProperty.asJsonPrimitive.isString -> {
                // Get the file
                val modelFile = File(File(MinecraftClient.getInstance().runDirectory, "config/extraplayermodels/models"), modelProperty.asString)
                // Doesn't exist? Exception. No trial, no nothing. We have strongest deserializer because of exception.
                if (!modelFile.exists()) {
                    throw JsonParseException("Model file does not exist")
                }
                // Make the configuration
                Configuration(
                    ModelLoader.gson.fromJson(modelFile.reader(), CustomPlayerModel::class.java)
                )
            }
            // Otherwise, throw an exception
            else -> {
                throw JsonParseException("Model name must be a string or null")
            }
        }
    }

}