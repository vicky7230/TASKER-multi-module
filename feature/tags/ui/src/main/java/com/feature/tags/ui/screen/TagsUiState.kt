package com.feature.tags.ui.screen

import com.core.domain.model.TagWithNotes

sealed class TagsUiState {
    data object Idle : TagsUiState()

    data object Loading : TagsUiState()

    data class TagLoaded(
        val tag: TagWithNotes,
    ) : TagsUiState()

    data class Error(
        val message: String,
    ) : TagsUiState()
}
