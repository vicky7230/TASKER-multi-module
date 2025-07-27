package com.feature.add_edit_note.ui.di

import com.feature.add_edit_note.ui.navigation.AddEditNoteFeature
import com.feature.add_edit_note.ui.navigation.AddEditNoteFeatureImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class AddEditNoteUiModule {
    @Binds
    abstract fun bindAddEditNoteUi(addEditNoteApiImpl: AddEditNoteFeatureImpl): AddEditNoteFeature
}
