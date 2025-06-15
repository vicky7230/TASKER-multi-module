package com.core.database

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.core.database.di.BaseApplicationTest

class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, BaseApplicationTest::class.java.name, context)
    }
}