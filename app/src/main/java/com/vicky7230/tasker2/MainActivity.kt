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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.core.common.navigation.SubGraph
import com.core.common.theme.TaskerTheme
import com.vicky7230.tasker2.navigation.DefaultNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var defaultNavigator: DefaultNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
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
                App(navHostController = navController, defaultNavigator = defaultNavigator)
            }
        }
    }
}

@Composable
fun App(
    navHostController: NavHostController,
    defaultNavigator: DefaultNavigator,
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
        NavHost(navController = navHostController, startDestination = SubGraph.Notes) {
            defaultNavigator.notesFeature.registerGraph(
                navHostController = navHostController,
                navGraphBuilder = this,
            )
            defaultNavigator.addEditNoteFeature.registerGraph(
                navHostController = navHostController,
                navGraphBuilder = this,
            )
            defaultNavigator.tagsFeature.registerGraph(
                navHostController = navHostController,
                navGraphBuilder = this,
            )
        }
    }
}
