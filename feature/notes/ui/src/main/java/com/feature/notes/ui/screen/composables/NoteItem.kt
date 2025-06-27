package com.feature.notes.ui.screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.theme.TaskerTheme
import com.core.common.utils.toColorSafely
import com.core.domain.model.NoteWithTag
import com.feature.notes.ui.screen.notes

@Composable
fun NoteItem(
    note: NoteWithTag,
    onNoteClick: (NoteWithTag) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .clickable { onNoteClick(note) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier =
                Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .size(28.dp),
            painter = painterResource(com.core.common.R.drawable.ic_ring_gray),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Text(
            modifier =
                Modifier
                    .padding(end = 16.dp, top = 19.dp, bottom = 19.dp)
                    .weight(1f),
            text = note.content,
            style =
                TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
        )
        Box(
            modifier =
                Modifier
                    .padding(end = 16.dp)
                    .size(12.dp)
                    .background(color = note.tagColor.toColorSafely(), shape = CircleShape),
        )
    }
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true)
@Composable
private fun NoteItemPreview() {
    TaskerTheme {
        NoteItem(
            note = notes[0],
            onNoteClick = {},
        )
    }
}
