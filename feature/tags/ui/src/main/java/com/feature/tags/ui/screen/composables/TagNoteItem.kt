@file:SuppressLint("NewApi")

package com.feature.tags.ui.screen.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.core.common.utils.toColorSafely
import com.core.domain.model.Note
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TagNoteItem(
    note: Note,
    onNoteClick: (Note) -> Unit,
    onNoteDoneClick: (Note) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.clickable { onNoteClick(note) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier =
                Modifier
                    .padding(start = 6.dp, end = 6.dp)
                    .size(48.dp)
                    .clickable {
                        if (note.done.not()) {
                            onNoteDoneClick(note)
                        }
                    }.padding(10.dp),
            painter =
                if (note.done) {
                    painterResource(com.core.common.R.drawable.ic_marked)
                } else {
                    painterResource(com.core.common.R.drawable.ic_ring_gray)
                },
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
                    color = Color.White,
                ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
        )
    }
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true)
@Composable
private fun TagNoteItemPreview() {
    TagNoteItem(
        modifier = Modifier.fillMaxWidth().background("#61DEA4".toColorSafely()),
        note =
            Note(
                id = 1,
                content = "Meeting with client",
                tagId = 1,
                timestamp = System.currentTimeMillis(),
                done = false,
                date = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
                time =
                    LocalDate
                        .now()
                        .atStartOfDay()
                        .toLocalTime()
                        .format(DateTimeFormatter.ofPattern("HH:mm:ss")),
            ),
        onNoteClick = {},
        onNoteDoneClick = {},
    )
}
