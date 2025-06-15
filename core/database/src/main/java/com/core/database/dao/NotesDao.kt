package com.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.core.database.entity.NoteEntity
import com.core.database.entity.NoteWithTagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Upsert
    suspend fun upsertNotes(notes: List<NoteEntity>): List<Long>

    @Query("SELECT * from notes")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Long): NoteEntity?

    @Transaction
    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteWithTagById(id: Long): NoteWithTagEntity?

    @Transaction
    @Query("SELECT * FROM notes")
    fun getAllNotesWithTag(): Flow<List<NoteWithTagEntity>>
}
