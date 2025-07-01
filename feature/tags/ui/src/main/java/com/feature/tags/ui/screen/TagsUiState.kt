package com.feature.tags.ui.screen

import com.core.domain.model.TagWithNotes

sealed class TagsUiState {
    data object Idle : TagsUiState()

    data object Loading : TagsUiState()

    data class TagLoaded(
        val tag: TagWithNotes,
        val tagsUiBottomSheet: TagsUiBottomSheet = TagsUiBottomSheet.None,
    ) : TagsUiState()

    data class Error(
        val message: String,
    ) : TagsUiState()
}

sealed class TagsUiBottomSheet {
    data object None : TagsUiBottomSheet()

    data class RenameTagBottomSheet(
        val tagId: Long,
        val tagName: String,
        val tagColor: String,
    ) : TagsUiBottomSheet()
}
