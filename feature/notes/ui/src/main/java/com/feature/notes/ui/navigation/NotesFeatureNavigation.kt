package com.feature.notes.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.core.common.navigation.Dest
import com.core.common.navigation.Feature
import com.core.common.navigation.SubGraph
import com.feature.notes.ui.screen.NotesScreen
import com.feature.notes.ui.screen.NotesUiBottomSheet
import com.feature.notes.ui.screen.NotesViewModel
import javax.inject.Inject

interface NotesFeature : Feature

class NotesFeatureImpl
    @Inject
    constructor() : NotesFeature {
        override fun registerGraph(
            navHostController: NavHostController,
            navGraphBuilder: NavGraphBuilder,
        ) {
            navGraphBuilder.navigation<SubGraph.Notes>(
                startDestination = Dest.Notes,
            ) {
                composable<Dest.Notes> { navBackStackEntry ->
                    val notesViewModel: NotesViewModel = hiltViewModel()
                    val state by notesViewModel.notesUiState.collectAsStateWithLifecycle()
                    NotesScreen(
                        modifier = Modifier.fillMaxSize(),
                        notesUiState = state,
                        onNoteClick = { note ->
                            navHostController.navigate(Dest.AddEditNote(noteId = note.id))
                        },
                        onTagClick = { tag ->
                            navHostController.navigate(Dest.Tags(tagId = tag.id))
                        },
                        onAddNoteClick = {
                            notesViewModel.onFabClick()
                            navHostController.navigate(Dest.AddEditNote(noteId = 0L))
                        },
                        onAddTagClick = {
                            notesViewModel.onFabClick()
                            notesViewModel.showCreateTagBottomSheet(NotesUiBottomSheet.CreateTagBottomSheet())
                        },
                        onFabClick = notesViewModel::onFabClick,
                        onBottomSheetColorItemClick = { color ->
                            notesViewModel.showCreateTagBottomSheet(
                                NotesUiBottomSheet.CreateTagBottomSheet(
                                    selectedColor = color,
                                ),
                            )
                        },
                        hideCreateTagBottomSheet = {
                            notesViewModel.showCreateTagBottomSheet(NotesUiBottomSheet.None)
                        },
                        onSaveTagNameClick = notesViewModel::createTag,
                        onNoteDoneClick = notesViewModel::markNoteAsDone,
                        onNoteDeleteClick = notesViewModel::markNoteAsDeleted,
                    )
                }
            }
        }
    }
