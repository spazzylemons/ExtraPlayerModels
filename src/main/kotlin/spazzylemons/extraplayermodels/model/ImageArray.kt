package spazzylemons.extraplayermodels.model

import net.minecraft.client.texture.NativeImage

/**
 * A simple class that wraps an IntArray
 */
class ImageArray(val ints: IntArray) {
    fun toNativeImage(): NativeImage {
        val nativeImage = NativeImage(64, 64, true)
        var i = 0
        for (y in 0..63) {
            for (x in 0..63) {
                val idk = ints[i++]
                nativeImage.setPixelRgba(x, y, idk)
            }
        }
        return nativeImage
    }
}