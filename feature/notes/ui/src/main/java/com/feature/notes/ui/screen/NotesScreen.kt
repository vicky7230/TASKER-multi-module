package com.feature.notes.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.core.common.R
import com.core.common.theme.Blue
import com.core.common.theme.TaskerTheme
import com.core.common.ui.ErrorScreen
import com.core.common.ui.LoadingScreen
import com.core.domain.model.NoteWithTag
import com.feature.notes.ui.screen.composables.FabOption

@Composable
fun NotesScreen(
    notesUiState: NotesUiState,
    onNoteClick: (NoteWithTag) -> Unit,
    onAddNoteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = notesUiState
    Scaffold(
        modifier = modifier,
    ) { padding ->
        when (state) {
            is NotesUiState.Error -> {
                ErrorScreen(
                    message = state.message,
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(padding),
                )
            }

            NotesUiState.Idle -> {}
            NotesUiState.Loading -> {
                LoadingScreen(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(padding),
                )
            }

            is NotesUiState.NotesLoaded ->
                NotesContent(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(padding),
                    options =
                        listOf(
                            FabOption(
                                label = "Task",
                                icon = ImageVector.vectorResource(R.drawable.ic_create_task),
                                color = Blue,
                                onClick = onAddNoteClick,
                            ),
                            FabOption(
                                label = "List",
                                icon = ImageVector.vectorResource(R.drawable.ic_create_list),
                                color = Blue,
                                onClick = {
                                    // TODO
                                },
                            ),
                        ),
                    notesUiState = state,
                    onNoteClick = onNoteClick,
                )
        }
    }
}

@Suppress("UnusedPrivateMember")
@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4,
)
@Composable
private fun NotesScreenPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class) notesUiState: NotesUiState,
) {
    TaskerTheme {
        NotesScreen(
            modifier = Modifier.fillMaxSize(),
            notesUiState = notesUiState,
            onAddNoteClick = {},
            onNoteClick = {},
        )
    }
}
