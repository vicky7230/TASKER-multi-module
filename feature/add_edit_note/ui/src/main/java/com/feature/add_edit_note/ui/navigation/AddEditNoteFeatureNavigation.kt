package com.feature.add_edit_note.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
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
import com.feature.add_edit_note.ui.ui.AddEditNoteScreen
import com.feature.add_edit_note.ui.ui.AddEditNoteSideEffect
import com.feature.add_edit_note.ui.ui.AddEditNoteViewModel
import javax.inject.Inject

interface AddEditNoteFeature : Feature

class AddEditNoteFeatureImpl
    @Inject
    constructor() : AddEditNoteFeature {
        override fun registerGraph(
            navHostController: NavHostController,
            navGraphBuilder: NavGraphBuilder,
        ) {
            navGraphBuilder.navigation<SubGraph.AddEditNote>(
                startDestination = Dest.AddEditNote(noteId = 0L),
            ) {
                composable<Dest.AddEditNote> { navBackStackEntry ->
                    val noteId = navBackStackEntry.arguments?.getLong("noteId") ?: 0L
                    Log.d("Note Id: ", noteId.toString())
                    val addEditNoteViewModel: AddEditNoteViewModel = hiltViewModel()
                    val state by addEditNoteViewModel.addEditeNoteUiState.collectAsStateWithLifecycle()
                    LaunchedEffect(Unit) {
                        addEditNoteViewModel.sideEffect.collect { sideEffect ->
                            when (sideEffect) {
                                AddEditNoteSideEffect.Finish -> navHostController.popBackStack()
                            }
                        }
                    }
                    AddEditNoteScreen(
                        modifier = Modifier.fillMaxSize(),
                        addEditNoteUiState = state,
                        onNoteChange = addEditNoteViewModel::onNoteChange,
                        onCancelClick = { navHostController.popBackStack() },
                        onDoneClick = addEditNoteViewModel::saveNote,
                    )
                }
            }
        }
    }
