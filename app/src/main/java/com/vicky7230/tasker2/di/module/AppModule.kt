package com.vicky7230.tasker2.di.module

import com.feature.add_edit_note.ui.navigation.AddEditNoteFeature
import com.feature.notes.ui.navigation.NotesFeature
import com.feature.tags.ui.navigation.TagsFeature
import com.vicky7230.tasker2.navigation.DefaultNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    fun provideDefaultNavigator(
        notesFeature: NotesFeature,
        addEditNoteFeature: AddEditNoteFeature,
        tagsFeature: TagsFeature,
    ): DefaultNavigator =
        DefaultNavigator(
            notesFeature = notesFeature,
            addEditNoteFeature = addEditNoteFeature,
            tagsFeature = tagsFeature,
        )
}
