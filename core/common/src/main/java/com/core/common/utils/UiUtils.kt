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

fun Color.toHexString(): String {
    val alpha = (alpha * 255).toInt().toString(16).padStart(2, '0')
    val red = (red * 255).toInt().toString(16).padStart(2, '0')
    val green = (green * 255).toInt().toString(16).padStart(2, '0')
    val blue = (blue * 255).toInt().toString(16).padStart(2, '0')
    return "#$alpha$red$green$blue"
}
