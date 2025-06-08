package com.feature.notes.domain.repo

import com.feature.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun upsertNote(note: Note): Long

    fun getAllNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Long): Note?
}