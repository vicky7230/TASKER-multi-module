package com.vicky7230.tasker.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.core.common.navigation.NotesFeature

@Composable
fun AppNavGraph(
    navController: NavHostController,
    navigationProvider: NavigationProvider,
    viewModelFactory: ViewModelProvider.Factory
) {
    NavHost(navController = navController, startDestination = NotesFeature.nestedRoute) {
        navigationProvider.notesApi.registerGraph(navController, this, viewModelFactory)
    }
}