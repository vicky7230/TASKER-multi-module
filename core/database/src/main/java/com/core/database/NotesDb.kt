@file:Suppress("MagicNumber")

package com.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.core.database.dao.NotesDao
import com.core.database.dao.TagsDao
import com.core.database.entity.ActiveNoteEntity
import com.core.database.entity.NoteEntity
import com.core.database.entity.TagEntity

@Database(
    entities = [NoteEntity::class, TagEntity::class],
    views = [ActiveNoteEntity::class],
    autoMigrations = [
        AutoMigration(1, 2),
        AutoMigration(2, 3),
        AutoMigration(3, 4),
    ],
    version = 4,
    exportSchema = true,
)
abstract class NotesDb : RoomDatabase() {
    abstract fun getNotesDao(): NotesDao

    abstract fun getTagsDao(): TagsDao
}
