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
import com.core.domain.usecase.UpdateNoteDeletedUseCase
import com.core.domain.usecase.UpdateNoteDoneUseCase
import com.feature.notes.domain.usecase.CreateTagUseCase
import com.feature.notes.domain.usecase.GetAllNotesWithTagUseCase
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class NotesViewModel
    @Inject
    constructor(
        private val getAllNotesWithTagUseCase: GetAllNotesWithTagUseCase,
        private val getAllTagsWithNotesUseCase: GetAllTagsWithNotesUseCase,
        private val createTagUseCase: CreateTagUseCase,
        private val updateNoteDoneUseCase: UpdateNoteDoneUseCase,
        private val updateNoteDeletedUseCase: UpdateNoteDeletedUseCase,
    ) : ViewModel() {
        companion object {
            private const val TAG = "NotesViewModel"
        }

        private val _notesUiState: MutableStateFlow<NotesUiState> =
            MutableStateFlow(NotesUiState.Loading)
        val notesUiState: StateFlow<NotesUiState> = _notesUiState.asStateFlow()

        init {
            viewModelScope.launch {
                @Suppress("TooGenericExceptionCaught")
                try {
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
                    }.collect { (filteredNotes, tags) ->
                        _notesUiState.value =
                            NotesUiState.NotesLoaded(
                                notes = filteredNotes.toPersistentList(),
                                tags = tags.toPersistentList(),
                            )
                    }
                } catch (ex: Exception) {
                    Log.e(TAG, "Error loading notes", ex)
                    _notesUiState.value = NotesUiState.Error("Error loading notes")
                }
            }
        }

        fun showCreateTagBottomSheet(notesUiBottomSheet: NotesUiBottomSheet) {
            _notesUiState.update {
                if (it is NotesUiState.NotesLoaded) {
                    it.copy(bottomSheet = notesUiBottomSheet)
                } else {
                    it
                }
            }
        }

        fun onFabClick() {
            _notesUiState.update {
                if (it is NotesUiState.NotesLoaded) {
                    it.copy(fabExpanded = !it.fabExpanded)
                } else {
                    it
                }
            }
        }

        fun createTag(
            tagName: String,
            tagColor: String,
        ) {
            viewModelScope.launch {
                createTagUseCase(tagName = tagName, tagColor = tagColor)
            }
        }

        fun markNoteAsDone(noteWithTag: NoteWithTag) {
            viewModelScope.launch {
                updateNoteDoneUseCase(id = noteWithTag.id, done = true)
            }
        }

        fun markNoteAsDeleted(noteWithTag: NoteWithTag) {
            viewModelScope.launch {
                updateNoteDeletedUseCase(id = noteWithTag.id, deleted = true)
            }
        }
    }
