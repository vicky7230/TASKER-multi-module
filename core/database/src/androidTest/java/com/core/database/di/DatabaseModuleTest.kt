package com.core.database.di

import android.content.Context
import androidx.room.Room
import com.core.database.NotesDb
import com.core.database.dao.NotesDao
import com.core.database.dao.TagsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class],
)
object DatabaseModuleTest {
    @Provides
    fun provideInMemoryDatabase(
        @ApplicationContext context: Context,
    ): NotesDb =
        Room
            .inMemoryDatabaseBuilder(context, NotesDb::class.java)
            .allowMainThreadQueries()
            .build()

    @Provides
    fun provideNotesDao(notesDb: NotesDb): NotesDao = notesDb.getNotesDao()

    @Provides
    fun provideTagsDao(notesDb: NotesDb): TagsDao = notesDb.getTagsDao()
}
