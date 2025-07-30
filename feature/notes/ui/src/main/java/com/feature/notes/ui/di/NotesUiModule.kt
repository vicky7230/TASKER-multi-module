package com.feature.notes.ui.di

import com.feature.notes.ui.navigation.NotesFeature
import com.feature.notes.ui.navigation.NotesFeatureImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class NotesUiModule {
    @Binds
    abstract fun bindNotesApi(notesFeatureImpl: NotesFeatureImpl): NotesFeature
}
