package com.feature.notes.ui.screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.theme.LightGray2
import com.core.common.theme.Red
import com.core.common.ui.ActionIcon
import com.core.common.ui.SwipeableItemWithActions
import com.core.common.ui.TagItem
import com.core.domain.model.NoteWithTag
import com.core.domain.model.TagWithNotes
import com.feature.notes.ui.screen.NotesUiState
import com.feature.notes.ui.screen.notes
import com.feature.notes.ui.screen.tags

@Composable
fun NotesOverview(
    notesUiState: NotesUiState.NotesLoaded,
    onNoteClick: (NoteWithTag) -> Unit,
    onTagClick: (TagWithNotes) -> Unit,
    onNoteDoneClick: (NoteWithTag) -> Unit,
    onNoteDeleteClick: (NoteWithTag) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            Text(
                modifier = Modifier.padding(start = 60.dp, top = 16.dp),
                text = stringResource(com.core.common.R.string.today),
                style =
                    TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                    ),
            )
        }

        itemsIndexed(
            items = notesUiState.notes,
            key = { index, note -> note.id },
        ) { index, note ->
            SwipeableItemWithActions(
                modifier = Modifier.animateItem(),
                isRevealed = note.optionRevealed,
                actions = {
                    ActionIcon(
                        onClick = { onNoteDeleteClick(note) },
                        backgroundColor = Red,
                        icon = ImageVector.vectorResource(id = com.core.common.R.drawable.ic_delete),
                        modifier = Modifier.fillMaxHeight(),
                        contentDescription = "Delete Icon",
                    )
                },
            ) {
                NoteItem(
                    modifier = Modifier.fillMaxWidth(),
                    note = note,
                    onNoteClick = onNoteClick,
                    onNoteDoneClick = onNoteDoneClick,
                )
            }
            HorizontalDivider(modifier = Modifier.padding(start = 60.dp))
        }

        item {
            Text(
                modifier = Modifier.padding(top = 32.dp, start = 60.dp, bottom = 15.dp),
                text = "Lists",
                color = LightGray2,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        items(notesUiState.tags) {
            TagItem(
                modifier =
                    Modifier
                        .animateItem()
                        .fillMaxWidth()
                        .padding(start = 60.dp, end = 16.dp),
                tag = it,
                onTagClick = onTagClick,
            )
            Spacer(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(Color.Transparent),
            )
        }
    }
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true)
@Composable
private fun NotesOverviewPreview() {
    NotesOverview(
        modifier = Modifier.fillMaxSize(),
        notesUiState = NotesUiState.NotesLoaded(notes, tags),
        onNoteClick = {},
        onTagClick = {},
        onNoteDoneClick = {},
        onNoteDeleteClick = {},
    )
}
