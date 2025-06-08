package com.vicky7230.tasker.di
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.core.common.di.AssistedViewModelFactory
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(
    // Map for regular @Inject ViewModels
    private val viewModelProviders: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>,
    // Map for @AssistedInject ViewModels (which use a factory)
    private val assistedViewModelFactories: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<AssistedViewModelFactory<out ViewModel>>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        // First, try to find a regular ViewModel (no Assisted Inject)
        var creator: Provider<out ViewModel>? = viewModelProviders[modelClass]
        if (creator == null) {
            // If not found, check if it's assignable from any bound regular ViewModel
            for ((key, value) in viewModelProviders) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }

        if (creator != null) {
            // Found a regular ViewModel, create it
            try {
                @Suppress("UNCHECKED_CAST")
                return creator.get() as T
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        // If not a regular ViewModel, try to find an Assisted Inject ViewModel Factory
        val assistedCreatorProvider = assistedViewModelFactories[modelClass]
        if (assistedCreatorProvider == null) {
            // If not found, check if it's assignable from any bound assisted ViewModel factory
            for ((key, value) in assistedViewModelFactories) {
                if (modelClass.isAssignableFrom(key)) {
                    // This implies the ViewModelKey for the assisted factory is on a base class
                    // if (value.get() is AssistedViewModelFactory<*>) { // Defensive check
                    @Suppress("UNCHECKED_CAST")
                    val savedStateHandle = extras.createSavedStateHandle()
                        ?: throw IllegalStateException("SavedStateHandle not found in CreationExtras for assisted ViewModel.")
                    return value.get().create(savedStateHandle) as T
                    // }
                    // else continue to throw or handle
                }
            }
        } else {
            // Found an Assisted Inject ViewModel Factory, create it with SavedStateHandle
            val savedStateHandle = extras.createSavedStateHandle()
                ?: throw IllegalStateException("SavedStateHandle not found in CreationExtras for assisted ViewModel.")
            try {
                @Suppress("UNCHECKED_CAST")
                return assistedCreatorProvider.get().create(savedStateHandle) as T
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        // If nothing matches, throw the original error
        throw IllegalArgumentException("unknown model class $modelClass")
    }
}