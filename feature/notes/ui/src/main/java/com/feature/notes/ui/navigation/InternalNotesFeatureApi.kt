package com.feature.notes.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.core.common.navigation.NotesFeature
import com.core.feature_api.FeatureApi
import com.feature.notes.ui.screen.NotesScreen
import com.feature.notes.ui.screen.NotesViewModel

internal object InternalNotesFeatureApi : FeatureApi {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder,
        viewModelFactory: ViewModelProvider.Factory
    ) {
        navGraphBuilder.navigation(
            startDestination = NotesFeature.notesScreenRoute,
            route = NotesFeature.nestedRoute
        ) {
            composable(NotesFeature.notesScreenRoute) { navBackStackEntry ->
                val notesViewModel = viewModel<NotesViewModel>(
                    viewModelStoreOwner = navBackStackEntry,
                    factory = viewModelFactory
                )
                val state by notesViewModel.notesUiState.collectAsStateWithLifecycle()
                NotesScreen(
                    modifier = Modifier.fillMaxSize(),
                    notesUiState = state ,
                    onNoteClick = { note->

                    },
                    onAddNoteClick = {

                    }
                )
            }
        }
    }
}