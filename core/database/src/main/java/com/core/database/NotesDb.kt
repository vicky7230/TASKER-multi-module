package com.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.core.database.dao.NotesDao
import com.core.database.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
abstract class NotesDb : RoomDatabase() {
    abstract fun getNotesDao(): NotesDao
}