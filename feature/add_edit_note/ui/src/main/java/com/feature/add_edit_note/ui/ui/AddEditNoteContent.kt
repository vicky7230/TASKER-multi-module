@file:SuppressLint("NewApi")
@file:Suppress("MagicNumber")

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.core.common.theme.TaskerTheme
import com.core.common.ui.ObserveKeyboardWithViewTree
import com.core.common.ui.rememberPickerState
import com.core.domain.model.NoteWithTag
import com.core.domain.model.TagWithNotes
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val TAG = "NoteContent"

@OptIn(FlowPreview::class)
@Composable
fun AddEditNoteContent(
    state: AddEditNoteUiState.NoteAndTags,
    onNoteChange: (NoteWithTag) -> Unit,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    var tagsExpanded by remember { mutableStateOf(false) }
    var calendarExpanded by remember { mutableStateOf(false) }
    var timePickerExpanded by remember { mutableStateOf(false) }
    val hoursPickerState = rememberPickerState(state.note.time.split(":")[0])
    val minutesPickerState = rememberPickerState(state.note.time.split(":")[1])

    val currentState by rememberUpdatedState(state)

    LaunchedEffect(onNoteChange) {
        snapshotFlow {
            String.format(
                Locale.getDefault(),
                "%02d:%02d:00",
                hoursPickerState.selectedItem.toInt(),
                minutesPickerState.selectedItem.toInt(),
            )
        }.debounce(300)
            .distinctUntilChanged()
            .collect { formattedTime: String ->
                Log.d(TAG, "onTimeSelect: $formattedTime")
                Log.d(TAG, "note: ${state.note}")
                onNoteChange(currentState.note.copy(time = formattedTime))
            }
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveKeyboardWithViewTree { isOpen ->
        if (isOpen && (tagsExpanded || calendarExpanded || timePickerExpanded)) {
            tagsExpanded = false
            calendarExpanded = false
            timePickerExpanded = false
        }
    }

    Box(
        modifier = modifier,
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
                onAlarmClick = {
                    timePickerExpanded = !timePickerExpanded
                    if (timePickerExpanded) {
                        keyboardController?.hide()
                        tagsExpanded = false
                        calendarExpanded = false
                    }
                },
                onCalendarClick = {
                    calendarExpanded = !calendarExpanded
                    if (calendarExpanded) {
                        keyboardController?.hide()
                        tagsExpanded = false
                        timePickerExpanded = false
                    }
                },
                onTagClick = {
                    tagsExpanded = !tagsExpanded
                    if (tagsExpanded) {
                        calendarExpanded = false
                        keyboardController?.hide()
                        timePickerExpanded = false
                    }
                },
                highlightAlarm = timePickerExpanded,
                highlightDate = calendarExpanded,
                highlightTag = tagsExpanded,
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
                note = state.note,
                expanded = calendarExpanded,
                onDateSelect = { date: LocalDate ->
                    val formattedDate = date.format(DateTimeFormatter.ISO_DATE)
                    Log.d(TAG, "onDateSelect: $formattedDate")
                    onNoteChange(state.note.copy(date = formattedDate))
                },
                modifier = Modifier.fillMaxWidth(),
            )

            VerticalTimePicker(
                expanded = timePickerExpanded,
                modifier = Modifier.fillMaxWidth(),
                hoursPickerState = hoursPickerState,
                minutesPickerState = minutesPickerState,
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
        AddEditNoteContent(
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
                    tags =
                        persistentListOf(
                            TagWithNotes(
                                id = 1,
                                name = "Work",
                                color = "#61DEA4",
                                notes = persistentListOf(),
                            ),
                            TagWithNotes(
                                id = 2,
                                name = "Shopping",
                                color = "#F45E6D",
                                notes = persistentListOf(),
                            ),
                            TagWithNotes(
                                id = 3,
                                name = "Personal",
                                color = "#B678FF",
                                notes = persistentListOf(),
                            ),
                            TagWithNotes(
                                id = 4,
                                name = "Family",
                                color = "#006CFF",
                                notes = persistentListOf(),
                            ),
                        ),
                ),
            onNoteChange = {},
            onCancelClick = {},
            onDoneClick = {},
        )
    }
}
