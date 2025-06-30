package com.feature.tags.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.core.common.navigation.AddEditNoteScreen
import com.core.common.navigation.TagGraph
import com.core.common.navigation.TagScreen
import com.core.feature_api.FeatureApi
import com.feature.tags.ui.screen.TagScreenUi
import com.feature.tags.ui.screen.TagsViewModel

object InternalTagsFeatureApi : FeatureApi {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder,
        viewModelFactory: ViewModelProvider.Factory,
    ) {
        navGraphBuilder.navigation<TagGraph>(
            startDestination = TagScreen(tagId = 0),
        ) {
            composable<TagScreen> { navBackStackEntry: NavBackStackEntry ->
                val noteId = navBackStackEntry.arguments?.getLong("tagId") ?: 0L
                Log.d("Tag Id: ", noteId.toString())
                val tagsViewModel =
                    viewModel<TagsViewModel>(
                        viewModelStoreOwner = navBackStackEntry,
                        factory = viewModelFactory,
                    )
                val state by tagsViewModel.tagsUiState.collectAsStateWithLifecycle()
                TagScreenUi(
                    modifier = Modifier.fillMaxSize(),
                    tagsUiState = state,
                    onNoteClick = { note ->
                        navHostController.navigate(AddEditNoteScreen(noteId = note.id))
                    },
                )
            }
        }
    }
}
