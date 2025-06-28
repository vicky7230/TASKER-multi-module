package com.feature.tags.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.core.common.ui.ErrorScreen
import com.core.common.ui.LoadingScreen
import com.core.domain.model.Note

@Composable
fun TagScreenUi(
    tagsUiState: TagsUiState,
    onNoteClick: (Note) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = tagsUiState
    Scaffold(
        modifier = modifier,
    ) { padding ->
        when (state) {
            is TagsUiState.Error -> {
                ErrorScreen(
                    message = state.message,
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(padding),
                )
            }

            TagsUiState.Idle -> {}
            TagsUiState.Loading -> {
                LoadingScreen(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(padding),
                )
            }

            is TagsUiState.TagLoaded -> {
                TagContent(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    tag = state.tag,
                    onNoteClick = onNoteClick,
                )
            }
        }
    }
}
