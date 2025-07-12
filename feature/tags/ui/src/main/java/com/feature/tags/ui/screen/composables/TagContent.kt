package com.feature.tags.ui.screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.theme.LightGray
import com.core.common.theme.Red
import com.core.common.theme.TaskerTheme
import com.core.common.ui.ActionIcon
import com.core.common.ui.SwipeableItemWithActions
import com.core.common.utils.toColorSafely
import com.core.domain.model.Note
import com.core.domain.model.TagWithNotes
import com.feature.tags.ui.screen.TagsUiBottomSheet
import com.feature.tags.ui.screen.TagsUiState
import com.feature.tags.ui.screen.tagWithNotes

@Composable
fun TagContent(
    tagsUiState: TagsUiState.TagLoaded,
    onNoteClick: (Note) -> Unit,
    onEditTagClick: (TagWithNotes) -> Unit,
    onNoteDoneClick: (Note) -> Unit,
    onNoteDeleteClick: (Note) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.background(color = tagsUiState.tag.color.toColorSafely()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.padding(top = 22.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(start = 60.dp),
                    text = tagsUiState.tag.name,
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
                            .size(24.dp)
                            .clickable { onEditTagClick(tagsUiState.tag) },
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
                text = "${tagsUiState.tag.notes.size} task",
                style =
                    TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = LightGray,
                    ),
            )

            LazyColumn(
                modifier =
                    Modifier
                        .padding(top = 16.dp)
                        .fillMaxSize(),
            ) {
                items(tagsUiState.tag.notes, key = { it.id }) { note ->

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
                        TagNoteItem(
                            modifier = Modifier.fillMaxWidth().background(tagsUiState.tag.color.toColorSafely()),
                            note = note,
                            onNoteClick = onNoteClick,
                            onNoteDoneClick = onNoteDoneClick,
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 60.dp),
                        color = Color.White,
                    )
                }
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
            tagsUiState =
                TagsUiState.TagLoaded(
                    tag = tagWithNotes,
                    tagsUiBottomSheet = TagsUiBottomSheet.None,
                ),
            onEditTagClick = {},
            onNoteClick = {},
            onNoteDoneClick = {},
            onNoteDeleteClick = {},
        )
    }
}
