package com.feature.notes.ui.screen.composables

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class FabOption(
    val label: String,
    val icon: ImageVector,
    val color: Color,
    val onClick: () -> Unit,
)
