package com.feature.notes.data.di

import com.core.domain.repo.NotesRepository
import com.feature.notes.data.repo.NotesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class NotesDataModule {
    @Binds
    abstract fun bindNotesRepository(notesRepositoryImpl: NotesRepositoryImpl): NotesRepository
}
