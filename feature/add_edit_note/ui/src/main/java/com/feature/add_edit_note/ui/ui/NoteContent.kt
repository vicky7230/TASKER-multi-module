@file:SuppressLint("NewApi")

package com.feature.add_edit_note.ui.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.core.common.theme.TaskerTheme
import com.core.common.ui.ObserveKeyboardWithViewTree
import com.core.domain.model.NoteWithTag
import com.core.domain.model.TagWithNotes
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun NoteContent(
    state: AddEditNoteUiState.NoteAndTags,
    onNoteChange: (NoteWithTag) -> Unit,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    var tagsExpanded by remember { mutableStateOf(false) }
    var calendarExpanded by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveKeyboardWithViewTree { isOpen ->
        if (isOpen && (tagsExpanded || calendarExpanded)) {
            tagsExpanded = false
            calendarExpanded = false
        }
    }

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
                note = state.note,
                onNoteChange = onNoteChange,
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
            )

            HorizontalDivider()

            NoteOptions(
                state = state,
                modifier =
                    Modifier
                        .imePadding()
                        .fillMaxWidth(),
                onAlarmClick = {},
                onCalendarClick = {
                    calendarExpanded = !calendarExpanded
                    if (calendarExpanded) {
                        keyboardController?.hide()
                        tagsExpanded = false
                    }
                },
                onTagClick = {
                    tagsExpanded = !tagsExpanded
                    if (tagsExpanded) {
                        calendarExpanded = false
                        keyboardController?.hide()
                    }
                },
            )

            TagsList(
                tags = state.tags,
                expanded = tagsExpanded,
                selectedTagId = state.note.tagId,
                onTagClick = { tag: TagWithNotes ->
                    onNoteChange(
                        state.note.copy(
                            tagId = tag.id,
                            tagName = tag.name,
                            tagColor = tag.color,
                        ),
                    )
                },
            )

            HorizontalCalendarUi(
                expanded = calendarExpanded,
                onDateSelect = { date: LocalDate ->
                    val formattedDate = date.format(DateTimeFormatter.ISO_DATE)
                    Log.d("NoteContent", "onDateSelect: $formattedDate")
                    onNoteChange(state.note.copy(date = formattedDate))
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
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
                AddEditNoteUiState.NoteAndTags(
                    NoteWithTag(
                        id = 0,
                        content = "note content",
                        timestamp = 0,
                        tagId = 1,
                        done = false,
                        tagName = "Work",
                        tagColor = "#61DEA4",
                        date = "2025-06-25",
                        time = "00:00:00",
                    ),
                    tags = persistentListOf(),
                ),
            onNoteChange = {},
            onCancelClick = {},
            onDoneClick = {},
        )
    }
}
