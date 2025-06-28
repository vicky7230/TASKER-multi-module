package com.vicky7230.tasker2.di.module

import com.feature.add_edit_note.ui.navigation.AddEditNoteApi
import com.feature.notes.ui.navigation.NotesApi
import com.feature.tags.ui.navigation.TagsApi
import com.vicky7230.tasker2.navigation.NavigationProvider
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideNavigationProvider(
        notesApi: NotesApi,
        addEditNoteApi: AddEditNoteApi,
        tagsApi: TagsApi,
    ): NavigationProvider = NavigationProvider(notesApi = notesApi, addEditNoteApi = addEditNoteApi, tagsApi = tagsApi)
}
