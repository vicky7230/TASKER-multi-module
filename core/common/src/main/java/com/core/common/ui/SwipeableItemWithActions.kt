package com.core.common.ui

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeableItemWithActions(
    isRevealed: Boolean,
    actions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    onExpand: () -> Unit = {},
    onCollapse: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    var contextMenuWidth by remember { mutableFloatStateOf(0f) }
    val offset = remember { Animatable(initialValue = 0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(isRevealed, contextMenuWidth) {
        if (isRevealed) {
            offset.animateTo(-contextMenuWidth)
        } else {
            offset.animateTo(0f)
        }
    }

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
    ) {
        Row(
            modifier =
                Modifier
                    .onSizeChanged {
                        contextMenuWidth = it.width.toFloat()
                    }.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            actions()
        }
        Surface(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .offset { IntOffset(offset.value.roundToInt(), 0) }
                    .pointerInput(contextMenuWidth) {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { change, dragAmount ->
                                Log.d("TAG", "dragAmount $dragAmount")
                                scope.launch {
                                    val newOffset =
                                        (offset.value + dragAmount).coerceIn(-contextMenuWidth, 0f)
                                    Log.d("TAG", "offset $newOffset")
                                    offset.snapTo(newOffset)
                                }
                            },
                            onDragEnd = {
                                when {
                                    -offset.value >= contextMenuWidth / 2f -> {
                                        Log.d("TAG", "TRUE")
                                        scope.launch {
                                            offset.animateTo(-contextMenuWidth)
                                            onExpand()
                                        }
                                    }

                                    else -> {
                                        scope.launch {
                                            Log.d("TAG", "FALSE")
                                            offset.animateTo(0f)
                                            onCollapse()
                                        }
                                    }
                                }
                            },
                        )
                    },
        ) {
            content()
        }
    }
}
