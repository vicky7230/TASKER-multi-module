package com.core.common.di

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

interface AssistedViewModelFactory<T : ViewModel> {
    fun create(savedStateHandle: SavedStateHandle): T
}
