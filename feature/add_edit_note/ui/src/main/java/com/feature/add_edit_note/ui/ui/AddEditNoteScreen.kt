package com.feature.add_edit_note.ui.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.sp
import com.core.common.theme.TaskerTheme
import com.feature.notes.domain.model.NoteWithTag

@Composable
fun AddEditNoteScreen(
    modifier: Modifier = Modifier,
    addEditNoteUiState: AddEditNoteUiState,
    onNoteContentChanged: (String) -> Unit,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit
) {
    val state = addEditNoteUiState
    Scaffold(
        modifier = modifier,
        topBar = {}
    ) { padding ->
        when (state) {
            is AddEditNoteUiState.Error -> {
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

            AddEditNoteUiState.Idle -> {}
            AddEditNoteUiState.Loading -> {}
            is AddEditNoteUiState.NoteData -> {
                NoteContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    state = state,
                    onNoteContentChanged = onNoteContentChanged,
                    onCancelClick = onCancelClick,
                    onDoneClick = onDoneClick
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4
)
@Composable
fun NotesScreenPreview() {
    TaskerTheme {
        AddEditNoteScreen(
            modifier = Modifier.fillMaxSize(),
            addEditNoteUiState = AddEditNoteUiState.NoteData(
                NoteWithTag(
                    id = 5,
                    content = "",
                    timestamp = 0,
                    tagId = 1,
                    done = false,
                    tagName = "Work",
                    tagColor = "#61DEA4"
                )
            ),
            onNoteContentChanged = {},
            onCancelClick = {},
            onDoneClick = {}
        )
    }
}
