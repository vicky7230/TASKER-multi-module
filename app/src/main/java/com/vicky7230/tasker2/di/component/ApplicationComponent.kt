package com.vicky7230.tasker2.di.component

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.core.database.di.DatabaseModule
import com.core.network.di.NetworkModule
import com.feature.add_edit_note.ui.di.AddEditNoteUiModule
import com.feature.notes.data.di.NotesDataModule
import com.feature.notes.ui.di.NotesUiModule
import com.feature.tags.data.di.TagsDataModule
import com.feature.tags.ui.di.TagsUiModule
import com.vicky7230.tasker2.BaseApplication
import com.vicky7230.tasker2.di.module.ActivityBindingModule
import com.vicky7230.tasker2.di.module.AppModule
import com.vicky7230.tasker2.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        NotesDataModule::class,
        NotesUiModule::class,
        AddEditNoteUiModule::class,
        TagsUiModule::class,
        TagsDataModule::class,
        AppModule::class,
        ViewModelModule::class,
        ActivityBindingModule::class,
    ],
)
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance isDebug: Boolean,
        ): ApplicationComponent
    }

    fun viewModelFactory(): ViewModelProvider.Factory

    fun inject(baseApplication: BaseApplication)
}
