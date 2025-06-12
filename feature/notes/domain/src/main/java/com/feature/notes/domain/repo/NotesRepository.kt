package com.feature.notes.domain.repo

import com.feature.notes.domain.model.Note
import com.feature.notes.domain.model.NoteWithTag
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun upsertNote(note: NoteWithTag): Long

    fun getAllNotes(): Flow<List<Note>>

    fun getAllNotesWithTag(): Flow<List<NoteWithTag>>

    suspend fun getNoteById(id: Long): Note?

    suspend fun getNoteWithTagById(id: Long): NoteWithTag?
}