package com.vicky7230.tasker.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.core.common.di.AssistedViewModelFactory
import com.feature.add_edit_note.ui.ui.AddEditNoteViewModel
import com.feature.notes.ui.screen.NotesViewModel
import com.vicky7230.tasker.di.ViewModelFactory
import com.vicky7230.tasker.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(NotesViewModel::class)
    abstract fun bindNotesViewModel(notesViewModel: NotesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddEditNoteViewModel::class)
    abstract fun bindAddEditNoteViewModel(
        factory: AddEditNoteViewModel.Factory,
    ): @JvmSuppressWildcards AssistedViewModelFactory<out ViewModel>
}
