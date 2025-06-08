package com.vicky7230.tasker.di.module

import com.feature.notes.ui.navigation.NotesApi
import com.vicky7230.tasker.navigation.NavigationProvider
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideNavigationProvider(notesApi: NotesApi): NavigationProvider {
        return NavigationProvider(notesApi)
    }
}