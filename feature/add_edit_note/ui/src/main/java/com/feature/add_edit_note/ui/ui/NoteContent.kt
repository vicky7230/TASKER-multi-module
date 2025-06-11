package com.feature.add_edit_note.ui.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.core.common.theme.TaskerTheme
import com.feature.notes.domain.model.Note

@Composable
fun NoteContent(
    modifier: Modifier = Modifier,
    state: AddEditNoteUiState.NoteData,
    onNoteContentChanged: (String) -> Unit,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit
) {
    var noteContent by remember { mutableStateOf(state.note.content) }
    Column {
        Row {
            Text(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp, start = 16.dp)
                    .clickable { onCancelClick() },
                text = stringResource(com.core.common.R.string.cancel),
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp
                ),
                color = Blue
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp, end = 16.dp)
                    .clickable { onDoneClick() },
                text = stringResource(com.core.common.R.string.done),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                ),
                color = Blue
            )
        }

        TextField(
            modifier = modifier,
            value = noteContent,
            onValueChange = {
                noteContent = it
                onNoteContentChanged(it)
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
            ),
            placeholder = {
                Text(
                    text = stringResource(com.core.common.R.string.what_do_you_want_tot_do),
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = LightGray
                )
            },
            leadingIcon = {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentHeight(align = Alignment.Top)
                        .padding(start = 16.dp)
                        .offset(y = 14.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(R.drawable.ic_ring_gray),
                        contentDescription = null
                    )
                }
            },
            minLines = 3,
            textStyle = TextStyle(
                fontSize = 22.sp
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
fun NoteContentPreview() {
    TaskerTheme {
        NoteContent(
            modifier = Modifier
                .fillMaxSize(),
            state = AddEditNoteUiState.NoteData(Note(0, "")),
            onNoteContentChanged = { },
            onCancelClick = {},
            onDoneClick = {}
        )
    }
}