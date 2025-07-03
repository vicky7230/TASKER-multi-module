package com.vicky7230.tasker2

import android.app.Application
import com.vicky7230.tasker2.di.component.ApplicationComponent
import com.vicky7230.tasker2.di.component.DaggerApplicationComponent

class BaseApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent =
            DaggerApplicationComponent
                .factory()
                .create(context = this, isDebug = BuildConfig.DEBUG)
    }
}
