package com.feature.tags.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.core.common.navigation.Dest
import com.core.common.navigation.Feature
import com.core.common.navigation.SubGraph
import com.core.domain.model.TagWithNotes
import com.feature.tags.ui.screen.TagScreenUi
import com.feature.tags.ui.screen.TagsUiBottomSheet
import com.feature.tags.ui.screen.TagsViewModel
import javax.inject.Inject

interface TagsFeature : Feature

class TagsFeatureImpl
    @Inject
    internal constructor() : TagsFeature {
        override fun registerGraph(
            navHostController: NavHostController,
            navGraphBuilder: NavGraphBuilder,
        ) {
            navGraphBuilder.navigation<SubGraph.Tags>(startDestination = Dest.Tags(tagId = 0)) {
                composable<Dest.Tags> { navBackStackEntry: NavBackStackEntry ->
                    val tagId = navBackStackEntry.arguments?.getLong("tagId") ?: 0L
                    Log.d("Tag Id: ", tagId.toString())
                    val tagsViewModel: TagsViewModel = hiltViewModel()
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
                            navHostController.navigate(Dest.AddEditNote(noteId = note.id))
                        },
                        onNoteDoneClick = tagsViewModel::markNoteAsDone,
                        onNoteDeleteClick = tagsViewModel::markNoteAsDeleted,
                    )
                }
            }
        }
    }
