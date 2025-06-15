package com.feature.notes.domain.repo

import com.feature.notes.domain.model.Note
import com.feature.notes.domain.model.NoteWithTag
import com.feature.notes.domain.model.TagWithNotes
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun upsertNotes(notes: List<NoteWithTag>): List<Long>

    fun getAllNotes(): Flow<List<Note>>

    fun getAllNotesWithTag(): Flow<List<NoteWithTag>>

    suspend fun getNoteById(id: Long): Note?

    suspend fun getNoteWithTagById(id: Long): NoteWithTag?

    fun getAllTagsWithNotes(): Flow<List<TagWithNotes>>
}