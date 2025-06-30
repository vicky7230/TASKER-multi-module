package com.feature.tags.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.core.common.theme.TaskerTheme
import com.core.common.ui.ErrorScreen
import com.core.common.ui.LoadingScreen
import com.core.domain.model.Note
import com.feature.tags.ui.screen.composables.TagContent

@Composable
fun TagScreenUi(
    tagsUiState: TagsUiState,
    onNoteClick: (Note) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = tagsUiState
    Scaffold(
        modifier = modifier,
        content = {
            when (state) {
                is TagsUiState.Error -> {
                    ErrorScreen(
                        message = state.message,
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(it),
                    )
                }

                TagsUiState.Idle -> {}
                TagsUiState.Loading -> {
                    LoadingScreen(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(it),
                    )
                }

                is TagsUiState.TagLoaded -> {
                    TagContent(
                        modifier = Modifier.fillMaxSize().padding(it),
                        tag = state.tag,
                        onNoteClick = onNoteClick,
                    )
                }
            }
        },
    )
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true)
@Composable
private fun TagScreenPreview(
    @PreviewParameter(TagScreenPreviewParameterProvider::class) state: TagsUiState,
) {
    TaskerTheme {
        TagScreenUi(
            modifier = Modifier.fillMaxSize(),
            tagsUiState = state,
            onNoteClick = {},
        )
    }
}
