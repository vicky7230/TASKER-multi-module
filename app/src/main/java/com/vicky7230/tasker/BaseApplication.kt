package com.vicky7230.tasker

import android.app.Application
import com.vicky7230.tasker.di.component.ApplicationComponent
import com.vicky7230.tasker.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class BaseApplication :
    Application(),
    HasAndroidInjector {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent =
            DaggerApplicationComponent
                .factory()
                .create(context = this, isDebug = BuildConfig.DEBUG)

        applicationComponent.inject(this)
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}
