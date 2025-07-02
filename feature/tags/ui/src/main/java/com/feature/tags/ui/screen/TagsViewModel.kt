@file:Suppress("MagicNumber")

package com.feature.tags.ui.screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.core.common.navigation.TagScreen
import com.feature.tags.domain.usecase.GetTagWithNotesUseCase
import com.feature.tags.domain.usecase.UpdateTagNameUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
        interface Factory {
            fun create(savedStateHandle: SavedStateHandle): TagsViewModel
        }

        private val _tagsUiState: MutableStateFlow<TagsUiState> = MutableStateFlow(TagsUiState.Idle)
        val tagsUiState: StateFlow<TagsUiState> = _tagsUiState.asStateFlow()

        init {
            val tagsScreen = savedStateHandle.toRoute<TagScreen>()
            viewModelScope.launch {
                _tagsUiState.value = TagsUiState.Loading
                @Suppress("TooGenericExceptionCaught")
                try {
                    getTagWithNotesUseCase(tagId = tagsScreen.tagId)
                        .collect {
                            _tagsUiState.value = TagsUiState.TagLoaded(it)
                        }
                } catch (ex: Exception) {
                    Log.e(TAG, "Error fetching tags: ${ex.message}", ex)
                    _tagsUiState.value = TagsUiState.Error("Error fetching tags")
                }
            }
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

        fun showRenameTagBottomSheet(tagsUiBottomSheet: TagsUiBottomSheet) {
            _tagsUiState.update {
                if (it is TagsUiState.TagLoaded) {
                    it.copy(tagsUiBottomSheet = tagsUiBottomSheet)
                } else {
                    it
                }
            }
        }
    }
