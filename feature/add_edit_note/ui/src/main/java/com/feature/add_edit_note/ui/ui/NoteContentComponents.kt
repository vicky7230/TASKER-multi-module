@file:Suppress("MagicNumber")

package com.feature.add_edit_note.ui.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.R
import com.core.common.theme.Blue
import com.core.common.theme.LightGray
import com.core.common.theme.LightGray3
import com.core.common.theme.TaskerTheme
import com.core.common.ui.TagItem
import com.core.common.utils.toColorSafely
import com.core.domain.model.NoteWithTag
import com.core.domain.model.TagWithNotes
import kotlinx.coroutines.delay

@Composable
fun TagsList(
    tags: List<TagWithNotes>,
    expanded: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .animateContentSize()
                .then(
                    if (expanded) {
                        Modifier.wrapContentHeight()
                    } else {
                        Modifier.height(0.dp)
                    },
                ),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            tags.forEachIndexed { index, tag ->
                TagItem(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = if (index == 0) 8.dp else 0.dp,
                                bottom = if (index == tags.size - 1) 8.dp else 0.dp,
                            ),
                    tag = tag,
                )
            }
        }
    }
}

@Suppress("UnusedPrivateMember")
@Preview
@Composable
private fun PreviewTagsList() {
    TaskerTheme {
        Box(modifier = Modifier.background(Color.White)) {
            TagsList(
                expanded = true,
                tags =
                    listOf(
                        TagWithNotes(
                            id = 1,
                            name = "Work",
                            color = "#61DEA4",
                            notes = emptyList(),
                        ),
                        TagWithNotes(
                            id = 2,
                            name = "Shopping",
                            color = "#F45E6D",
                            notes = emptyList(),
                        ),
                        TagWithNotes(
                            id = 3,
                            name = "Personal",
                            color = "#B678FF",
                            notes = emptyList(),
                        ),
                        TagWithNotes(
                            id = 4,
                            name = "Family",
                            color = "#006CFF",
                            notes = emptyList(),
                        ),
                    ),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
fun ActionButtons(
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Text(
            modifier =
                Modifier
                    .padding(top = 10.dp, bottom = 10.dp, start = 16.dp)
                    .clickable { onCancelClick() },
            text = stringResource(R.string.cancel),
            style =
                TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                ),
            color = Blue,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier =
                Modifier
                    .padding(top = 10.dp, bottom = 10.dp, end = 16.dp)
                    .clickable { onDoneClick() },
            text = stringResource(R.string.done),
            style =
                TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                ),
            color = Blue,
        )
    }
}

@Composable
fun NoteOptions(
    state: AddEditNoteUiState.NoteAndTags,
    onTagClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.padding(start = 15.dp).size(24.dp),
            painter = painterResource(R.drawable.ic_calendar),
            contentDescription = null,
        )

        Icon(
            modifier = Modifier.padding(start = 20.dp),
            painter = painterResource(R.drawable.ic_alarm),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier =
                Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .clickable { onTagClick() },
            text = state.note.tagName,
            style =
                TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = LightGray3,
                ),
        )
        Box(
            modifier =
                Modifier
                    .padding(start = 8.dp, end = 15.dp)
                    .size(12.dp)
                    .background(
                        color = state.note.tagColor.toColorSafely(),
                        shape = CircleShape,
                    ),
        )
    }
}

@Composable
fun NoteInputField(
    noteContent: String,
    onNoteContentChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        delay(100) // allows view to be attached properly
        keyboardController?.show()
    }
    TextField(
        modifier = modifier.focusRequester(focusRequester),
        value = noteContent,
        onValueChange = {
            onNoteContentChange(it)
        },
        colors =
            TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
            ),
        placeholder = {
            Text(
                text = stringResource(R.string.what_do_you_want_tot_do),
                style =
                    TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                    ),
                color = LightGray,
            )
        },
        leadingIcon = {
            Box(
                modifier =
                    Modifier
                        .fillMaxHeight()
                        .wrapContentHeight(align = Alignment.Top)
                        .padding(start = 16.dp, end = 10.dp)
                        .offset(y = 12.dp),
            ) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(R.drawable.ic_ring_gray),
                    contentDescription = null,
                )
            }
        },
        minLines = 3,
        textStyle =
            TextStyle(
                fontSize = 18.sp,
            ),
    )
}

@Suppress("UnusedPrivateMember")
@Preview
@Composable
private fun PreviewActionButtons() {
    TaskerTheme {
        Box(modifier = Modifier.background(color = Color.White)) {
            ActionButtons(
                onCancelClick = {},
                onDoneClick = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Suppress("UnusedPrivateMember")
@Preview
@Composable
private fun PreviewNoteOptions() {
    TaskerTheme {
        Box(modifier = Modifier.background(color = Color.White).fillMaxWidth()) {
            NoteOptions(
                state =
                    AddEditNoteUiState.NoteAndTags(
                        note =
                            NoteWithTag(
                                id = 0,
                                content = "Sample note content",
                                timestamp = 0,
                                tagId = 1,
                                done = false,
                                tagName = "Work",
                                tagColor = "#61DEA4",
                            ),
                        tags = emptyList(),
                    ),
                onTagClick = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Suppress("UnusedPrivateMember")
@Preview
@Composable
private fun PreviewNoteInputField() {
    TaskerTheme {
        Box(modifier = Modifier.background(color = Color.White)) {
            NoteInputField(
                noteContent = "This is a sample note content",
                onNoteContentChange = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
