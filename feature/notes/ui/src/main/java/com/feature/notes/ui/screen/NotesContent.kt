@file:Suppress("MagicNumber")

package com.feature.notes.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.core.common.R
import com.core.common.theme.Blue
import com.core.common.theme.TaskerTheme
import com.core.domain.model.NoteWithTag
import com.feature.notes.ui.screen.composables.FabMenu
import com.feature.notes.ui.screen.composables.FabOption
import com.feature.notes.ui.screen.composables.NotesOverview

@Composable
fun NotesContent(
    notesUiState: NotesUiState.NotesLoaded,
    fabMenuItems: List<FabOption>,
    onNoteClick: (NoteWithTag) -> Unit,
    modifier: Modifier = Modifier,
) {
    var fabExpanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (fabExpanded) 45f else 0f,
        label = "Fab Rotation",
    )
    Box(modifier = modifier) {
        NotesOverview(
            modifier = Modifier.fillMaxSize(),
            notesUiState = notesUiState,
            onNoteClick = onNoteClick,
        )

        // Scrim overlay layer - positioned between content and FAB
        if (fabExpanded) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(Color.Gray.copy(alpha = 0.7f))
                        .clickable { fabExpanded = false }
                        .zIndex(2f),
            )
        }

        AnimatedVisibility(
            visible = fabExpanded,
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .zIndex(4f),
        ) {
            FabMenu(
                items = fabMenuItems,
                modifier =
                    Modifier
                        .padding(bottom = 90.dp, end = 16.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(8.dp),
                            clip = false,
                        ).background(color = Color.White, shape = RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp)),
            )
        }

        // FAB layer - highest z-index
        Button(
            onClick = { fabExpanded = !fabExpanded },
            shape = CircleShape,
            modifier =
                Modifier
                    .padding(16.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = CircleShape,
                        clip = false,
                    ).size(56.dp)
                    .align(Alignment.BottomEnd)
                    .zIndex(5f),
            contentPadding = PaddingValues(0.dp), // Remove default padding
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "FAB",
                modifier = Modifier.rotate(rotation),
                tint = Blue,
            )
        }
    }
}

@Suppress("UnusedPrivateMember")
@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4,
)
@Composable
private fun NotesContentPreview() {
    TaskerTheme {
        NotesContent(
            notesUiState = NotesUiState.NotesLoaded(notes, tags),
            fabMenuItems =
                listOf(
                    FabOption(
                        label = "Task",
                        icon = ImageVector.vectorResource(R.drawable.ic_create_task),
                        color = Blue,
                        onClick = {},
                    ),
                    FabOption(
                        label = "List",
                        icon = ImageVector.vectorResource(R.drawable.ic_create_list),
                        color = Blue,
                        onClick = {},
                    ),
                ),
            onNoteClick = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}
