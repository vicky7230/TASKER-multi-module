package com.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.core.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert
    suspend fun insertNote(note: NoteEntity): Long

    @Query("SELECT * from notes")
    fun getAllNotes(): Flow<List<NoteEntity>>
}