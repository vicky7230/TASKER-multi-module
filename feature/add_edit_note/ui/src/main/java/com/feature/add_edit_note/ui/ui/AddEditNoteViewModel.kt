@file:SuppressLint("NewApi")

package com.feature.add_edit_note.ui.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.core.common.di.AssistedViewModelFactory
import com.core.common.navigation.AddEditNoteScreen
import com.core.domain.model.NoteWithTag
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TAG = "NoteContent"

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
                    val tags = getAllTagsWithNotesUseCase().first()

                    if (tags.isNotEmpty()) {
                        val note = getNoteWithTagByIdUseCase(id = noteId)
                        currentNote = note ?: NoteWithTag(
                            content = "",
                            timestamp = System.currentTimeMillis(),
                            tagId = 1,
                            done = false,
                            tagName = tags[0].name,
                            tagColor = tags[0].color,
                            date = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
                            time =
                                LocalDate
                                    .now()
                                    .atStartOfDay()
                                    .toLocalTime()
                                    .format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                        )

                        currentNote?.let { noteWithTag: NoteWithTag ->
                            _addEditeNoteUiState.value =
                                AddEditNoteUiState.NoteAndTags(
                                    note = noteWithTag,
                                    tags = tags.toPersistentList(),
                                )
                        }
                    }
                } catch (e: Exception) {
                    Log.e("AddEditNoteViewModel", "Error loading note and tags", e)
                    _addEditeNoteUiState.value = AddEditNoteUiState.Error("Failed to load note and tags")
                }
            }
        }

        fun onNoteChange(noteWithTag: NoteWithTag) {
            currentNote = noteWithTag
            currentNote?.let { note: NoteWithTag ->
                _addEditeNoteUiState.update { currentState: AddEditNoteUiState ->
                    if (currentState is AddEditNoteUiState.NoteAndTags) {
                        Log.d(TAG, "onNoteChange: $note")
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
