package com.feature.add_edit_note.ui.ui

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
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveKeyboardWithViewTree { isOpen ->
        if (isOpen && tagsExpanded) {
            tagsExpanded = false
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
                onTagClick = {
                    tagsExpanded = !tagsExpanded
                    if (tagsExpanded) {
                        keyboardController?.hide()
                    }
                },
            )

            TagsList(
                tags = state.tags,
                expanded = tagsExpanded,
                selectedTagId = state.note.tagId,
                onTagClick = { tag: TagWithNotes ->
                    onNoteChange(state.note.copy(tagId = tag.id, tagName = tag.name, tagColor = tag.color))
                },
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
                    ),
                    tags = emptyList(),
                ),
            onNoteChange = { },
            onCancelClick = {},
            onDoneClick = {},
        )
    }
}
