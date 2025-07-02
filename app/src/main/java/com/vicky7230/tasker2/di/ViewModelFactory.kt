package com.vicky7230.tasker2.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory
    @Inject
    constructor(
        private val viewModelProviders: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(
            modelClass: Class<T>,
            extras: CreationExtras,
        ): T {
            val creator =
                viewModelProviders[modelClass]
                    ?: viewModelProviders
                        .asIterable()
                        .firstOrNull { modelClass.isAssignableFrom(it.key) }
                        ?.value
                    ?: throw IllegalArgumentException("Unknown ViewModel class: $modelClass")

            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        }
    }
