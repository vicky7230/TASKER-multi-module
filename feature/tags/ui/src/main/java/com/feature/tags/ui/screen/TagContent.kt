@file:SuppressLint("NewApi")

package com.feature.tags.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.theme.LightGray
import com.core.common.theme.LightGray6
import com.core.common.theme.TaskerTheme
import com.core.common.utils.toColorSafely
import com.core.domain.model.Note
import com.core.domain.model.TagWithNotes
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TagContent(
    tag: TagWithNotes,
    onNoteClick: (Note) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .background(
                    color = tag.color.toColorSafely(),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                ).clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier =
                Modifier
                    .padding(top = 5.dp)
                    .width(37.dp)
                    .height(5.dp)
                    .background(color = LightGray6, shape = RoundedCornerShape(3.dp)),
        )

        Row(
            modifier = Modifier.padding(top = 22.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(start = 60.dp),
                text = tag.name,
                style =
                    TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    ),
            )
            Icon(
                modifier =
                    Modifier
                        .padding(end = 12.dp)
                        .size(24.dp),
                painter = painterResource(id = com.core.common.R.drawable.ic_edit),
                tint = Color.Unspecified,
                contentDescription = "Tag edit icon",
            )
        }

        Text(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 60.dp),
            text = "${tag.notes.size} task",
            style =
                TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = LightGray,
                ),
        )

        LazyColumn(
            modifier = Modifier.padding(top = 16.dp).fillMaxSize(),
        ) {
            items(tag.notes, key = { it.id }) { note ->
                TagNoteItem(
                    modifier = Modifier.fillMaxWidth(),
                    note = note,
                    onNoteClick = onNoteClick,
                )
                HorizontalDivider(
                    modifier = Modifier.padding(start = 60.dp).background(Color.White),
                )
            }
        }
    }
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true)
@Composable
private fun TagContentPreview() {
    TaskerTheme {
        TagContent(
            modifier = Modifier.fillMaxSize(),
            tag =
                TagWithNotes(
                    id = 1,
                    name = "Work",
                    color = "#61DEA4",
                    notes =
                        persistentListOf(
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
                            Note(
                                id = 2,
                                content = "Prepare presentation",
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
                            Note(
                                id = 3,
                                content = "Send email to team",
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
                            Note(
                                id = 4,
                                content = "Review project proposal",
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
                        ),
                ),
            onNoteClick = {},
        )
    }
}
