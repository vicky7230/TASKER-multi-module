package com.core.common.utils

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

fun String.toColorSafely(): Color =
    try {
        Color(this.toColorInt())
    } catch (e: IllegalArgumentException) {
        Color.Black
    }
