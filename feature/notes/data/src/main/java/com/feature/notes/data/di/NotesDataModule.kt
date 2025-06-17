package com.feature.notes.data.di

import com.feature.notes.data.repo.NotesRepositoryImpl
import com.feature.notes.domain.repo.NotesRepository
import dagger.Binds
import dagger.Module

@Module
abstract class NotesDataModule {
    @Binds
    abstract fun bindNotesRepository(notesRepositoryImpl: NotesRepositoryImpl): NotesRepository
}
