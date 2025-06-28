@file:SuppressLint("NewApi")

package com.feature.tags.ui.screen

import android.annotation.SuppressLint
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.core.domain.model.Note
import com.core.domain.model.TagWithNotes
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TagScreenPreviewParameterProvider : PreviewParameterProvider<TagsUiState> {
    override val values =
        sequenceOf(
            TagsUiState.Idle,
            TagsUiState.Loading,
            TagsUiState.TagLoaded(tagWithNotes),
            TagsUiState.Error("An error occurred"),
        )
}

val tagWithNotes =
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
    )
