package com.feature.add_edit_note.ui.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.core.common.di.AssistedViewModelFactory
import com.core.common.navigation.AddEditNoteScreen
import com.core.domain.model.NoteWithTag
import com.core.domain.model.TagWithNotes
import com.core.domain.usecase.GetAllTagsWithNotesUseCase
import com.feature.add_edit_note.domain.usecase.GetNoteWithTagByIdUseCase
import com.feature.add_edit_note.domain.usecase.UpsertNotesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddEditNoteViewModel
    @AssistedInject
    constructor(
        @Assisted private val savedStateHandle: SavedStateHandle,
        private val getNoteWithTagByIdUseCase: GetNoteWithTagByIdUseCase,
        private val upsertNotesUseCase: UpsertNotesUseCase,
        private val getAllTagsWithNotesUseCase: GetAllTagsWithNotesUseCase,
    ) : ViewModel() {
        @AssistedFactory
        interface Factory : AssistedViewModelFactory<AddEditNoteViewModel> {
            override fun create(savedStateHandle: SavedStateHandle): AddEditNoteViewModel
        }

        private val _addEditeNoteUiState: MutableStateFlow<AddEditNoteUiState> =
            MutableStateFlow(AddEditNoteUiState.Idle)
        val addEditeNoteUiState: StateFlow<AddEditNoteUiState> =
            _addEditeNoteUiState.asStateFlow()

        private var _sideEffect = MutableSharedFlow<AddEditNoteSideEffect>()
        val sideEffect: SharedFlow<AddEditNoteSideEffect> = _sideEffect.asSharedFlow()

        private var currentNote: NoteWithTag? = null

        init {
            val addEditNoteScreen = savedStateHandle.toRoute<AddEditNoteScreen>()
            getNoteAndTags(addEditNoteScreen.noteId)
        }

        private fun getNoteAndTags(noteId: Long) {
            @Suppress("TooGenericExceptionCaught")
            viewModelScope.launch {
                try {
                    val note = getNoteWithTagByIdUseCase(id = noteId)
                    currentNote = note ?: NoteWithTag(
                        content = "",
                        timestamp = System.currentTimeMillis(),
                        tagId = 1,
                        done = false,
                        tagName = "Work",
                        tagColor = "#61DEA4",
                        date = "2025-06-25", // TODO fix this
                        time = "00:00:00", // TODO fix this
                    )
                    getAllTagsWithNotesUseCase().collect { tags: List<TagWithNotes> ->
                        currentNote?.let { noteWithTag: NoteWithTag ->
                            _addEditeNoteUiState.value =
                                AddEditNoteUiState.NoteAndTags(note = noteWithTag, tags = tags.toPersistentList())
                        }
                    }
                } catch (e: Exception) {
                    Log.e("AddEditNoteViewModel", "Error loading note", e)
                    _addEditeNoteUiState.value = AddEditNoteUiState.Error("Failed to load note")
                }
            }
        }

        fun onNoteChange(note: NoteWithTag) {
            currentNote = note
            currentNote?.let { note: NoteWithTag ->
                _addEditeNoteUiState.update { currentState: AddEditNoteUiState ->
                    if (currentState is AddEditNoteUiState.NoteAndTags) {
                        currentState.copy(note = note)
                    } else {
                        currentState
                    }
                }
            }
        }

        fun saveNote() {
            viewModelScope.launch {
                currentNote?.let { note ->
                    if (note.content.isNotBlank()) {
                        upsertNotesUseCase(listOf(note))
                        _sideEffect.emit(AddEditNoteSideEffect.Finish)
                    }
                }
            }
        }
    }
