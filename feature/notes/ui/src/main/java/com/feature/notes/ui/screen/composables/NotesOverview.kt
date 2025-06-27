package com.feature.notes.ui.screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.theme.LightGray
import com.core.common.theme.LightGray2
import com.core.common.ui.TagItem
import com.core.domain.model.NoteWithTag
import com.feature.notes.ui.screen.NotesUiState

@Composable
fun NotesOverview(
    notesUiState: NotesUiState.NotesLoaded,
    onNoteClick: (NoteWithTag) -> Unit,
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

        items(notesUiState.notes) {
            NoteItem(
                modifier = Modifier.fillMaxWidth(),
                note = it,
                onNoteClick = onNoteClick,
            )
            Spacer(
                modifier =
                    Modifier
                        .padding(start = 60.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(LightGray),
            )
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
                        .fillMaxWidth()
                        .padding(start = 60.dp, end = 16.dp),
                tag = it,
                onTagClick = {},
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
