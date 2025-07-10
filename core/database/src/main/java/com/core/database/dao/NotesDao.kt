package com.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.core.database.entity.NoteEntity
import com.core.database.entity.NoteWithTagEntity
import com.core.database.entity.UpdateNoteDeleted
import com.core.database.entity.UpdateNoteDone
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
    @Query("SELECT * FROM notes WHERE isDeleted = 0")
    fun getAllNotesWithTag(): Flow<List<NoteWithTagEntity>>

    @Update(entity = NoteEntity::class)
    suspend fun updateNoteDone(updateNoteDone: UpdateNoteDone): Int

    @Update(entity = NoteEntity::class)
    suspend fun updateNoteDeleted(updateNoteDeleted: UpdateNoteDeleted): Int
}
