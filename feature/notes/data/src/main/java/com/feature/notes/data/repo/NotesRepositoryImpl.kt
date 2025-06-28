package com.feature.notes.data.repo

import com.core.database.NotesDb
import com.core.database.entity.NoteEntity
import com.core.database.entity.NoteWithTagEntity
import com.core.domain.model.Note
import com.core.domain.model.NoteWithTag
import com.core.domain.repo.NotesRepository
import com.feature.notes.data.mapper.toDomain
import com.feature.notes.data.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotesRepositoryImpl
    @Inject
    constructor(
        private val notesDb: NotesDb,
    ) : NotesRepository {
        override suspend fun upsertNotes(notes: List<NoteWithTag>): List<Long> = notesDb.getNotesDao().upsertNotes(notes.map { it.toEntity() })

        override fun getAllNotes(): Flow<List<Note>> =
            notesDb
                .getNotesDao()
                .getAllNotes()
                .map { it.map { noteEntity: NoteEntity -> noteEntity.toDomain() } }

        override fun getAllNotesWithTag(): Flow<List<NoteWithTag>> =
            notesDb
                .getNotesDao()
                .getAllNotesWithTag()
                .map { it.map { noteWithTagEntity: NoteWithTagEntity -> noteWithTagEntity.toDomain() } }

        override suspend fun getNoteById(id: Long): Note? = notesDb.getNotesDao().getNoteById(id)?.toDomain()

        override suspend fun getNoteWithTagById(id: Long): NoteWithTag? = notesDb.getNotesDao().getNoteWithTagById(id)?.toDomain()
    }
