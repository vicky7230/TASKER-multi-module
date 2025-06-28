@file:Suppress("MagicNumber")
@file:SuppressLint("NewApi")

package com.feature.notes.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.common.utils.TimeUtils
import com.core.domain.model.NoteWithTag
import com.core.domain.usecase.GetAllTagsWithNotesUseCase
import com.feature.notes.domain.usecase.GetAllNotesWithTagUseCase
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class NotesViewModel
    @Inject
    constructor(
        private val getAllNotesWithTagUseCase: GetAllNotesWithTagUseCase,
        private val getAllTagsWithNotesUseCase: GetAllTagsWithNotesUseCase,
    ) : ViewModel() {
        companion object {
            private const val TAG = "NotesViewModel"
        }

        val notesUiState: StateFlow<NotesUiState> =
            combine(
                getAllNotesWithTagUseCase().distinctUntilChanged(),
                getAllTagsWithNotesUseCase().distinctUntilChanged(),
            ) { notes, tags ->
                notes.filter { noteWithTag: NoteWithTag ->
                    TimeUtils.isTimestampToday(
                        LocalDate
                            .parse(noteWithTag.date)
                            .atStartOfDay(ZoneId.systemDefault())
                            .toInstant()
                            .toEpochMilli(),
                    )
                } to tags
            }.map { (filteredNotes, tags) ->
                NotesUiState.NotesLoaded(
                    notes = filteredNotes.toPersistentList(),
                    tags = tags.toPersistentList(),
                ) as NotesUiState
            }.onStart {
                emit(NotesUiState.Loading)
            }.flowOn(Dispatchers.IO)
                .catch { throwable: Throwable ->
                    Log.e(TAG, "Error loading notes", throwable)
                    emit(NotesUiState.Error("Error loading notes"))
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000L),
                    initialValue = NotesUiState.Idle,
                )
    }
