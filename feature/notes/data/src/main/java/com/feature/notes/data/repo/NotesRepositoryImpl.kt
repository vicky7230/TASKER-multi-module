package com.feature.notes.data.repo

import com.core.database.NotesDb
import com.feature.notes.data.mapper.toDomain
import com.feature.notes.data.mapper.toEntity
import com.feature.notes.domain.model.Note
import com.feature.notes.domain.model.NoteWithTag
import com.feature.notes.domain.model.TagWithNotes
import com.feature.notes.domain.repo.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val notesDb: NotesDb
) : NotesRepository {
    override suspend fun upsertNote(note: NoteWithTag): Long {
        return notesDb.getNotesDao().upsertNote(note.toEntity())
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return notesDb.getNotesDao().getAllNotes().map { it.map { it.toDomain() } }
    }

    override fun getAllNotesWithTag(): Flow<List<NoteWithTag>> {
        return notesDb.getNotesDao().getAllNotesWithTag().map { it.map { it.toDomain() } }
    }

    override suspend fun getNoteById(id: Long): Note? {
        return notesDb.getNotesDao().getNoteById(id)?.toDomain()
    }

    override suspend fun getNoteWithTagById(id: Long): NoteWithTag? {
        return notesDb.getNotesDao().getNoteWithTagById(id)?.toDomain()
    }

    override fun getAllTagsWithNotes(): Flow<List<TagWithNotes>> {
        return notesDb.getTagsDao().getAllTagsWithNotes().map { it.map { it.toDomain() } }
    }
}