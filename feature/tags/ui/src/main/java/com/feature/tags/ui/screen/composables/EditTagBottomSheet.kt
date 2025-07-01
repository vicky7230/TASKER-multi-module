package com.feature.tags.ui.screen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.theme.TaskerTheme
import com.feature.tags.ui.screen.TagsUiBottomSheet
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun EditTagBottomSheet(
    bottomSheet: TagsUiBottomSheet.RenameTagBottomSheet,
    hideEditTagBottomSheet: () -> Unit,
    onSaveTagNameClick: (Long, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()
    var tagName by remember { mutableStateOf(bottomSheet.tagName) }
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = {
            hideEditTagBottomSheet()
        },
        sheetState = sheetState,
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Rename Tag",
                style =
                    TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    ),
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = tagName,
                label = { Text("Tag") },
                onValueChange = {
                    tagName = it
                },
                textStyle = TextStyle(fontSize = 18.sp),
            )

            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onSaveTagNameClick(bottomSheet.tagId, tagName.trim())
                            hideEditTagBottomSheet()
                        }
                    }
                },
            ) {
                Text("Save")
            }
        }
    }
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TagRenameDialogPreview() {
    TaskerTheme {
        EditTagBottomSheet(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            bottomSheet =
                TagsUiBottomSheet.RenameTagBottomSheet(
                    tagId = 1,
                    tagName = "Tag",
                    tagColor = "#000000",
                ),
            hideEditTagBottomSheet = { },
            onSaveTagNameClick = { _, _ -> },
        )
    }
}
