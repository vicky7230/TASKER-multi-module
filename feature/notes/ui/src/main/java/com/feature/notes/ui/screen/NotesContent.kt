package com.feature.notes.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.theme.TaskerTheme
import com.feature.notes.domain.model.Note

@Composable
fun NotesContent(
    modifier: Modifier = Modifier,
    notesUiState: NotesUiState.NotesList,
    onNoteClick: (Note) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(notesUiState.notes) {
            NoteItem(
                modifier = Modifier.fillMaxWidth(),
                note = it,
                onNoteClick = onNoteClick
            )
        }
    }
}

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClick: (Note) -> Unit
) {
    Box(
        modifier = modifier
            .clickable {
                onNoteClick(note)
            }
            .padding(16.dp)) {
        Text(
            text = note.content,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4
)
@Composable
fun NotesContentPreview() {
    TaskerTheme {
        NotesContent(
            modifier = Modifier.fillMaxSize(),
            notesUiState = NotesUiState.NotesList(notes),
            onNoteClick = {}
        )
    }
}