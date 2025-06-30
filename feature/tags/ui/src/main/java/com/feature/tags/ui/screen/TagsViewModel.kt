@file:Suppress("MagicNumber")

package com.feature.tags.ui.screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.core.common.di.AssistedViewModelFactory
import com.core.common.navigation.TagScreen
import com.feature.tags.domain.usecase.GetTagWithNotesUseCase
import com.feature.tags.domain.usecase.UpdateTagNameUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TagsViewModel
    @AssistedInject
    constructor(
        @Assisted private val savedStateHandle: SavedStateHandle,
        private val getTagWithNotesUseCase: GetTagWithNotesUseCase,
        private val updateTagNameUseCase: UpdateTagNameUseCase,
    ) : ViewModel() {
        companion object {
            private val TAG = TagsViewModel::class.simpleName
        }

        @AssistedFactory
        interface Factory : AssistedViewModelFactory<TagsViewModel> {
            override fun create(savedStateHandle: SavedStateHandle): TagsViewModel
        }

        val tagsUiState: StateFlow<TagsUiState>

        init {
            val tagsScreen = savedStateHandle.toRoute<TagScreen>()
            tagsUiState =
                getTagWithNotesUseCase(tagId = tagsScreen.tagId)
                    .map { tags -> TagsUiState.TagLoaded(tags) as TagsUiState }
                    .onStart {
                        emit(TagsUiState.Loading)
                    }.catch { throwable: Throwable ->
                        Log.e(TAG, "Error fetching tags: ${throwable.message}", throwable)
                        emit(TagsUiState.Error("Error fetching tags"))
                    }.stateIn(
                        scope = viewModelScope,
                        initialValue = TagsUiState.Idle,
                        started = SharingStarted.WhileSubscribed(5000),
                    )
        }

        fun updateTagName(
            tagId: Long,
            newName: String,
        ) {
            @Suppress("TooGenericExceptionCaught")
            viewModelScope.launch {
                try {
                    updateTagNameUseCase(tagId = tagId, newName = newName)
                } catch (ex: Exception) {
                    Log.e(TAG, "Error updating tag name: ${ex.message}", ex)
                }
            }
        }
    }
