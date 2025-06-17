package com.feature.add_edit_note.ui.di

import com.feature.add_edit_note.ui.navigation.AddEditNoteApi
import com.feature.add_edit_note.ui.navigation.AddEditNoteApiImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AddEditNoteUiModule {
    @Binds
    abstract fun bindAddEditNoteUi(addEditNoteApiImpl: AddEditNoteApiImpl): AddEditNoteApi
}
