package com.core.database.di

import android.app.Application

class BaseApplicationTest : Application() {

    lateinit var applicationComponentTest: ApplicationComponentTest

    override fun onCreate() {
        super.onCreate()

        applicationComponentTest = DaggerApplicationComponentTest.factory().create(this)
    }
}