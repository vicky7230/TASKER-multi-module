package com.feature.notes.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.core.common.theme.Blue
import com.core.common.theme.TaskerTheme
import com.core.common.ui.ErrorScreen
import com.core.common.ui.LoadingScreen
import com.core.domain.model.NoteWithTag

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
        topBar = {},
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddNoteClick() },
                containerColor = Color.White,
                contentColor = Blue,
                shape = CircleShape,
                // elevation = FloatingActionButtonDefaults.elevation(0.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Note",
                )
            }
        },
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
