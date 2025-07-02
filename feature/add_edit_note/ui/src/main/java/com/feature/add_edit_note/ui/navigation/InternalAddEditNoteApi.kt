package com.feature.add_edit_note.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.core.common.navigation.AddEditNoteGraph
import com.core.common.navigation.AddEditNoteScreen
import com.core.feature_api.FeatureApi
import com.feature.add_edit_note.ui.ui.AddEditNoteScreen
import com.feature.add_edit_note.ui.ui.AddEditNoteSideEffect
import com.feature.add_edit_note.ui.ui.AddEditNoteViewModel
import javax.inject.Inject

internal class InternalAddEditNoteApi
    @Inject
    constructor(
        private val addEditNoteViewModelFactory: AddEditNoteViewModel.Factory,
    ) : FeatureApi {
        override fun registerGraph(
            navHostController: NavHostController,
            navGraphBuilder: NavGraphBuilder,
            viewModelFactory: ViewModelProvider.Factory,
        ) {
            navGraphBuilder.navigation<AddEditNoteGraph>(startDestination = AddEditNoteScreen(noteId = 0L)) {
                composable<AddEditNoteScreen> { navBackStackEntry ->
                    val noteId = navBackStackEntry.arguments?.getLong("noteId") ?: 0L
                    Log.d("Note Id: ", noteId.toString())
                    val addEditNoteViewModel =
                        viewModel<AddEditNoteViewModel>(
                            viewModelStoreOwner = navBackStackEntry,
                            factory =
                                object : ViewModelProvider.Factory {
                                    override fun <T : ViewModel> create(
                                        modelClass: Class<T>,
                                        extras: CreationExtras,
                                    ): T {
                                        @Suppress("UNCHECKED_CAST")
                                        return addEditNoteViewModelFactory.create(extras.createSavedStateHandle()) as T
                                    }
                                },
                        )
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
