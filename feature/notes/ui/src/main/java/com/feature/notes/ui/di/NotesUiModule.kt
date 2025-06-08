package com.feature.notes.ui.di

import com.feature.notes.ui.navigation.NotesApi
import com.feature.notes.ui.navigation.NotesApiImpl
import dagger.Binds
import dagger.Module

@Module
abstract class NotesUiModule {

    @Binds
    abstract fun bindNotesApi(notesApiImpl: NotesApiImpl) : NotesApi
}