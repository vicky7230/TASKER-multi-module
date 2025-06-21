package com.feature.notes.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.common.utils.TimeUtils
import com.feature.notes.domain.usecase.GetAllNotesWithTagUseCase
import com.feature.notes.domain.usecase.GetAllTagsWithNotesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesViewModel
    @Inject
    constructor(
        private val getAllNotesWithTagUseCase: GetAllNotesWithTagUseCase,
        private val getAllTagsWithNotesUseCase: GetAllTagsWithNotesUseCase,
    ) : ViewModel() {
        private val _notesUiState: MutableStateFlow<NotesUiState> = MutableStateFlow(NotesUiState.Idle)
        val notesUiState: StateFlow<NotesUiState> = _notesUiState.asStateFlow()

        init {
            viewModelScope.launch {
                @Suppress("TooGenericExceptionCaught")
                try {
                    combine(
                        getAllNotesWithTagUseCase().distinctUntilChanged(),
                        getAllTagsWithNotesUseCase().distinctUntilChanged(),
                    ) { notes, tags ->
                        NotesUiState.NotesLoaded(
                            notes.filter { TimeUtils.isTimestampToday(it.timestamp) },
                            tags,
                        )
                    }.flowOn(Dispatchers.IO)
                        .collect { newState ->
                            _notesUiState.update { currentState ->
                                newState
                            }
                        }
                } catch (e: Exception) {
                    _notesUiState.update { NotesUiState.Error(e.message ?: "Unknown error") }
                }
            }
        }
    }
