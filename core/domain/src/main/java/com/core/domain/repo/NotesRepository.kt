package com.core.domain.repo

import com.core.domain.model.Note
import com.core.domain.model.NoteWithTag
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun upsertNotes(notes: List<NoteWithTag>): List<Long>

    fun getAllNotes(): Flow<List<Note>>

    fun getAllNotesWithTag(): Flow<List<NoteWithTag>>

    suspend fun getNoteById(id: Long): Note?

    suspend fun getNoteWithTagById(id: Long): NoteWithTag?

    suspend fun updateNoteDone(
        id: Long,
        done: Boolean,
    ): Int

    suspend fun updateNoteDeleted(
        id: Long,
        deleted: Boolean,
    ): Int
}
