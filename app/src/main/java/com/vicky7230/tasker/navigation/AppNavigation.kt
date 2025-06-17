package com.vicky7230.tasker.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.core.common.navigation.NotesGraph

@Composable
fun AppNavGraph(
    navController: NavHostController,
    navigationProvider: NavigationProvider,
    viewModelFactory: ViewModelProvider.Factory,
) {
    NavHost(navController = navController, startDestination = NotesGraph) {
        navigationProvider.notesApi.registerGraph(navController, this, viewModelFactory)
        navigationProvider.addEditNoteApi.registerGraph(navController, this, viewModelFactory)
    }
}
