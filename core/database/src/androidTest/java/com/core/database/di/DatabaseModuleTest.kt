package com.core.database.di

import android.content.Context
import androidx.room.Room
import com.core.database.NotesDb
import dagger.Module
import dagger.Provides

@Module
class DatabaseModuleTest {

    @Provides
    fun provideInMemoryDatabase(context: Context): NotesDb {
        return Room.inMemoryDatabaseBuilder(context, NotesDb::class.java).allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideNotesDao(notesDb: NotesDb) = notesDb.getNotesDao()

    @Provides
    fun provideTagsDao(notesDb: NotesDb) = notesDb.getTagsDao()
}