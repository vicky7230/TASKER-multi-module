package com.core.common.ui

import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Composable
fun ObserveKeyboardWithViewTree(
    onKeyboardVisibilityChange: (Boolean) -> Unit,
) {
    val view = LocalView.current
    val currentOnKeyboardVisibilityChange by rememberUpdatedState(onKeyboardVisibilityChange)
    DisposableEffect(view) {
        val listener =
            ViewTreeObserver.OnGlobalLayoutListener {
                val isKeyboardOpen =
                    ViewCompat
                        .getRootWindowInsets(view)
                        ?.isVisible(WindowInsetsCompat.Type.ime()) == true

                currentOnKeyboardVisibilityChange(isKeyboardOpen)
            }

        view.viewTreeObserver.addOnGlobalLayoutListener(listener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
}
