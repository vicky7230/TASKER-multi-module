package com.vicky7230.tasker.di.module

import com.vicky7230.tasker.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}