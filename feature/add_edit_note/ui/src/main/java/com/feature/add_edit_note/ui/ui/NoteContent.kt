package com.feature.add_edit_note.ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.R
import com.core.common.theme.Blue
import com.core.common.theme.LightGray
import com.core.common.theme.LightGray3
import com.core.common.theme.TaskerTheme
import com.core.common.utils.toColorSafely
import com.core.domain.model.NoteWithTag

@Composable
fun NoteContent(
    state: AddEditNoteUiState.NoteData,
    onNoteContentChange: (String) -> Unit,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // For scroll-ability when keyboard overlaps
    val scrollState = rememberScrollState()
    Box(
        modifier = modifier.imePadding(), // Push content up when keyboard appears
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
        ) {
            ActionButtons(onCancelClick, onDoneClick)

            NoteInputField(
                noteContent = state.note.content,
                onNoteContentChange = onNoteContentChange,
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
            )

            HorizontalDivider()

            NoteOptions(state = state, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun ActionButtons(
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
private fun NoteOptions(
    state: AddEditNoteUiState.NoteData,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp, start = 15.dp),
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
                    .padding(start = 8.dp)
                    .size(12.dp)
                    .background(
                        color = state.note.tagColor.toColorSafely(),
                        shape = CircleShape,
                    ),
        )
    }
}

@Composable
private fun NoteInputField(
    noteContent: String,
    onNoteContentChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
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
@Preview(
    showBackground = true,
    showSystemUi = false,
    device = Devices.PIXEL_4,
)
@Composable
private fun NoteContentPreview() {
    TaskerTheme {
        NoteContent(
            modifier =
                Modifier
                    .fillMaxSize(),
            state =
                AddEditNoteUiState.NoteData(
                    NoteWithTag(
                        id = 0,
                        content = "note content",
                        timestamp = 0,
                        tagId = 1,
                        done = false,
                        tagName = "Work",
                        tagColor = "#61DEA4",
                    ),
                ),
            onNoteContentChange = { },
            onCancelClick = {},
            onDoneClick = {},
        )
    }
}
