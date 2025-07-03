@file:Suppress("MagicNumber")

package com.vicky7230.tasker2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.core.common.theme.TaskerTheme
import com.vicky7230.tasker2.navigation.AppNavGraph
import com.vicky7230.tasker2.navigation.NavigationProvider
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationProvider: NavigationProvider

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as BaseApplication).applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskerTheme {
                // Set status bar appearance based on theme
                val isDarkTheme =
                    !MaterialTheme.colorScheme.background
                        .luminance()
                        .let { it > 0.5f }
                LaunchedEffect(isDarkTheme) {
                    WindowCompat.getInsetsController(window, window.decorView).apply {
                        isAppearanceLightStatusBars = !isDarkTheme
                    }
                }

                val navController = rememberNavController()
                App(navHostController = navController, navigationProvider = navigationProvider, viewModelFactory = viewModelFactory)
            }
        }
    }
}

@Composable
fun App(
    navHostController: NavHostController,
    navigationProvider: NavigationProvider,
    viewModelFactory: ViewModelProvider.Factory,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier =
            modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
                .windowInsetsPadding(WindowInsets.navigationBars),
        color = MaterialTheme.colorScheme.background,
    ) {
        AppNavGraph(
            navController = navHostController,
            navigationProvider = navigationProvider,
            viewModelFactory = viewModelFactory,
        )
    }
}
