package com.feature.notes.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import com.core.common.theme.Blue
import com.core.common.theme.TaskerTheme
import com.feature.notes.domain.model.NoteWithTag

@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    notesUiState: NotesUiState,
    onNoteClick: (NoteWithTag) -> Unit,
    onAddNoteClick: () -> Unit
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
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Note"
                )
            }
        }
    ) { padding ->
        when (state) {
            is NotesUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.message,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    )
                }
            }

            NotesUiState.Idle -> {}
            NotesUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is NotesUiState.NotesLoaded -> NotesContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                notesUiState = state,
                onNoteClick = onNoteClick
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4
)
@Composable
fun NotesScreenPreview(@PreviewParameter(NotesPreviewParameterProvider::class) notesUiState: NotesUiState) {
    TaskerTheme {
        NotesScreen(
            modifier = Modifier.fillMaxSize(),
            notesUiState = notesUiState,
            onAddNoteClick = {},
            onNoteClick = {}
        )
    }
}