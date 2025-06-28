package com.vicky7230.tasker2.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.core.common.di.AssistedViewModelFactory
import com.feature.add_edit_note.ui.ui.AddEditNoteViewModel
import com.feature.notes.ui.screen.NotesViewModel
import com.feature.tags.ui.screen.TagsViewModel
import com.vicky7230.tasker2.di.ViewModelFactory
import com.vicky7230.tasker2.di.ViewModelKey
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

    @Binds
    @IntoMap
    @ViewModelKey(TagsViewModel::class)
    abstract fun bindTagsViewModel(
        factory: TagsViewModel.Factory,
    ): @JvmSuppressWildcards AssistedViewModelFactory<out ViewModel>
}
