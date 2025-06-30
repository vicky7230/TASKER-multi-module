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
