package spazzylemons.extraplayermodels.client

import spazzylemons.extraplayermodels.model.CustomPlayerModel

/**
 * Configuration structure class for Extra Player Models.
 *
 * @property model The model to use, can be null if the player is using the vanilla model.
 */
class Configuration(val model: CustomPlayerModel?)