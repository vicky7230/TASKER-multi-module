package com.feature.notes.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feature.notes.domain.usecase.GetAllNotesWithTagUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesWithTagUseCase
) : ViewModel() {

    private val _notesUiState: MutableStateFlow<NotesUiState> = MutableStateFlow(NotesUiState.Idle)
    val notesUiState: StateFlow<NotesUiState> = _notesUiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                getAllNotesUseCase()
                    .distinctUntilChanged()
                    .collect {
                        _notesUiState.update { currentState ->
                            NotesUiState.NotesList(it)
                        }
                    }
            } catch (e: Exception) {
                _notesUiState.update { NotesUiState.Error(e.message ?: "Unknown error") }
            }
        }
    }
}
