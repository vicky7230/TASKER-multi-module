package com.feature.notes.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.common.utils.TimeUtils
import com.feature.notes.domain.usecase.GetAllNotesWithTagUseCase
import com.feature.notes.domain.usecase.GetAllTagsWithNotesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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
                combine(
                    getAllNotesWithTagUseCase().distinctUntilChanged(),
                    getAllTagsWithNotesUseCase().distinctUntilChanged(),
                ) { notes, tags ->
                    NotesUiState.NotesLoaded(
                        notes.filter { TimeUtils.isTimestampToday(it.timestamp) },
                        tags,
                    )
                }.flowOn(Dispatchers.IO)
                    .catch { throwable: Throwable ->
                        Log.e("NotesViewModel", "Error loading notes", throwable)
                        _notesUiState.update {
                            NotesUiState.Error(
                                throwable.message ?: "Unknown error",
                            )
                        }
                    }.collect { newState ->
                        _notesUiState.update { currentState ->
                            newState
                        }
                    }
            }
        }
    }
