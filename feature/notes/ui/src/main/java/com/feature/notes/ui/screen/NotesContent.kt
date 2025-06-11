package com.feature.notes.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.R
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
    Row(
        modifier = modifier
            .clickable { onNoteClick(note) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            painter = painterResource(R.drawable.ic_ring_gray),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(end = 16.dp, top= 19.dp, bottom = 19.dp),
            text = note.content,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
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