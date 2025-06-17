package com.core.database.di

import android.content.Context
import com.core.database.NotesDb
import com.core.database.dao.NotesDao
import com.core.database.dao.TagsDao
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModuleTest::class])
interface ApplicationComponentTest {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): ApplicationComponentTest
    }

    fun notesDb(): NotesDb

    fun notesDao(): NotesDao

    fun tagsDao(): TagsDao
}
