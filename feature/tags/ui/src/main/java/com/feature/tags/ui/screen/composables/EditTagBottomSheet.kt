package com.feature.tags.ui.screen.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.theme.LightGray
import com.core.common.theme.LightGray3
import com.core.common.theme.TaskerTheme
import com.core.common.ui.PrimaryButton
import com.core.common.ui.RoundedTextField
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
        ) {
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = "Rename Tag",
                style =
                    TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    ),
            )

            Text(
                modifier = Modifier.padding(bottom = 20.dp, top = 4.dp),
                text = "Enter a new name for the tag",
                style =
                    TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = LightGray3,
                    ),
            )

            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "Tag Name",
                style =
                    TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    ),
            )

            RoundedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = tagName,
                onTextChange = {
                    tagName = it
                },
            )

            Row(modifier = Modifier.padding(vertical = 20.dp)) {
                PrimaryButton(
                    buttonText = "Cancel",
                    modifier = Modifier.weight(1f),
                    onButtonClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                hideEditTagBottomSheet()
                            }
                        }
                    },
                    buttonColors =
                        ButtonDefaults.buttonColors().copy(
                            containerColor = LightGray,
                        ),
                )
                Spacer(modifier = Modifier.width(14.dp))
                PrimaryButton(
                    buttonText = "Save",
                    modifier = Modifier.weight(1f),
                    onButtonClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onSaveTagNameClick(bottomSheet.tagId, tagName.trim())
                                hideEditTagBottomSheet()
                            }
                        }
                    },
                    buttonTextColor = Color.White,
                )
            }
        }
    }
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EditTagBottomSheetPreview() {
    TaskerTheme {
        EditTagBottomSheet(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
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
