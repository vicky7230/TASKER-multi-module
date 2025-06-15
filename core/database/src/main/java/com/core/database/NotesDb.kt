package com.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.core.database.dao.NotesDao
import com.core.database.dao.TagsDao
import com.core.database.entity.NoteEntity
import com.core.database.entity.TagEntity

@Database(
    entities = [NoteEntity::class, TagEntity::class],
    autoMigrations = [AutoMigration(1, 2)],
    version = 2,
    exportSchema = true
)
abstract class NotesDb : RoomDatabase() {
    abstract fun getNotesDao(): NotesDao
    abstract fun getTagsDao(): TagsDao
}