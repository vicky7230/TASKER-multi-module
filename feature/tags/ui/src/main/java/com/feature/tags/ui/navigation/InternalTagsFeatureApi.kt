package com.feature.tags.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.core.common.navigation.AddEditNoteScreen
import com.core.common.navigation.TagGraph
import com.core.common.navigation.TagScreen
import com.core.domain.model.TagWithNotes
import com.core.feature_api.FeatureApi
import com.feature.tags.ui.screen.TagScreenUi
import com.feature.tags.ui.screen.TagsUiBottomSheet
import com.feature.tags.ui.screen.TagsViewModel
import javax.inject.Inject

internal class InternalTagsFeatureApi
    @Inject
    constructor(
        private val tagsViewModelFactory: TagsViewModel.Factory,
    ) : FeatureApi {
        override fun registerGraph(
            navHostController: NavHostController,
            navGraphBuilder: NavGraphBuilder,
            viewModelFactory: ViewModelProvider.Factory,
        ) {
            navGraphBuilder.navigation<TagGraph>(
                startDestination = TagScreen(tagId = 0),
            ) {
                composable<TagScreen> { navBackStackEntry: NavBackStackEntry ->
                    val tagId = navBackStackEntry.arguments?.getLong("tagId") ?: 0L
                    Log.d("Tag Id: ", tagId.toString())
                    val tagsViewModel =
                        viewModel<TagsViewModel>(
                            viewModelStoreOwner = navBackStackEntry,
                            factory =
                                object : ViewModelProvider.Factory {
                                    override fun <T : ViewModel> create(
                                        modelClass: Class<T>,
                                        extras: CreationExtras,
                                    ): T {
                                        @Suppress("UNCHECKED_CAST")
                                        return tagsViewModelFactory.create(extras.createSavedStateHandle()) as T
                                    }
                                },
                        )
                    val state by tagsViewModel.tagsUiState.collectAsStateWithLifecycle()
                    TagScreenUi(
                        modifier = Modifier.fillMaxSize(),
                        tagsUiState = state,
                        onEditTagClick = { tagWithNotes: TagWithNotes ->
                            tagsViewModel.showRenameTagBottomSheet(
                                TagsUiBottomSheet.RenameTagBottomSheet(
                                    tagId = tagWithNotes.id,
                                    tagName = tagWithNotes.name,
                                    tagColor = tagWithNotes.color,
                                ),
                            )
                        },
                        hideEditTagBottomSheet = {
                            tagsViewModel.showRenameTagBottomSheet(TagsUiBottomSheet.None)
                        },
                        onSaveTagNameClick = tagsViewModel::updateTagName,
                        onNoteClick = { note ->
                            navHostController.navigate(AddEditNoteScreen(noteId = note.id))
                        },
                        onNoteDoneClick = tagsViewModel::markNoteAsDone,
                        onNoteDeleteClick = tagsViewModel::markNoteAsDeleted,
                    )
                }
            }
        }
    }
