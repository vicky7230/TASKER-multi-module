package com.feature.add_edit_note.ui.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.core.common.di.AssistedViewModelFactory
import com.core.common.navigation.AddEditNoteScreen
import com.feature.add_edit_note.domain.usecase.GetNoteByIdUseCase
import com.feature.add_edit_note.domain.usecase.UpsertNoteUseCase
import com.feature.notes.domain.model.Note
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddEditNoteViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val upsertNoteUseCase: UpsertNoteUseCase
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

    private var currentNote: Note? = null

    init {
        val addEditNoteScreen = savedStateHandle.toRoute<AddEditNoteScreen>()
        getNoteById(addEditNoteScreen.noteId)
    }

    private fun getNoteById(noteId: Long) {
        viewModelScope.launch {
            val note = getNoteByIdUseCase(id = noteId)
            currentNote = note ?: Note(content = "")
            currentNote?.let {
                _addEditeNoteUiState.value = AddEditNoteUiState.NoteData(it)
            }
        }
    }

    fun onNoteContentChanged(content: String) {
        currentNote = currentNote?.copy(content = content)
        currentNote?.let {
            _addEditeNoteUiState.value = AddEditNoteUiState.NoteData(it)
        }
    }

    fun saveNote() {
        viewModelScope.launch {
            currentNote?.let { note ->
                if (note.content.isNotBlank()) {
                    upsertNoteUseCase(note)
                }
            }
            _sideEffect.emit(AddEditNoteSideEffect.finish)
        }
    }
}
