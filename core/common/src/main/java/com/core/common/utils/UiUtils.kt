@file:Suppress("MagicNumber")

package com.core.common.utils

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

private const val TAG = "UiUtils"

fun String.toColorSafely(): Color =
    try {
        Color(this.toColorInt())
    } catch (e: IllegalArgumentException) {
        Log.e(TAG, "Invalid color string: $this", e)
        Color.Black
    }

@Suppress("ImplicitDefaultLocale")
fun Color.toHexString(includeAlpha: Boolean = true): String {
    val a = (alpha * 255).toInt()
    val r = (red * 255).toInt()
    val g = (green * 255).toInt()
    val b = (blue * 255).toInt()

    return if (includeAlpha) {
        String.format("#%02X%02X%02X%02X", a, r, g, b)
    } else {
        String.format("#%02X%02X%02X", r, g, b)
    }
}
