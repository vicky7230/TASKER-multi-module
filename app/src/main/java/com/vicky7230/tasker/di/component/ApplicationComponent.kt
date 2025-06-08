package com.vicky7230.tasker.di.component

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.core.database.di.DatabaseModule
import com.core.network.di.NetworkModule
import com.feature.notes.data.di.NotesDataModule
import com.feature.notes.ui.di.NotesUiModule
import com.vicky7230.tasker.BaseApplication
import com.vicky7230.tasker.di.module.ActivityBindingModule
import com.vicky7230.tasker.di.module.AppModule
import com.vicky7230.tasker.di.module.ViewModelModule
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
        AppModule::class,
        ViewModelModule::class,
        ActivityBindingModule::class
    ]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun viewModelFactory(): ViewModelProvider.Factory

    fun inject(baseApplication: BaseApplication)
}