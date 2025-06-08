package com.feature.notes.data.repo

import com.core.database.NotesDb
import com.feature.notes.data.mapper.toDomain
import com.feature.notes.data.mapper.toEntity
import com.feature.notes.domain.model.Note
import com.feature.notes.domain.repo.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val notesDb: NotesDb
) : NotesRepository {
    override suspend fun upsertNote(note: Note): Long {
        return notesDb.getNotesDao().upsertNote(note.toEntity())
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return notesDb.getNotesDao().getAllNotes().map { it.map { it.toDomain() } }
    }

    override suspend fun getNoteById(id: Long): Note? {
        return notesDb.getNotesDao().getNoteById(id)?.toDomain()
    }
}