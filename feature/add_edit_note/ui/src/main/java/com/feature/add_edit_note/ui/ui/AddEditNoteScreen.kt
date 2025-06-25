package com.feature.add_edit_note.ui.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.core.common.theme.TaskerTheme
import com.core.common.ui.ErrorScreen
import com.core.domain.model.NoteWithTag
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AddEditNoteScreen(
    addEditNoteUiState: AddEditNoteUiState,
    onNoteChange: (NoteWithTag) -> Unit,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = addEditNoteUiState
    Scaffold(
        modifier = modifier,
        topBar = {},
    ) { padding ->
        when (state) {
            is AddEditNoteUiState.Error -> {
                ErrorScreen(
                    message = state.message,
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(padding),
                )
            }

            AddEditNoteUiState.Idle -> {}
            AddEditNoteUiState.Loading -> {}
            is AddEditNoteUiState.NoteAndTags -> {
                NoteContent(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(padding),
                    state = state,
                    onNoteChange = onNoteChange,
                    onCancelClick = onCancelClick,
                    onDoneClick = onDoneClick,
                )
            }
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
private fun NotesScreenPreview() {
    TaskerTheme {
        AddEditNoteScreen(
            modifier = Modifier.fillMaxSize(),
            addEditNoteUiState =
                AddEditNoteUiState.NoteAndTags(
                    NoteWithTag(
                        id = 5,
                        content = "",
                        timestamp = 0,
                        tagId = 1,
                        done = false,
                        tagName = "Work",
                        tagColor = "#61DEA4",
                        date = "2025-06-25",
                        time = "00:00:00",
                    ),
                    tags = persistentListOf(),
                ),
            onNoteChange = {},
            onCancelClick = {},
            onDoneClick = {},
        )
    }
}
